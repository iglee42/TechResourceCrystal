package fr.iglee42.techresourcecrystal.block.entity;

import fr.iglee42.techresourcecrystal.block.BlockCrystaliser;
import fr.iglee42.techresourcecrystal.init.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CrystaliserBlockEntity extends BlockEntity {

    private int second;
    private boolean start;

    public CrystaliserBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntity.CRYSTALISER.get(), pos, state);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, CrystaliserBlockEntity pBlockEntity){

    }

    private void testStart(BlockState pState){
        this.start = pState.getValue(BlockCrystaliser.LEFT) && pState.getValue(BlockCrystaliser.RIGHT) && pState.getValue(BlockCrystaliser.MOLD);
    }
}
