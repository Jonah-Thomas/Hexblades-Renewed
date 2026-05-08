package alexthw.hexblades.client.render.models;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.entity.EarthElementalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EarthElementalModel extends GeoModel<EarthElementalEntity> {

    @Override
    public ResourceLocation getModelResource(EarthElementalEntity entity) {
        return new ResourceLocation(Hexblades.MODID, "geo/earth_elemental.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EarthElementalEntity entity) {
        return new ResourceLocation(Hexblades.MODID, "textures/entity/earth_elemental.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EarthElementalEntity entity) {
        return new ResourceLocation(Hexblades.MODID, "animations/animation.hexblades.ee.idle.json");
    }
}
