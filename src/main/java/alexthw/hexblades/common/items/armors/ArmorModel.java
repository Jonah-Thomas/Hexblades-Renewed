package alexthw.hexblades.common.items.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

// TODO: ArmorModel uses legacy BipedModel/ModelRenderer patterns that no longer apply in 1.20.1.
// ModelPart construction in 1.20.1 requires PartDefinition and cannot be instantiated directly.
// This class is effectively dead code since HexWArmor uses GeckoLib for rendering.
// Kept as a stub for compilation; real armor rendering goes through HexWArmorRenderer (GeckoLib).
public abstract class ArmorModel<T extends LivingEntity> extends HumanoidModel<T> {
    EquipmentSlot slot;

    public ArmorModel(EquipmentSlot slot, ModelPart root) {
        super(root);
        this.slot = slot;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        hat.visible = false;
        body.visible = leftArm.visible = rightArm.visible = head.visible = leftLeg.visible = rightLeg.visible = false;
        if (this.slot == EquipmentSlot.CHEST) {
            body.visible = true;
            leftArm.visible = true;
            rightArm.visible = true;
        }
        if (this.slot == EquipmentSlot.HEAD) {
            head.visible = true;
        }
        if (this.slot == EquipmentSlot.FEET) {
            leftLeg.visible = true;
            rightLeg.visible = true;
        }
        if (this.slot == EquipmentSlot.LEGS) {
            body.visible = true;
            leftLeg.visible = true;
            rightLeg.visible = true;
        }
        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelPart part, float x, float y, float z) {
        part.xRot = x;
        part.yRot = y;
        part.zRot = z;
    }
}
