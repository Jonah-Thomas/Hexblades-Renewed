package alexthw.hexblades.client.render.models;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.entity.BaseElementalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public abstract class BaseElementalModel extends GeoModel<BaseElementalEntity> {

    @Override
    public ResourceLocation getModelResource(BaseElementalEntity entity) {
        return new ResourceLocation(Hexblades.MODID, "geo/.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BaseElementalEntity entity) {
        return new ResourceLocation(Hexblades.MODID, "textures/entity/.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BaseElementalEntity entity) {
        return new ResourceLocation(Hexblades.MODID, "animations/hexblades..animation.json");
    }
}
