package alexthw.hexblades.client.render.tile;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.blocks.tile_entities.SwordStandTileEntity;
import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.common.items.dulls.Hammer_dull;
import alexthw.hexblades.common.items.tier1.EarthHammer1;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import com.mojang.blaze3d.vertex.VertexConsumer;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SwordStandRenderer extends GeoBlockRenderer<SwordStandTileEntity> {

    public SwordStandRenderer(BlockEntityRendererProvider.Context context) {
        super(new GeoModel<SwordStandTileEntity>() {
            @Override
            public ResourceLocation getModelResource(SwordStandTileEntity object) {
                return new ResourceLocation(Hexblades.MODID, "geo/sword_stand.geo.json");
            }

            @Override
            public ResourceLocation getTextureResource(SwordStandTileEntity object) {
                return new ResourceLocation(Hexblades.MODID, "textures/custom_models/sword_stand.png");
            }

            @Override
            public ResourceLocation getAnimationResource(SwordStandTileEntity animatable) {
                return new ResourceLocation(Hexblades.MODID, "animations/animation.hexblades.sword_stand.json");
            }
        });
    }

    @Override
    public void postRender(PoseStack poseStack, SwordStandTileEntity tileEntity, BakedGeoModel model, MultiBufferSource bufferIn, VertexConsumer buffer, boolean isReRender, float partialTicks, int combinedLightIn, int combinedOverlayIn, float red, float green, float blue, float alpha) {
        if (isReRender) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        ItemStack iStack = tileEntity.stack;
        if (!iStack.isEmpty() && mc.level != null) {
            poseStack.pushPose();
            Item item = iStack.getItem();
            if ((item instanceof EarthHammer1) || (item instanceof Hammer_dull)) {
                poseStack.translate(0.05D, 0.3D, 0.1D);
            } else if (item instanceof SwordItem || item instanceof IHexblade) {
                poseStack.translate(0.05D, 0.4D, 0.1D);
                poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(45.0F));
            }
            poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(2.0F * ((float) (mc.level.getGameTime() % 360L) + partialTicks)));
            mc.getItemRenderer().renderStatic(iStack, ItemDisplayContext.GROUND, combinedLightIn, combinedOverlayIn, poseStack, bufferIn, mc.level, 0);
            poseStack.popPose();
        }
    }
}
