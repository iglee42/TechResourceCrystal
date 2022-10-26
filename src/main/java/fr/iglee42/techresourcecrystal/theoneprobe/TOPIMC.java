package fr.iglee42.techresourcecrystal.theoneprobe;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.block.BlockCrystaliser;
import fr.iglee42.techresourcecrystal.block.CustomFragmentedCore;
import fr.iglee42.techresourcecrystal.block.entity.CrystaliserBlockEntity;
import fr.iglee42.techresourcecrystal.customize.crystaliserRecipe.CrystaliserRecipe;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;


public class TOPIMC implements IProbeInfoProvider, Function<ITheOneProbe, Void> {
    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(TechResourcesCrystal.MODID,"theoneprobe_plugin");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState pState, IProbeHitData iProbeHitData) {
        if (level.getBlockEntity(iProbeHitData.getPos()) instanceof CrystaliserBlockEntity tile){
            if (tile.isStart()){
                iProbeInfo.text(new TextComponent("Recipe : ").append(new TranslatableComponent(tile.getCurrentRecipe().getResultItem().getDescriptionId())));
                ProgressStyle style = new ProgressStyle().showText(false);
                if (tile.getStartSecond() >= 0 && tile.getStartSecond() <10)style = style.filledColor(Color.rgb(255,0,0)).alternateFilledColor(Color.rgb(255,0,0));
                if (tile.getStartSecond() >= 10 && tile.getStartSecond() <20)style = style.filledColor(Color.rgb(255,255,0)).alternateFilledColor(Color.rgb(255,255,0));
                if (tile.getStartSecond() >= 20)style = style.filledColor(Color.rgb(0,255,0)).alternateFilledColor(Color.rgb(0,255,0));
                iProbeInfo.progress(tile.getStartSecond(),30,style);
            } else {
                if (pState.getValue(BlockCrystaliser.LEFT) && pState.getValue(BlockCrystaliser.RIGHT) && pState.getValue(BlockCrystaliser.MOLD)) {
                    if (level.getBlockState(iProbeHitData.getPos().offset(0,-1,0)).isAir()){
                        iProbeInfo.text(new TextComponent("Waiting a block under"));
                    } else {
                        if (level.getRecipeManager().getAllRecipesFor(CrystaliserRecipe.Type.INSTANCE).stream().noneMatch(r -> CrystaliserBlockEntity.testBlock(r,level.getBlockState(iProbeHitData.getPos().offset(0,-1,0))))){
                            iProbeInfo.text(new TextComponent("The block under is invalid"));
                        } else {
                            iProbeInfo.text(new TextComponent("Waiting a redstone signal"));
                        }
                    }
                } else {
                    String missing = "Missing : ";
                    int missLava = 0;
                    if (!pState.getValue(BlockCrystaliser.LEFT)){
                        missLava += 1;
                    }
                    if (!pState.getValue(BlockCrystaliser.RIGHT)){
                        missLava += 1;
                    }
                    if (!pState.getValue(BlockCrystaliser.MOLD)){
                        missing = missing + "Mold" + (missLava > 0 ? " & " : "");
                    }
                    missing = missing + (missLava == 1 ? "1 Lava Bucket" : missLava == 2 ? "2 Lava Buckets" : "");
                    iProbeInfo.text(new TextComponent(missing));
                }
            }
        }
        if (pState.getBlock() instanceof CustomFragmentedCore){
            iProbeInfo.text(new TextComponent("Crystals : " + pState.getValue(CustomFragmentedCore.CRYSTAL) + "/3"));
        }
    }

    @Override
    public Void apply(ITheOneProbe iTheOneProbe) {
        iTheOneProbe.registerProvider(this);
        return null;
    }
}
