package alexthw.hexblades.common.items;

import alexthw.hexblades.deity.DeityLocks;
import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.deity.HexFacts;
import elucent.eidolon.api.deity.Deity;
import elucent.eidolon.capability.IReputation;
import elucent.eidolon.util.KnowledgeUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ElementalSoul extends Item {

    public ElementalSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!world.isClientSide() && entityIn instanceof Player player) {
            if (!KnowledgeUtil.knowsFact(player, HexFacts.EVOLVE_RITUAL)) {
                world.getCapability(IReputation.INSTANCE).ifPresent(rep -> {
                    Deity deity = HexDeities.HEX_DEITY;
                    if (rep.unlock(player.getUUID(), deity.getId(), DeityLocks.EVOLVED_WEAPON)) {
                        deity.onReputationUnlock(player, DeityLocks.EVOLVED_WEAPON);
                    }
                });
            }
        }
    }
}
