package alexthw.hexblades.client.render.tile;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.blocks.tile_entities.FirePedestalTileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FirePedestalRenderer extends GeoBlockRenderer<FirePedestalTileEntity> {
    public FirePedestalRenderer() {
        super(new GeoModel<FirePedestalTileEntity>() {
            @Override
            public ResourceLocation getAnimationResource(FirePedestalTileEntity animatable) {
                return new ResourceLocation(Hexblades.MODID, "animations/animation.hexblades.fire_pedestal.json");
            }

            @Override
            public ResourceLocation getModelResource(FirePedestalTileEntity object) {
                return new ResourceLocation(Hexblades.MODID, "geo/fire_pedestal.geo.json");
            }

            @Override
            public ResourceLocation getTextureResource(FirePedestalTileEntity object) {
                return new ResourceLocation(Hexblades.MODID, "textures/custom_models/fire_pedestal.png");
            }
        });
    }
}
