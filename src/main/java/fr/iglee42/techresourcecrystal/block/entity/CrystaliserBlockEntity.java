package fr.iglee42.techresourcecrystal.block.entity;

import fr.iglee42.techresourcecrystal.block.BlockCrystaliser;
import fr.iglee42.techresourcecrystal.customize.CustomRecipes;
import fr.iglee42.techresourcecrystal.customize.crystaliserRecipe.CrystaliserRecipe;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModBlockEntity;
import fr.iglee42.techresourcecrystal.init.ModItem;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CrystaliserBlockEntity extends BlockEntity implements Container, RecipeHolder {

    private static int tick,tickSecond,crystalSecond,startSecond;
    private static boolean start;
    private Block block = Blocks.AIR;
    private Level lastLevel;
    private BlockState lastState;
    private final RecipeType<? extends CrystaliserRecipe> recipeType = CustomRecipes.CRYSTALISER;
    private Recipe<?> recipesUsed;

    public CrystaliserBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntity.CRYSTALISER.get(), pos, state);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, CrystaliserBlockEntity pBlockEntity){
        if (tick == 20){
            tick = 0;
            tickSecond += 1;
            startSecond += 1;
        }
        tick +=1;

        if (tickSecond == 1){
            second(pLevel,pPos,pState,pBlockEntity);
            tickSecond = 0;
        }
    }

    private static void second(Level pLevel, BlockPos pPos, BlockState pState, CrystaliserBlockEntity pBlockEntity) {
        testStart(pState,pBlockEntity);
        if (start){
            crystalSecond += 1;
            if (crystalSecond == 30){
                /*start = false;
                crystalSecond = 0;
                BlockState emptyState = pState;
                emptyState = emptyState.setValue(BlockCrystaliser.LEFT,false).setValue(BlockCrystaliser.RIGHT,false);
                pLevel.sendBlockUpdated(pPos,pState,emptyState,0);
                Block.popResource(pLevel, pPos.offset(0, 1, 0), );
                pBlockEntity.setBlock(Blocks.AIR);*/
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("tick",tick);
        tag.putInt("tickSecond",tickSecond);
        tag.putInt("crystalSecond",crystalSecond);
        tag.putInt("startSecond",startSecond);
        tag.putBoolean("start",start);
        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((p_187449_, p_187450_) -> {
            compoundtag.putInt(p_187449_.toString(), p_187450_);
        });
        tag.put("RecipesUsed", compoundtag);
    }

    private static void testStart(BlockState pState, CrystaliserBlockEntity pBlockEntity){
        start = pBlockEntity.recipesUsed != null && pState.getValue(BlockCrystaliser.LEFT) && pState.getValue(BlockCrystaliser.RIGHT) && pState.getValue(BlockCrystaliser.MOLD);
    }

    public boolean isStart() {
        return start;
    }

    public void setBlock(Block name) {
        this.block = name;
    }


    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return block.defaultBlockState().is(Blocks.AIR);
    }

    @Override
    public ItemStack getItem(int p_18941_) {
        return new ItemStack(block);
    }

    @Override
    public ItemStack removeItem(int p_18942_, int p_18943_) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_18951_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int p_18944_, ItemStack p_18945_) {

    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return true;
    }

    @Override
    public void clearContent() {

    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> r) {
        if (r != null) {
            ResourceLocation resourcelocation = r.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return null;
    }
}
