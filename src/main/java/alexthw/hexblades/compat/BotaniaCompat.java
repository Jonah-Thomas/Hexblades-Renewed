package alexthw.hexblades.compat;

import alexthw.hexblades.common.items.armors.BotaniaArmor;
import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.network.RefillEffectPacket;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.registers.HexRegistry;
import alexthw.hexblades.util.HexUtils;
import elucent.eidolon.codex.Page;
import elucent.eidolon.codex.TitlePage;
import elucent.eidolon.codex.WorktablePage;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.codex.CodexHexChapters.makePageKey;
import static alexthw.hexblades.codex.CodexHexChapters.nukeRecipe;

public class BotaniaCompat {

    public static void refillApotecaries(Level world, BlockPos urn) {
        List<vazkii.botania.common.block.block_entity.PetalApothecaryBlockEntity> apothecaries =
                HexUtils.getTilesWithinAABB(vazkii.botania.common.block.block_entity.PetalApothecaryBlockEntity.class, world,
                        new AABB(urn.offset(-2, -1, -2), urn.offset(3, 2, 3)));
        for (var fillable : apothecaries) {
            if (fillable.getFluid() == vazkii.botania.api.block.PetalApothecary.State.EMPTY) {
                fillable.setFluid(vazkii.botania.api.block.PetalApothecary.State.WATER);
                HexRegistry.CHANNEL.send(
                        PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(fillable.getBlockPos())),
                        new RefillEffectPacket(fillable.getBlockPos(), 0.5F));
            }
        }
    }

    public static HexWArmor makeArmor(ArmorItem.Type type, Item.Properties properties) {
        return new BotaniaArmor(type, properties);
    }

    public static void addRecipes() {
        WorktableRegistry.register(new WorktableRecipe(
                new Ingredient[]{
                        Ingredient.of(ItemStack.EMPTY), Ingredient.of(vazkii.botania.common.item.BotaniaItems.manaweaveCloth), Ingredient.of(ItemStack.EMPTY),
                        Ingredient.of(vazkii.botania.common.item.BotaniaItems.manaweaveCloth), Ingredient.of(HexItem.FOCUS_BASE.get()), Ingredient.of(vazkii.botania.common.item.BotaniaItems.manaweaveCloth),
                        Ingredient.of(ItemStack.EMPTY), Ingredient.of(vazkii.botania.common.item.BotaniaItems.manaweaveCloth), Ingredient.of(ItemStack.EMPTY)
                },
                new Ingredient[]{
                        Ingredient.of(vazkii.botania.common.item.BotaniaItems.terrasteelNugget),
                        Ingredient.of(vazkii.botania.common.item.BotaniaItems.terrasteelNugget),
                        Ingredient.of(vazkii.botania.common.item.BotaniaItems.terrasteelNugget),
                        Ingredient.of(vazkii.botania.common.item.BotaniaItems.terrasteelNugget)
                },
                new ItemStack(HexItem.FOCUS_BOTANIA.get(), 1)
            ).setRegistryName("hexblades", "botania_focus"));
    }

    public static void renderer() {
        // GeckoLib4: armor renderer is registered via initializeClient() on BotaniaArmor itself
    }

    public static Page[] makeCodex() {
        TitlePage titled = new TitlePage(makePageKey("botania_focus"));
        WorktablePage focusCraft = new WorktablePage(new ItemStack(HexItem.FOCUS_BOTANIA.get(), 1));
        return new Page[]{titled, nukeRecipe(COMMON.NUKE_WORKBENCH.get(), focusCraft)};
    }
}
