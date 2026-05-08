package alexthw.hexblades.client.render.entity;

import alexthw.hexblades.client.render.models.FireElementalModel;
import alexthw.hexblades.common.entity.FireElementalEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import elucent.eidolon.client.particle.Particles;
import elucent.eidolon.registries.EidolonParticles;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireElementalER extends GeoEntityRenderer<FireElementalEntity> {

    public FireElementalER(EntityRendererProvider.Context context) {
        super(context, new FireElementalModel());
    }

    @Override
    public void render(FireElementalEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
        Particles.create(EidolonParticles.FLAME_PARTICLE).setAlpha(0.7F, 0.0F).setScale(0.35F, 0.0F).setLifetime(10).randomOffset(0.3D, 0.3D).randomVelocity(0, -0.15F).setColor(1.5F, 0.5F, 0.25F, 0.5F, 0.25F, 0.1F).spawn(entity.level(), entity.getX(), entity.getY() + 0.75F, entity.getZ());
        if (entity.isCannonLoaded()) {
            Particles.create(EidolonParticles.WISP_PARTICLE).setAlpha(0.5F).setScale(0.25F, 0.0F).setLifetime(10).randomOffset(0.1D, 0.1D).setColor(1.5F, 0.75F, 0.25F, 1.0F, 0.25F, 0.1F).spawn(entity.level(), entity.getX() + Math.cos(Math.toRadians(entity.yBodyRot + 90)), entity.getY() + 2.0F, entity.getZ() + Math.sin(Math.toRadians(entity.yBodyRot + 90)));
            Particles.create(EidolonParticles.FLAME_PARTICLE).setAlpha(0.5F).setScale(0.25F, 0.0F).setLifetime(10).randomOffset(0.1D, 0.1D).setColor(1.0F, 0.5F, 0.15F, 0.65F, 0.25F, 0.075F).spawn(entity.level(), entity.getX() + Math.cos(Math.toRadians(entity.yBodyRot + 90)), entity.getY() + 2.0F, entity.getZ() + Math.sin(Math.toRadians(entity.yBodyRot + 90)));
        }
    }
}
