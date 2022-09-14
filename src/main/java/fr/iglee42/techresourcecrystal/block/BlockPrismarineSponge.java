package fr.iglee42.techresourcecrystal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class BlockPrismarineSponge extends Block {
    public BlockPrismarineSponge() {
        super(Properties.copy(Blocks.SPONGE));
    }

    public void onPlace(BlockState p_56811_, Level p_56812_, BlockPos p_56813_, BlockState p_56814_, boolean p_56815_) {
        if (!p_56814_.is(p_56811_.getBlock())) {
            this.tryAbsorbWater(p_56812_, p_56813_);
        }
    }

    public void neighborChanged(BlockState p_56801_, Level p_56802_, BlockPos p_56803_, Block p_56804_, BlockPos p_56805_, boolean p_56806_) {
        this.tryAbsorbWater(p_56802_, p_56803_);
    }

    protected void tryAbsorbWater(Level p_56798_, BlockPos p_56799_) {
        this.removeLavaBreadthFirstSearch(p_56798_, p_56799_);
    }

    private void removeLavaBreadthFirstSearch(Level level, BlockPos pos1) {
        int i = 0;
        BlockPos pos = pos1.offset(-6,0,-6);
        for (int x = 0; x < 12; x++){
            for(int y = 0;y < 12;y++){
                for (int z = 0; z < 12; z++){
                    BlockPos newPos = pos.offset(x,y,z);
                    FluidState fluidstate = level.getFluidState(newPos);
                    if (fluidstate.is(FluidTags.LAVA)){
                        level.setBlockAndUpdate(newPos,Blocks.AIR.defaultBlockState());
                        i++;
                    }
                    if (i >= 2048) break;
                }
                if (i >= 2048) break;
            }
            if (i >= 2048) break;
        }

    }
}
