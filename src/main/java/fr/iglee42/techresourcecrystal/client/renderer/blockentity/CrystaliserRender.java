package fr.iglee42.techresourcecrystal.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.iglee42.techresourcecrystal.block.BlockCrystaliser;
import fr.iglee42.techresourcecrystal.block.entity.CrystaliserBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrystaliserRender implements BlockEntityRenderer<CrystaliserBlockEntity> {

    public CrystaliserRender(BlockEntityRendererProvider.Context p_173602_) {
    }

    public void render(CrystaliserBlockEntity entity, float p_112345_, PoseStack stack, MultiBufferSource p_112347_, int p_112348_, int p_112349_) {
        Direction direction = entity.getBlockState().getValue(BlockCrystaliser.FACING);

    }
}