package fr.iglee42.techresourcecrystal.block;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.block.entity.CrystaliserBlockEntity;
import fr.iglee42.techresourcecrystal.init.ModItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class BlockCrystaliser extends BaseEntityBlock {

    public static final BooleanProperty RIGHT = BooleanProperty.create("right");
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
    public static final BooleanProperty MOLD = BooleanProperty.create("mold");
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public Timer timer;

    public BlockCrystaliser() {
        super(Properties.of(Material.HEAVY_METAL).noOcclusion().strength(1.5f));
        this.registerDefaultState(this.defaultBlockState().setValue(MOLD, false).setValue(RIGHT, false).setValue(LEFT, false));
    }


    @Override
    public List<ItemStack> getDrops(BlockState p_60537_, LootContext.Builder p_60538_) {
        List<ItemStack> drop = new ArrayList<>();
        drop.add(new ItemStack(this));
        if (p_60537_.getValue(MOLD)) drop.add(new ItemStack(ModItem.CRYSTALISER_MOLD.get()));
        return drop;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof CrystaliserBlockEntity tile) {
            if (player.getMainHandItem().getItem() != Items.AIR) {
                if (player.getMainHandItem().getItem() == ModItem.CRYSTALISER_MOLD.get()) {
                    if (!state.getValue(MOLD)) {
                        level.setBlock(pos, state.setValue(MOLD, true), 0);
                        if (!player.isCreative())
                            player.getMainHandItem().setCount(player.getMainHandItem().getCount() - 1);
                        return InteractionResult.CONSUME;
                    }
                } else if (player.getMainHandItem().getItem() == Items.LAVA_BUCKET) {
                    if (!state.getValue(LEFT)) {
                        level.setBlock(pos, state.setValue(LEFT, true), 0);
                        if (!player.isCreative())
                            player.getMainHandItem().setCount(player.getMainHandItem().getCount() - 1);
                        if (!player.isCreative()) player.addItem(new ItemStack(Items.BUCKET));
                        return InteractionResult.CONSUME;
                    } else if (!state.getValue(RIGHT) && state.getValue(LEFT)) {
                        level.setBlock(pos, state.setValue(RIGHT, true), 0);
                        if (!player.isCreative())
                            player.getMainHandItem().setCount(player.getMainHandItem().getCount() - 1);
                        if (!player.isCreative()) player.addItem(new ItemStack(Items.BUCKET));
                        return InteractionResult.CONSUME;
                    }
                } else if (player.getMainHandItem().getItem() == Items.BUCKET) {
                    if (!tile.isStart()) {
                        if (state.getValue(RIGHT)) {
                            level.setBlock(pos, state.setValue(RIGHT, false), 0);
                            if (!player.isCreative())
                                player.getMainHandItem().setCount(player.getMainHandItem().getCount() - 1);
                            if (!player.isCreative()) player.addItem(new ItemStack(Items.LAVA_BUCKET));
                            return InteractionResult.CONSUME;
                        } else if (!state.getValue(RIGHT) && state.getValue(LEFT)) {
                            level.setBlock(pos, state.setValue(LEFT, false), 0);
                            if (!player.isCreative())
                                player.getMainHandItem().setCount(player.getMainHandItem().getCount() - 1);
                            if (!player.isCreative()) player.addItem(new ItemStack(Items.LAVA_BUCKET));
                            return InteractionResult.CONSUME;
                        }
                    } else {
                        player.displayClientMessage(Component.literal("§cYou can't remove lava the machine is in functionning !"), true);
                    }
                }
            } else {
                if (player.isCrouching()) {
                    if (!tile.isStart()) {
                        if (state.getValue(MOLD)) {
                            level.setBlock(pos, state.setValue(MOLD, false), 0);
                            if (!player.isCreative())
                                player.addItem(new ItemStack(ModItem.CRYSTALISER_MOLD.get()));
                            return InteractionResult.CONSUME;
                        }
                    } else {
                        player.displayClientMessage(Component.literal("§cYou can't remove mold the machine is in functionning !"), true);
                    }
                }
            }
        }


        return InteractionResult.PASS;
    }


    @Override
    public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        if (p_60516_.getBlockEntity(p_60517_) instanceof CrystaliserBlockEntity tile) tile.breakBlock(p_60516_,p_60517_);
        p_60516_.removeBlockEntity(p_60517_);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {
        return super.getStateForPlacement(p_196258_1_).setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite()).setValue(MOLD, false).setValue(RIGHT, false).setValue(LEFT, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        if (RIGHT == null) TechResourcesCrystal.LOGGER.warn("Right property is null !");
        if (LEFT == null) TechResourcesCrystal.LOGGER.warn("Left property is null !");
        if (MOLD == null) TechResourcesCrystal.LOGGER.warn("Mold property is null !");
        if (FACING == null) TechResourcesCrystal.LOGGER.warn("Facing property is null !");
        p_206840_1_.add(FACING);
        p_206840_1_.add(MOLD);
        p_206840_1_.add(RIGHT);
        p_206840_1_.add(LEFT);
        super.createBlockStateDefinition(p_206840_1_);
    }

    @Override
    public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
        if (p_60567_.getBlockEntity(p_60568_) instanceof CrystaliserBlockEntity tile) tile.initStands(p_60567_,p_60568_);
    }

    private boolean redstoneIsActivated(Level world, BlockPos pos) {
        if (world.hasNeighborSignal(pos)) {
            return true;
        }
        return false;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pPos1, boolean p_60514_) {
        if (redstoneIsActivated(pLevel, pPos)) {
            BlockPos newPos = pPos.offset(0, -1, 0);
            if (pLevel.getBlockEntity(pPos) instanceof CrystaliserBlockEntity tile) {
                //Crystal type = TypesConstants.getType(((CustomFragmentedCore)pLevel.getBlockState(newPos).getBlock()).getType());
                if (!tile.isStart()) tile.setBlock(pLevel.getBlockState(newPos),pLevel,newPos);
                //pLevel.destroyBlock(newPos,false);
            }
        }
    }

    private boolean checkNeeded(BlockState bs) {
        return bs.getValue(RIGHT) && bs.getValue(LEFT) && bs.getValue(MOLD);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_60584_) {
        return PushReaction.BLOCK;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new CrystaliserBlockEntity(p_153215_, p_153216_);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        if (level.isClientSide()) {
            return null;
        }
        return (lvl, pos, state, t) -> {
            if (t instanceof CrystaliserBlockEntity tile) {
                tile.tick(lvl, pos, state);
            }
        };
    }
}
