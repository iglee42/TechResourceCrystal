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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.decoration.ArmorStand;
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

    private int tick, tickSecond, crystalSecond, startSecond,startTick;
    private Container c = new SimpleContainer(1);
    private Level lastLevel;
    private BlockState lastState;
    private BlockPos lastPos;
    private boolean start;
    private CrystaliserRecipe currentRecipe;
    private BlockState stockedBlock;
    private ArmorStand missingStand;
    private ArmorStand timeStand;

    public CrystaliserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntity.CRYSTALISER.get(), pos, state);
    }

    public void initStands(Level lvl,BlockPos pos) {
        missingStand = new ArmorStand(lvl,pos.getX() +0.5,pos.getY() - 1,pos.getZ()+0.5);
        timeStand = new ArmorStand(lvl,pos.getX()+0.5,pos.getY() - 0.8,pos.getZ()+0.5);
        missingStand.setNoGravity(true);
        timeStand.setNoGravity(true);
        missingStand.setInvisible(true);
        timeStand.setInvisible(true);
        missingStand.noCulling = true;
        timeStand.noCulling = true;
        missingStand.blocksBuilding = false;
        timeStand.blocksBuilding = false;
        lvl.addFreshEntity(missingStand);
        lvl.addFreshEntity(timeStand);

    }

    public void tick(Level lvl, BlockPos pos, BlockState state) {
        if (tick == 20) {
            tick = 0;
            tickSecond += 1;
            startSecond += 1;
        }
        if (missingStand == null) initStands(lvl,pos);
        missingStand.setCustomNameVisible(missingStand.getCustomName() != null && !missingStand.getCustomName().getString().equals("Armor Stand"));
        lastLevel = lvl;
        lastState = state;
        lastPos = pos;
        startTick += 1;
        tick += 1;
        BlockPos upPos = pos.offset(0,1,0);
        if (!lvl.getBlockState(upPos).isAir()) {
            deleteStands();
        }


        if (tickSecond == 1) {
            second(lvl, pos, state);
            tickSecond = 0;
        }
    }

    public void deleteStands(){
        if (missingStand != null) {
            this.missingStand.remove(Entity.RemovalReason.KILLED);}
        if (timeStand != null ){
            this.timeStand.remove(Entity.RemovalReason.KILLED);
        }
        this.missingStand = null;
        this.timeStand = null;

    }
    public void breakBlock(Level level, BlockPos pos){
        this.deleteStands();
        if (this.start && this.stockedBlock != null)this.level.setBlockAndUpdate(pos.offset(0,-1,0),this.stockedBlock);
    }

    private void second(Level pLevel, BlockPos pPos, BlockState pState) {
        CrystaliserRecipe recipe = pLevel
                .getRecipeManager()
                .getAllRecipesFor(CrystaliserRecipe.Type.INSTANCE)
                .stream()
                .filter(r -> CrystaliserBlockEntity.testBlock(r,this.stockedBlock))
                .findFirst()
                .orElse(null);
        currentRecipe = recipe;
        start = testStart(pState, recipe);
        if (missingStand == null) initStands(pLevel,pPos);
        if (start) {
            crystalSecond += 1;
            missingStand.setCustomName(new TextComponent("Recipe : ").append(new TranslatableComponent(recipe.getResultItem().getDescriptionId())));
            timeStand.setCustomName(new TextComponent("Remaining Time : " + (30 - crystalSecond)));
            timeStand.setCustomNameVisible(true);
            if (crystalSecond == 30) {
                crystalSecond = 0;
                BlockState emptyState = pState;
                emptyState = emptyState.setValue(BlockCrystaliser.LEFT, false).setValue(BlockCrystaliser.RIGHT, false).setValue(BlockCrystaliser.MOLD, true);
                this.stockedBlock = null;
                pLevel.setBlockAndUpdate(pPos, emptyState);
                Block.popResource(pLevel, pPos.offset(0, 1, 0), recipe.getResultItem());
                //c.setItem(0,new ItemStack(Blocks.AIR));
            }
        } else {
            timeStand.setCustomNameVisible(false);
            if (pState.getValue(BlockCrystaliser.LEFT) && pState.getValue(BlockCrystaliser.RIGHT) && pState.getValue(BlockCrystaliser.MOLD)) {
                if (level.getBlockState(pPos.offset(0,-1,0)).isAir()){
                    missingStand.setCustomName(new TextComponent("Waiting a block under"));
                } else {
                    if (pLevel.getRecipeManager().getAllRecipesFor(CrystaliserRecipe.Type.INSTANCE).stream().noneMatch(r -> CrystaliserBlockEntity.testBlock(r,level.getBlockState(pPos.offset(0,-1,0))))){
                        missingStand.setCustomName(new TextComponent("The block under is invalid"));
                    } else {
                        missingStand.setCustomName(new TextComponent("Waiting a redstone signal"));
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
                missingStand.setCustomName(new TextComponent(missing));
            }
        }

    }

    public static boolean testBlock(CrystaliserRecipe r,BlockState stockedBlock) {
        if (stockedBlock == null) return false;
        boolean flag = Arrays.stream(r.getIngredient().getItems()).anyMatch(s -> s.getItem() == stockedBlock.getBlock().asItem());
        boolean flag1 = false;
        try {
            flag1 = hasAllAskedProperties(r,stockedBlock);
        } catch (UnknowPropertyException e) {
            e.printStackTrace();
        }
        return flag && flag1;
    }

    private static boolean hasAllAskedProperties(CrystaliserRecipe r,BlockState stockedBlock) throws UnknowPropertyException {
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
        tag.put("missingStand", missingStand.serializeNBT());
        tag.put("timeStand", timeStand.serializeNBT());
        deleteStands();
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

    private static int getPropType(Property<?> prop) {
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
        this.missingStand = EntityType.ARMOR_STAND.create(this.lastLevel);
        if (level != null) this.missingStand.load((CompoundTag) tag.get("missingStand"));
        this.timeStand = EntityType.ARMOR_STAND.create(this.lastLevel);
        if (level != null) this.timeStand.load((CompoundTag) tag.get("timeStand"));
        this.level.addFreshEntity(this.missingStand);
        this.level.addFreshEntity(this.timeStand);
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
        if (pLevel.getRecipeManager().getAllRecipesFor(CrystaliserRecipe.Type.INSTANCE).stream().noneMatch(r ->  CrystaliserBlockEntity.testBlock(r,block))){
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

    public CrystaliserRecipe getCurrentRecipe() {
        return currentRecipe;
    }
}
