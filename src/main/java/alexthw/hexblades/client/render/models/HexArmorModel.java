package alexthw.hexblades.client.render.models;

import alexthw.hexblades.common.items.armors.HexWArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static alexthw.hexblades.util.HexUtils.prefix;

public class HexArmorModel extends GeoModel<HexWArmor> {

    @Override
    public ResourceLocation getModelResource(HexWArmor armor) {
        return prefix("geo/armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HexWArmor armor) {
        return prefix("textures/entity/hex_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HexWArmor animatable) {
        return null;
    }
}
