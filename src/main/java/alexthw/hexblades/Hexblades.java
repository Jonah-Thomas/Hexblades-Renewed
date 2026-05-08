package alexthw.hexblades;

import alexthw.hexblades.client.ClientEvents;
import alexthw.hexblades.registers.*;
import alexthw.hexblades.util.CompatUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(Hexblades.MODID)
public class Hexblades {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "hexblades";

    // Creative tab registered via DeferredRegister
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("hexblades", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.hexblades"))
                    .icon(() -> new ItemStack(HexItem.PATRON_SOUL2.get()))
                    .displayItems((params, output) -> {
                        // Items are added by BuildCreativeModeTabContentsEvent in CreativeTabEvents.java
                    })
                    .build()
    );

    public Hexblades() {
        IEventBus hexbus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);

        GeckoLib.initialize();
        hexbus.addListener(this::setup);

        CompatUtil.check();
        Registry.init(hexbus);
        HexRegistry.init();
        TABS.register(hexbus);

        hexbus.addListener(this::onBuildCreativeTab);
        MinecraftForge.EVENT_BUS.register(new CreativeTabEvents());
        MinecraftForge.EVENT_BUS.register(alexthw.hexblades.codex.CodexHexChapters.class);

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            MinecraftForge.EVENT_BUS.register(new ClientEvents());
            return new Object();
        });

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            HexRegistry.post_init();
        });
        this.registerPlacements();
    }

    private void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().equals(TAB.getKey())) return;
        ForgeRegistries.ITEMS.getEntries().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(MODID))
                .sorted(java.util.Comparator.comparing(e -> e.getKey().location().getPath()))
                .forEach(e -> event.accept(e.getValue()));
    }

    private void registerPlacements() {
        SpawnPlacements.register(
                HexEntityType.FIRE_ELEMENTAL.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules
        );
    }
}
