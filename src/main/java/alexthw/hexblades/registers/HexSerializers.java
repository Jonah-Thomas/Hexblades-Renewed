package alexthw.hexblades.registers;

import alexthw.hexblades.commands.args.DeityArgumentType;
import alexthw.hexblades.commands.args.FactArgumentType;
import alexthw.hexblades.commands.args.KnowledgeArgumentType;
import alexthw.hexblades.recipes.ArmorFocusRecipe;
import alexthw.hexblades.recipes.WarlockArmorDye;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.RegistryObject;

import static alexthw.hexblades.util.HexUtils.prefix;

public class HexSerializers {

    public static final RegistryObject<RecipeSerializer<?>> ARMOR_FOCUS_SERIALIZER =
            Registry.RECIPE_SERIALIZERS.register("armor_focus_attach", () -> ArmorFocusRecipe.SERIALIZER);

    public static final RegistryObject<RecipeSerializer<?>> DYE_WARLOCK_SERIALIZER =
            Registry.RECIPE_SERIALIZERS.register("dye_warlock_armor", () -> WarlockArmorDye.SERIALIZER);

    public static void registerCmdArgTypesSerializers() {
        register(prefix("deity"), DeityArgumentType.class, SingletonArgumentInfo.contextFree(DeityArgumentType::new));
        register(prefix("knowledge"), KnowledgeArgumentType.class, SingletonArgumentInfo.contextFree(KnowledgeArgumentType::new));
        register(prefix("fact"), FactArgumentType.class, SingletonArgumentInfo.contextFree(FactArgumentType::new));
    }

    private static <T extends ArgumentType<?>, I extends ArgumentTypeInfo.Template<T>> void register(
            ResourceLocation key, Class<T> clazz, ArgumentTypeInfo<T, I> info) {
        ArgumentTypeInfos.registerByClass(clazz, info);
    }
}
