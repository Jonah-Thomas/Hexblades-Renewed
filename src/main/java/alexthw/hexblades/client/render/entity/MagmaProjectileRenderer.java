package alexthw.hexblades.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;

public class MagmaProjectileRenderer<T extends Entity & ItemSupplier> extends ThrownItemRenderer<T> {
    public MagmaProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
}
