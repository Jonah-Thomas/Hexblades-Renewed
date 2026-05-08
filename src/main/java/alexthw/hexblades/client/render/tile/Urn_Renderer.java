package alexthw.hexblades.client.render.tile;

import alexthw.hexblades.common.blocks.tile_entities.EverfullUrnTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class Urn_Renderer implements BlockEntityRenderer<EverfullUrnTileEntity> {

    public Urn_Renderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(EverfullUrnTileEntity tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
    }
}
