package alexthw.hexblades.client.render.entity;

import alexthw.hexblades.client.render.models.EarthElementalModel;
import alexthw.hexblades.common.entity.EarthElementalEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EarthElementalER extends GeoEntityRenderer<EarthElementalEntity> {
    public EarthElementalER(EntityRendererProvider.Context context) {
        super(context, new EarthElementalModel());
    }
}
