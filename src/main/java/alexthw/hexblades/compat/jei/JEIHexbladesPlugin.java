package alexthw.hexblades.compat.jei;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.recipes.ArmorFocusRecipe;
import alexthw.hexblades.recipes.WarlockArmorDye;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIHexbladesPlugin implements IModPlugin {

    private static final ResourceLocation UID = new ResourceLocation(Hexblades.MODID, "main");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getCraftingCategory().addCategoryExtension(ArmorFocusRecipe.class, ArmorFocusRecipeWrapper::new);
        registration.getCraftingCategory().addCategoryExtension(WarlockArmorDye.class, WarlockArmorDyeWrapper::new);
    }
}
