package alexthw.hexblades.client;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.client.render.entity.FireElementalER;
import alexthw.hexblades.client.render.tile.FirePedestalRenderer;
import alexthw.hexblades.client.render.tile.SwordStandRenderer;
import alexthw.hexblades.client.render.tile.Urn_Renderer;
import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.network.MiningSwitchPacket;
import alexthw.hexblades.network.WeaponAwakenPacket;
import alexthw.hexblades.registers.HexBlockEntityType;
import alexthw.hexblades.registers.HexEntityType;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.util.Constants;
import elucent.eidolon.network.Networking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import static alexthw.hexblades.compat.ArmorCompatHandler.attachRenderers;
import static alexthw.hexblades.util.HexUtils.prefix;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Hexblades.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    public static final KeyMapping HEXBLADE_KEYBINDING = new KeyMapping("key.hexblades.awake", GLFW.GLFW_KEY_H, "key.categories.misc");
    public static final KeyMapping HEXDRILL_KEYBINDING = new KeyMapping("key.hexblades.mining", GLFW.GLFW_KEY_J, "key.categories.misc");

    @SubscribeEvent
    public static void registerKeybinding(RegisterKeyMappingsEvent event) {
        event.register(HEXBLADE_KEYBINDING);
        event.register(HEXDRILL_KEYBINDING);
    }

    @SubscribeEvent
    public void onKeyPress(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().player == null) return;
        if (HEXBLADE_KEYBINDING.consumeClick()) Networking.sendToServer(new WeaponAwakenPacket());
        else if (HEXDRILL_KEYBINDING.consumeClick()) Networking.sendToServer(new MiningSwitchPacket());
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(HexBlockEntityType.SWORD_STAND_TILE_ENTITY.get(), SwordStandRenderer::new);
        event.registerBlockEntityRenderer(HexBlockEntityType.FIRE_PEDESTAL_TILE_ENTITY.get(), ctx -> new FirePedestalRenderer());
        event.registerBlockEntityRenderer(HexBlockEntityType.EVERFULL_URN_TILE_ENTITY.get(), Urn_Renderer::new);

        event.registerEntityRenderer(HexEntityType.FULGOR_PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(HexEntityType.MAGMA_PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(HexEntityType.FIRE_ELEMENTAL.get(), FireElementalER::new);
        //event.registerEntityRenderer(HexEntityType.EARTH_ELEMENTAL.get(), EarthElementalER::new);
    }

    @SubscribeEvent
    public static void initClientEvents(FMLClientSetupEvent event) {
        attachRenderers();

        event.enqueueWork(() -> {
            // Awakening toggles
            registerToggleAnimation(HexItem.FROST_RAZOR.get());
            registerToggleAnimation(HexItem.FROST_RAZOR1.get());
            registerToggleAnimation(HexItem.FIRE_BRAND.get());
            registerToggleAnimation(HexItem.FIRE_BRAND1.get());
            registerToggleAnimation(HexItem.WATER_SABER.get());
            registerToggleAnimation(HexItem.WATER_SABER1.get());
            registerToggleAnimation(HexItem.EARTH_HAMMER.get());
            registerToggleAnimation(HexItem.EARTH_HAMMER1.get());
            registerToggleAnimation(HexItem.LIGHTNING_DAGGER_L.get());
            registerToggleAnimation(HexItem.LIGHTNING_DAGGER_R.get());
            registerToggleAnimation(HexItem.LIGHTNING_SSWORD_L.get());
            registerToggleAnimation(HexItem.LIGHTNING_SSWORD_R.get());
            registerToggleAnimation(HexItem.BLOOD_SWORD.get());

            registerToggleDrillAnimation(HexItem.EARTH_HAMMER1.get());
        });
    }

    public static void registerToggleAnimation(Item item) {
        net.minecraft.client.renderer.item.ItemProperties.register(item, prefix(Constants.NBT.AW_State),
                (stack, world, entity, seed) -> ((IHexblade) stack.getItem()).getAwakened(stack) ? 1.0F : 0.0F);
    }

    public static void registerToggleDrillAnimation(Item item) {
        net.minecraft.client.renderer.item.ItemProperties.register(item, prefix(Constants.NBT.MiningSwitch),
                (stack, world, entity, seed) -> stack.getOrCreateTag().getBoolean(Constants.NBT.MiningSwitch) ? 1.0F : 0.0F);
    }
}
