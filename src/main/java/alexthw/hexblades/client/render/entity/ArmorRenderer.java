package alexthw.hexblades.client.render.entity;

import alexthw.hexblades.client.render.models.HexArmorModel;
import alexthw.hexblades.common.items.armors.HexWArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ArmorRenderer extends GeoArmorRenderer<HexWArmor> {
    public ArmorRenderer() {
        super(new HexArmorModel());
    }
}
