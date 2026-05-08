package alexthw.hexblades.deity;

import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.api.deity.Deity;
import elucent.eidolon.capability.IReputation;
import elucent.eidolon.util.KnowledgeUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static elucent.eidolon.Eidolon.prefix;

public class HexDeity extends Deity {

    public HexDeity(ResourceLocation id, int red, int green, int blue) {
        super(id, red, green, blue);
        // Build the progression stages mirroring the old reputation thresholds:
        //   rep >= 6  -> awakening ritual unlocked
        //   rep >= 30 -> elemental summon unlocked (capped until EVOLVED_WEAPON lock is cleared)
        //   rep >= 60 -> max damage cap
        progression
                .add(new Stage(new ResourceLocation("hexblades", "awakening"), 6, true))
                .add(new Stage(new ResourceLocation("hexblades", "elemental_summon"), 30, true))
                .add(new Stage(new ResourceLocation("hexblades", "max_damage"), 60, true));
        progression.setMax(60);
    }

    /**
     * Called when the player advances past a stage threshold (stage was just unlocked).
     * The {@code lock} ResourceLocation is the id of the stage that was just completed.
     */
    @Override
    public void onReputationUnlock(Player player, ResourceLocation lock) {

        if (lock.equals(new ResourceLocation("hexblades", "awakening"))) {
            // Grant the awakening ritual fact and drop an elemental core
            KnowledgeUtil.grantFact(player, HexFacts.AWAKENING_RITUAL);
            Level world = player.level();
            if (!world.isClientSide) {
                world.addFreshEntity(new ItemEntity(world,
                        player.getX() + 0.5D,
                        player.getY() + 2.5D,
                        player.getZ() + 0.5D,
                        new ItemStack(HexItem.ELEMENTAL_CORE.get())));
            }
        } else if (lock.equals(new ResourceLocation("hexblades", "elemental_summon"))) {
            // Grant the evolve ritual fact and the elemental summon fact
            KnowledgeUtil.grantFact(player, HexFacts.ELEMENTAL_SUMMON);
        } else if (lock.equals(new ResourceLocation("hexblades", "max_damage"))) {
            // At the cap stage, nothing extra to grant
        }

    }

    /**
     * Called when the player reaches a stage cap but cannot yet progress past it
     * (i.e., requirements for the current stage are not met).
     * The {@code lock} ResourceLocation is the id of the stage that capped progress.
     */
    @Override
    public void onReputationLock(Player player, ResourceLocation lock) {

        if (lock.equals(new ResourceLocation("hexblades", "elemental_summon"))) {
            // Player is at the 30-rep cap; grant the evolve ritual once they satisfy requirements
            // TODO: add a StageRequirement to the elemental_summon stage if a prerequisite is needed,
            // or trigger a specific event here. In the old system rep.lock() was used to
            // gate progress — use the progression Stage.requirement() system instead.
            KnowledgeUtil.grantFact(player, HexFacts.EVOLVE_RITUAL);
        }

    }

}
