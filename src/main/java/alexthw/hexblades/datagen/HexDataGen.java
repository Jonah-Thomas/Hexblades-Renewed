package alexthw.hexblades.datagen;

import alexthw.hexblades.Hexblades;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Hexblades.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class HexDataGen {
    private HexDataGen() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<net.minecraft.core.HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        HexBlockTagsProvider blockTagsProvider = new HexBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        gen.addProvider(event.includeClient(), new HexItemModelProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeClient(), new HexBlockStateProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeServer(), blockTagsProvider);
        gen.addProvider(event.includeServer(), new HexItemTagProvider(packOutput, lookupProvider, blockTagsProvider, existingFileHelper));
        gen.addProvider(event.includeServer(), new HexRecipeProvider(packOutput));
        gen.addProvider(event.includeServer(), new HexLootTableProvider(packOutput));
    }
}
