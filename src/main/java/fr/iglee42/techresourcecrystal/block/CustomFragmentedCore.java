package fr.iglee42.techresourcecrystal.block;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModItem;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CustomFragmentedCore extends Block {
    public static final IntegerProperty CRYSTAL = IntegerProperty.create("crystal",0,3);
    private String type = "water";
    public CustomFragmentedCore(String type) {
        super(Properties.of(Material.ICE_SOLID).strength(10.5F, 3600000.0F).noOcclusion().sound(SoundType.AMETHYST));
        this.registerDefaultState(this.defaultBlockState().setValue(CRYSTAL,0));
        this.type = type;
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_60537_, LootContext.Builder p_60538_) {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ModBlock.getCrystalCore(type)));
        drops.add(new ItemStack(ModItem.getFragmentedCrystal(type),p_60537_.getValue(CRYSTAL)));
        return drops;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {
        return super.getStateForPlacement(p_196258_1_).setValue(CRYSTAL,0);
    }
    @Override
    public InteractionResult use(BlockState p_225533_1_, Level p_225533_2_, BlockPos p_225533_3_, Player p_225533_4_, InteractionHand p_225533_5_, BlockHitResult p_225533_6_) {
        if (p_225533_4_.getMainHandItem().getItem() != Items.AIR){
            if (p_225533_4_.getMainHandItem().getItem() == ModItem.getFragmentedCrystal(type)){
                if (p_225533_1_.getValue(CRYSTAL) < 3){
                    p_225533_2_.setBlock(p_225533_3_,p_225533_1_.setValue(CRYSTAL, p_225533_1_.getValue(CRYSTAL) +1),0);
                    if (!p_225533_4_.isCreative())p_225533_4_.getMainHandItem().setCount(p_225533_4_.getMainHandItem().getCount() -1);
                    return InteractionResult.CONSUME;
                }
            }
        }

        return InteractionResult.PASS;
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        if (CRYSTAL == null) TechResourcesCrystal.LOGGER.warn("Crystal property is null !");
        p_206840_1_.add(CRYSTAL);
        super.createBlockStateDefinition(p_206840_1_);
    }


    public String getType() {
        return type;
    }


}
