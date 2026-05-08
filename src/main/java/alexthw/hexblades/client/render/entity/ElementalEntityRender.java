package alexthw.hexblades.client.render.entity;

import alexthw.hexblades.client.render.models.MinionElementalModel;
import alexthw.hexblades.common.entity.BaseElementalEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ElementalEntityRender extends GeoEntityRenderer<BaseElementalEntity> {

    public ElementalEntityRender(EntityRendererProvider.Context context) {
        super(context, new MinionElementalModel());
    }

    @Override
    public ResourceLocation getTextureLocation(BaseElementalEntity entity) {
        return null;
    }
}
