package fr.iglee42.techresourcecrystal.init;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.item.ItemFreezeArrow;
import fr.iglee42.techresourcecrystal.item.ItemCaptureStone;
import fr.iglee42.techresourcecrystal.item.ItemGoatHorn;
import fr.iglee42.techresourcecrystal.item.ItemMobDrop;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItem {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TechResourcesCrystal.MODID);

    public static final RegistryObject<Item> FRAGMENTED_WATER_CRYSTAL = ITEMS.register("fragmented_water_crystal_item",()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(3)));
    public static final RegistryObject<Item> FRAGMENTED_AIR_CRYSTAL = ITEMS.register("fragmented_air_crystal_item",()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(3)));
    public static final RegistryObject<Item> FRAGMENTED_FIRE_CRYSTAL = ITEMS.register("fragmented_fire_crystal_item",()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(3)));
    public static final RegistryObject<Item> FRAGMENTED_EARTH_CRYSTAL = ITEMS.register("fragmented_earth_crystal_item",()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(3)));
    public static final RegistryObject<Item> WATER_CRYSTAL = ITEMS.register("water_crystal",() -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> AIR_CRYSTAL = ITEMS.register("air_crystal",() -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> FIRE_CRYSTAL = ITEMS.register("fire_crystal",() -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> EARTH_CRYSTAL = ITEMS.register("earth_crystal",() -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> UNIFIED_CRYSTAL = ITEMS.register("unified_crystal",() -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)));

    public static final RegistryObject<Item> CRYSTALISER_MOLD = ITEMS.register("crystaliser_mold", () -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)){
        @Override
        public boolean hasContainerItem(ItemStack stack) {
            return true;
        }

        @Override
        public ItemStack getContainerItem(ItemStack itemStack) {
            return itemStack.copy();
        }
    });
    public static final RegistryObject<Item> MOB_MOLD = ITEMS.register("mob_mold", () -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)){
        @Override
        public boolean hasContainerItem(ItemStack stack) {
            return true;
        }

        @Override
        public ItemStack getContainerItem(ItemStack itemStack) {
            return itemStack.copy();
        }

        @Override
        public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
            if (p_41421_.getDisplayName().getString().equals("[LIMachi]")) p_41423_.add(new TextComponent("https://www.youtube.com/c/LIMachi"));
            super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        }
    });
    public static final RegistryObject<Item> MINERALS_MOLD = ITEMS.register("minerals_mold", () -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)){
        @Override
        public boolean hasContainerItem(ItemStack stack) {
            return true;
        }

        @Override
        public ItemStack getContainerItem(ItemStack itemStack) {
            return itemStack.copy();
        }
    });
    public static final RegistryObject<Item> ROCKS_MOLD = ITEMS.register("rocks_mold", () -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)){
        @Override
        public boolean hasContainerItem(ItemStack stack) {
            return true;
        }

        @Override
        public ItemStack getContainerItem(ItemStack itemStack) {
            return itemStack.copy();
        }
    });
    public static final RegistryObject<Item> UNIFIED_MOB_MOLD = ITEMS.register("unified_mob_mold", () -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)){
        @Override
        public boolean hasContainerItem(ItemStack stack) {
            return true;
        }

        @Override
        public ItemStack getContainerItem(ItemStack itemStack) {
            return itemStack.copy();
        }
    });

    public static final RegistryObject<Item> WATER_CRYSTAL_PLATE = ITEMS.register("water_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> AIR_CRYSTAL_PLATE = ITEMS.register("air_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> FIRE_CRYSTAL_PLATE = ITEMS.register("fire_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> EARTH_CRYSTAL_PLATE = ITEMS.register("earth_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));

    public static final RegistryObject<Item> CAPTURE_STONE = ITEMS.register("capture_stone", ()-> new ItemCaptureStone(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> CRYSTALISED_SPAWN_EGG = ITEMS.register("crystalised_spawn_egg", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));

    //Mob Items
    public static final RegistryObject<Item> DOLPHIN_HIDE = ITEMS.register("dolphin_hide", ()-> new ItemMobDrop(EntityType.DOLPHIN,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//l
    public static final RegistryObject<Item> DONKEY_LEATHER = ITEMS.register("donkey_leather", ()-> new ItemMobDrop(EntityType.DONKEY,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//l
    public static final RegistryObject<Item> DROWNED_FLESH = ITEMS.register("drowned_flesh", ()-> new ItemMobDrop(EntityType.DROWNED,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//f
    public static final RegistryObject<Item> FOX_FUR = ITEMS.register("fox_fur", ()-> new ItemMobDrop(EntityType.FOX,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//f
    public static final RegistryObject<Item> GOAT_HORN = ITEMS.register("goat_horn", ()-> new ItemGoatHorn(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> NETHER_LEATHER = ITEMS.register("nether_leather", ()-> new ItemMobDrop(EntityType.HOGLIN,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//l
    public static final RegistryObject<Item> SANDED_FLESH = ITEMS.register("sanded_flesh", ()-> new ItemMobDrop(EntityType.HUSK,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//f
    public static final RegistryObject<Item> LLAMA_LEATHER = ITEMS.register("llama_leather", ()-> new ItemMobDrop(EntityType.LLAMA,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//l
    public static final RegistryObject<Item> MULE_LEATHER = ITEMS.register("mule_leather", ()-> new ItemMobDrop(EntityType.MULE,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//l
    public static final RegistryObject<Item> POLAR_BEAR_FUR = ITEMS.register("polar_bear_fur", ()-> new ItemMobDrop(EntityType.POLAR_BEAR,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//f
    public static final RegistryObject<Item> FREEZE_ARROW = ITEMS.register("freeze_arrow", ()-> new ItemFreezeArrow(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP),15.0f));//f mais peut-etre utilisable arc / arbalette
    public static final RegistryObject<Item> WITHER_BONE = ITEMS.register("wither_bone", ()-> new ItemMobDrop(EntityType.WITHER_SKELETON,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//f
    public static final RegistryObject<Item> WOLF_HIDE = ITEMS.register("wolf_hide", ()-> new ItemMobDrop(EntityType.WOLF,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//l
    public static final RegistryObject<Item> ZOMBIFIED_PORKCHOP = ITEMS.register("zombified_porkchop", ()-> new ItemMobDrop(EntityType.ZOGLIN,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//f
    public static final RegistryObject<Item> FRAGMENTED_EMERALD = ITEMS.register("fragmented_emerald", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//f
    public static final RegistryObject<Item> NETHER_FLESH = ITEMS.register("nether_flesh", ()-> new ItemMobDrop(EntityType.ZOMBIFIED_PIGLIN,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//f
    public static final RegistryObject<Item> AXOLOTL_SCUTE = ITEMS.register("axolotl_scute", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//f
    public static final RegistryObject<Item> CAT_HIDE = ITEMS.register("cat_hide", ()-> new ItemMobDrop(EntityType.CAT,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//l
    public static final RegistryObject<Item> VEX_WING = ITEMS.register("vex_wing", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//f
    public static final RegistryObject<Item> BAT_WING = ITEMS.register("bat_wing", ()-> new ItemMobDrop(EntityType.BAT,new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));//f
}
