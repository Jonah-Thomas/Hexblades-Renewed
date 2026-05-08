package alexthw.hexblades.client.render.models;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.entity.FireElementalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import javax.annotation.Nullable;

public class FireElementalModel extends GeoModel<FireElementalEntity> {

    @Override
    public ResourceLocation getModelResource(FireElementalEntity entity) {
        return new ResourceLocation(Hexblades.MODID, "geo/fire_elemental_v2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FireElementalEntity entity) {
        return new ResourceLocation(Hexblades.MODID, "textures/entity/fire_elemental_v2.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FireElementalEntity entity) {
        return new ResourceLocation(Hexblades.MODID, "animations/animation.hexblades.fe.json");
    }

    @Override
    public void setCustomAnimations(FireElementalEntity entity, long instanceId, @Nullable AnimationState<FireElementalEntity> animationState) {
        super.setCustomAnimations(entity, instanceId, animationState);
        if (animationState == null) return;
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head == null) return;
        EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        if (extraData == null) return;
        head.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
        head.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
    }
}
