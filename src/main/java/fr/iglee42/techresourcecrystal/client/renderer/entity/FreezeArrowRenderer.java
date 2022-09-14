package fr.iglee42.techresourcecrystal.client.renderer.entity;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.entity.projectile.FreezeArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FreezeArrowRenderer extends ArrowRenderer<FreezeArrow> {
    public FreezeArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FreezeArrow entity) {
        Item arrowItem = entity.getPickupItem().getItem();
        return new ResourceLocation(TechResourcesCrystal.MODID,"textures/entity/freeze_arrow.png");
    }
}
