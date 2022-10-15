package fr.iglee42.techresourcecrystal.block.entity;

import com.google.gson.JsonPrimitive;
import fr.iglee42.techresourcecrystal.block.BlockCrystaliser;
import fr.iglee42.techresourcecrystal.customize.exception.PropertyTypeNotSupportedException;
import fr.iglee42.techresourcecrystal.customize.exception.UnknowPropertyException;
import fr.iglee42.techresourcecrystal.customize.crystaliserRecipe.CrystaliserRecipe;
import fr.iglee42.techresourcecrystal.init.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.*;

public class CrystaliserBlockEntity extends BlockEntity {

    private int tick, tickSecond, crystalSecond, startSecond;
    private Container c = new SimpleContainer(1);
    private Level lastLevel;
    private BlockState lastState;
    private boolean start;
    private BlockState stockedBlock;

    public CrystaliserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntity.CRYSTALISER.get(), pos, state);
    }

    public void tick(Level lvl, BlockPos pos, BlockState state) {
        if (tick == 20) {
            tick = 0;
            tickSecond += 1;
            startSecond += 1;
        }
        tick += 1;

        if (tickSecond == 1) {
            second(lvl, pos, state);
            tickSecond = 0;
        }
    }

    private void second(Level pLevel, BlockPos pPos, BlockState pState) {
        CrystaliserRecipe recipe = pLevel
                .getRecipeManager()
                .getAllRecipesFor(CrystaliserRecipe.Type.INSTANCE)
                .stream()
                .filter(this::testBlock)
                .findFirst()
                .orElse(null);
        start = testStart(pState, recipe);
        if (start) {
            crystalSecond += 1;
            if (crystalSecond == 30) {
                crystalSecond = 0;
                BlockState emptyState = pState;
                emptyState = emptyState.setValue(BlockCrystaliser.LEFT, false).setValue(BlockCrystaliser.RIGHT, false).setValue(BlockCrystaliser.MOLD, true);
                pLevel.setBlockAndUpdate(pPos, emptyState);
                Block.popResource(pLevel, pPos.offset(0, 1, 0), recipe.getResultItem());
                //c.setItem(0,new ItemStack(Blocks.AIR));
                this.stockedBlock = null;
            }
        }
    }

    private boolean testBlock(CrystaliserRecipe r) {
        if (stockedBlock == null) return false;
        boolean flag = Arrays.stream(r.getIngredient().getItems()).anyMatch(s -> s.getItem() == stockedBlock.getBlock().asItem());
        boolean flag1 = false;
        try {
            flag1 = hasAllAskedProperties(r);
        } catch (UnknowPropertyException e) {
            e.printStackTrace();
        }
        return flag && flag1;
    }

    private boolean hasAllAskedProperties(CrystaliserRecipe r) throws UnknowPropertyException {
        HashMap<String, JsonPrimitive> recipeProps = new HashMap<String, JsonPrimitive>(r.getRequiredProperties());
        if (recipeProps.isEmpty()) return true;
        for (Property<?> prop : stockedBlock.getProperties()) {
            if (recipeProps.keySet().stream().anyMatch(p -> p.equals(prop.getName()))) {
                String key = recipeProps.keySet().stream().filter(p -> Objects.equals(p, prop.getName())).findFirst().get();
                JsonPrimitive prim = recipeProps.get(key);
                if (getPropType(prop) == 2) {
                    EnumProperty<?> enProp = (EnumProperty<?>) prop;
                    List<String> posValues = new ArrayList<>();
                    for (Object val : enProp.getValueClass().getEnumConstants().clone()) {
                        posValues.add(val.toString().toLowerCase());
                    }
                    if (posValues.contains(prim.getAsString())) recipeProps.remove(key);
                    else
                        throw new IllegalArgumentException("The property " + prop.getName() + " has no value " + prim.getAsString());

                } else {
                    if (getPropType(prop) == 1) {
                        if (!prop.getPossibleValues().contains(prim.getAsInt())) {
                            throw new IllegalArgumentException(key + " property is not in the property range.");
                        } else {
                           int stockedInt = Integer.parseInt(stockedBlock.getValue(prop).toString());
                            if (prim.isNumber() && prim.getAsInt() == stockedInt){
                                recipeProps.remove(key);
                            }
                        }
                    } else if (getPropType(prop) == 0) {
                        boolean stockedBool = Boolean.parseBoolean(stockedBlock.getValue(prop).toString());
                       if (prim.isBoolean() && prim.getAsBoolean() == stockedBool) recipeProps.remove(key);
                    }
                }
            } else {
                throw new UnknowPropertyException("A property from crystaliser recipe : " + r.getId().toString() + " is unknow !");
            }
        }
        return recipeProps.isEmpty();
    }

    private boolean testStart(BlockState pState, CrystaliserRecipe recipe) {
        return recipe != null && pState.getValue(BlockCrystaliser.LEFT) && pState.getValue(BlockCrystaliser.RIGHT) && pState.getValue(BlockCrystaliser.MOLD);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("start", start);
        tag.putInt("tick", tick);
        tag.putInt("tickSecond", tickSecond);
        tag.putInt("crystalSecond", crystalSecond);
        tag.putInt("startSecond", startSecond);
        try {
            if (stockedBlock != null)saveProps(tag);//tag.putString("block",stockedBlock.getBlock().getRegistryName().toString());
        } catch (PropertyTypeNotSupportedException e) {
            e.printStackTrace();
        }
        //tag.put("properties",saveProps());
    }

    private void saveProps(CompoundTag tag) throws PropertyTypeNotSupportedException {
        tag.putString("block", stockedBlock.getBlock().getRegistryName().toString());
        CompoundTag props = new CompoundTag();
        for (Property<?> prop : stockedBlock.getProperties()) {
            //System.out.println(prop.getValueClass().getTypeName());
            switch (getPropType(prop)) {
                case 0:
                    props.putBoolean(prop.getName(), Boolean.parseBoolean(stockedBlock.getValue(prop).toString()));
                    break;
                case 1:
                    props.putInt(prop.getName(), Integer.parseInt(stockedBlock.getValue(prop).toString()));
                    break;
                case 2:
                    props.putString(prop.getName(), stockedBlock.getValue(prop).toString());
                    break;
                case -1:
                    throw new PropertyTypeNotSupportedException("Property type is not supported");
                default:
                    break;
            }
        }
        tag.put("properties",props);
    }

    private int getPropType(Property<?> prop) {
        String[] typeNameTab = prop.getValueClass().getTypeName().split("\\.");
        String typeName = typeNameTab[typeNameTab.length-1];
        if (prop.getValueClass().isEnum()) return 2;
        if (typeName.equals("Boolean")) return 0;
        if (typeName.equals("Integer")) return 1;
        return -1;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.start = tag.getBoolean("start");
        this.tick = tag.getInt("tick");
        this.tickSecond = tag.getInt("tickSecond");
        this.crystalSecond = tag.getInt("crystalSecond");
        this.startSecond = tag.getInt("startSecond");
        try {
            loadProps(tag);
        } catch (PropertyTypeNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void loadProps(CompoundTag tag) throws PropertyTypeNotSupportedException {
        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(Registry.BLOCK.get(new ResourceLocation(tag.getString("block"))));
        StateDefinition<Block,BlockState> tempDef = builder.create(Block::defaultBlockState,BlockState::new);
        BlockState tempState = tempDef.any();
        CompoundTag props = tag.getCompound("properties");
        for (Property pr : tempState.getProperties()){
            switch (getPropType(pr)) {
                case 0:
                    tempState = tempState.setValue(pr,props.getBoolean(pr.getName()));
                    break;
                case 1:
                    tempState = tempState.setValue(pr,props.getInt(pr.getName()));
                    break;
                case 2:
                    tempState = tempState.setValue(pr,props.getString(pr.getName()));
                    break;
                case -1:
                    throw new PropertyTypeNotSupportedException("Property type is not supported");
                default:
                    break;
            }
        }
    }

    public void setBlock(BlockState block, Level pLevel, BlockPos pPos) {
        if (block.getBlock() == Blocks.AIR) return;
        this.stockedBlock = block;
        if (pLevel.getRecipeManager().getAllRecipesFor(CrystaliserRecipe.Type.INSTANCE).stream().noneMatch(this::testBlock)){
            this.stockedBlock = null;
            return;
        }
        this.stockedBlock = block;
        pLevel.destroyBlock(pPos, false);
    }

    public boolean isStart() {
        return start;
    }

    public int getStartSecond() {
        return crystalSecond;
    }
}
