package fr.iglee42.techresourcecrystal.block;

import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomFragmentedCrystal extends Block {
    private String type = "water";

    public CustomFragmentedCrystal(String type) {
        super(Properties.of(Material.ICE_SOLID).strength(1.5f).sound(SoundType.AMETHYST));
        this.type = type;
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_60537_, LootContext.Builder p_60538_) {
        List<ItemStack> drops = new ArrayList<>();
        if (TypesConstants.getType(type).dropFragmented())drops.add(new ItemStack(ModItem.getFragmentedCrystal(type),new Random().nextInt(2)+1));
        return drops;
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (TypesConstants.getType(type).hasCoreRecipe()){
            if (p_60506_.getMainHandItem().getItem() != Items.AIR){
                if (p_60506_.getMainHandItem().getItem() == fr.iglee42.igleelib.common.init.ModItem.LAVA_SHARD.get()) {
                    p_60504_.setBlockAndUpdate(p_60505_, ModBlock.getCrystalCore(type).defaultBlockState());
                    if (!p_60506_.isCreative())p_60506_.getMainHandItem().setCount(p_60506_.getMainHandItem().getCount() -1);
                    return InteractionResult.CONSUME;
                }
            }
        }
        return InteractionResult.PASS;
    }
}
