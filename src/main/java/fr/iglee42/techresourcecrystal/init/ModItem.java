package fr.iglee42.techresourcecrystal.init;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.item.ItemFreezeArrow;
import fr.iglee42.techresourcecrystal.item.ItemCaptureStone;
import fr.iglee42.techresourcecrystal.item.ItemMobDrop;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItem {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TechResourcesCrystal.MODID);
    public static final DeferredRegister<Item> MOBS_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TechResourcesCrystal.MODID+ ".mobs");

    //public static final RegistryObject<Item> FRAGMENTED_WATER_CRYSTAL = ITEMS.register("fragmented_water_crystal_item",()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(3)));
    //public static final RegistryObject<Item> FRAGMENTED_AIR_CRYSTAL = ITEMS.register("fragmented_air_crystal_item",()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(3)));
    //public static final RegistryObject<Item> FRAGMENTED_FIRE_CRYSTAL = ITEMS.register("fragmented_fire_crystal_item",()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(3)));
    //public static final RegistryObject<Item> FRAGMENTED_EARTH_CRYSTAL = ITEMS.register("fragmented_earth_crystal_item",()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(3)));
    //public static final RegistryObject<Item> WATER_CRYSTAL = ITEMS.register("water_crystal",() -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    //public static final RegistryObject<Item> AIR_CRYSTAL = ITEMS.register("air_crystal",() -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    //public static final RegistryObject<Item> FIRE_CRYSTAL = ITEMS.register("fire_crystal",() -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    //public static final RegistryObject<Item> EARTH_CRYSTAL = ITEMS.register("earth_crystal",() -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> UNIFIED_CRYSTAL = ITEMS.register("unified_crystal",() -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)));

    public static final RegistryObject<Item> CRYSTALISER_MOLD = ITEMS.register("crystaliser_mold", () -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)){
        @Override
        public boolean hasCraftingRemainingItem(ItemStack stack) {
            return true;
        }

        @Override
        public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
            return itemStack;
        }
    });
    public static final RegistryObject<Item> MOB_MOLD = ITEMS.register("mob_mold", () -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)){
        @Override
        public boolean hasCraftingRemainingItem(ItemStack stack) {
            return true;
        }

        @Override
        public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
            return itemStack;
        }

        @Override
        public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
            if (p_41421_.getDisplayName().getString().equals("[LIMachi]")) p_41423_.add(Component.literal("https://www.youtube.com/c/LIMachi"));
            super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        }
    });
    public static final RegistryObject<Item> MINERALS_MOLD = ITEMS.register("minerals_mold", () -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)){
        @Override
        public boolean hasCraftingRemainingItem(ItemStack stack) {
            return true;
        }

        @Override
        public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
            return itemStack;
        }
    });
    public static final RegistryObject<Item> ROCKS_MOLD = ITEMS.register("rocks_mold", () -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)){
        @Override
        public boolean hasCraftingRemainingItem(ItemStack stack) {
            return true;
        }

        @Override
        public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
            return itemStack;
        }
    });
    public static final RegistryObject<Item> UNIFIED_MOB_MOLD = ITEMS.register("unified_mob_mold", () -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)){
        @Override
        public boolean hasCraftingRemainingItem(ItemStack stack) {
            return true;
        }

        @Override
        public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
            return itemStack;
        }
    });

    //public static final RegistryObject<Item> WATER_CRYSTAL_PLATE = ITEMS.register("water_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    //public static final RegistryObject<Item> AIR_CRYSTAL_PLATE = ITEMS.register("air_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    //public static final RegistryObject<Item> FIRE_CRYSTAL_PLATE = ITEMS.register("fire_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    //public static final RegistryObject<Item> EARTH_CRYSTAL_PLATE = ITEMS.register("earth_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));

    public static final RegistryObject<Item> CAPTURE_STONE = ITEMS.register("capture_stone", ()-> new ItemCaptureStone(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> CRYSTALISED_SPAWN_EGG = ITEMS.register("crystalised_spawn_egg", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));

    //Mob Items
    public static final RegistryObject<Item> AXOLOTL_SCUTE = createMobItem("axolotl_scute",EntityType.AXOLOTL);
    public static final RegistryObject<Item> DOLPHIN_HIDE = createMobItem("dolphin_hide",EntityType.DOLPHIN);

    public static final RegistryObject<Item> DONKEY_LEATHER = createMobItem("donkey_leather",EntityType.DONKEY);

    public static final RegistryObject<Item> DROWNED_FLESH = createMobItem("drowned_flesh",EntityType.DROWNED);

    public static final RegistryObject<Item> FOX_FUR = createMobItem("fox_fur",EntityType.FOX);

    //public static final RegistryObject<Item> GOAT_HORN = MOBS_ITEMS.register("goat_horn",()->new ItemGoatHorn(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)))    public static final RegistryObject<Item> NETHER_LEATHER = createMobItem("nether_leather",EntityType.HOGLIN);

    public static final RegistryObject<Item> SANDED_FLESH = createMobItem("sanded_flesh",EntityType.HUSK);

    public static final RegistryObject<Item> LLAMA_LEATHER = createMobItem("llama_leather",EntityType.LLAMA);

    public static final RegistryObject<Item> MULE_LEATHER = createMobItem("mule_leather",EntityType.MULE);

    public static final RegistryObject<Item> POLAR_BEAR_FUR = createMobItem("polar_bear_fur",EntityType.POLAR_BEAR);

    public static final RegistryObject<Item> WITHER_BONE = createMobItem("wither_bone",EntityType.WITHER_SKELETON);

    public static final RegistryObject<Item> WOLF_HIDE = createMobItem("wolf_hide",EntityType.WOLF);

    public static final RegistryObject<Item> ZOMBIFIED_PORKCHOP = createMobItem("zombified_porkchop",EntityType.ZOGLIN);

    public static final RegistryObject<Item> NETHER_LEATHER = createMobItem("nether_leather",EntityType.HOGLIN);

    public static final RegistryObject<Item> FRAGMENTED_EMERALD = createMobItem("fragmented_emerald",EntityType.ZOMBIE_VILLAGER);

    public static final RegistryObject<Item> NETHER_FLESH = createMobItem("nether_flesh",EntityType.ZOMBIFIED_PIGLIN);

    public static final RegistryObject<Item> BAT_WING = createMobItem("bat_wing",EntityType.BAT);

    public static final RegistryObject<Item> VEX_WING = createMobItem("vex_wing",EntityType.VEX);

    public static final RegistryObject<Item> CAT_HIDE = createMobItem("cat_hide",EntityType.CAT);

    public static final RegistryObject<Item> ALLAY_WING = createMobItem("allay_wing",EntityType.ALLAY);

    public static final RegistryObject<Item> FROG_LEG = createMobItem("frog_leg",EntityType.FROG);


    public static final RegistryObject<Item> FREEZE_ARROW = MOBS_ITEMS.register("freeze_arrow",()->new ItemFreezeArrow(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP),2.0f));

    private static RegistryObject<Item> createMobItem(String name,EntityType type){
        return MOBS_ITEMS.register(name,()->new ItemMobDrop(type,new Item.Properties().tab(TechResourcesCrystal.MOBS_GROUP)));
    }
    public static void createCrystal(String type) {
        if (!TypesConstants.isValidType(type)) return;
        ITEMS.register("fragmented_"+type+"_crystal_item",()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(3)));
        ITEMS.register(type+ "_crystal",() -> new Item(new Item.Properties().stacksTo(1).tab(TechResourcesCrystal.CRYSTAL_GROUP)));
        ITEMS.register(type+"_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    }

    public static Item getCrystal(String type){
        if (!TypesConstants.isValidType(type)) return null;
        if (ITEMS.getEntries().stream().noneMatch(b -> b.getId().getPath().equals(type+"_crystal"))) throw new IllegalArgumentException("The crystal type is unknow");
        return ITEMS.getEntries().stream().filter(b -> b.getId().getPath().equals(type+"_crystal")).findFirst().get().get();
    }

    public static Item getFragmentedCrystal(String type){
        if (!TypesConstants.isValidType(type)) return null;
        if (ITEMS.getEntries().stream().noneMatch(b -> b.getId().getPath().equals("fragmented_"+type+"_crystal_item"))) throw new IllegalArgumentException("The fragmented crystal type is unknow");
        return ITEMS.getEntries().stream().filter(b -> b.getId().getPath().equals("fragmented_"+type+"_crystal_item")).findFirst().get().get();
    }
    public static Item getPlate(String type){
        if (!TypesConstants.isValidType(type)) return null;
        if (ITEMS.getEntries().stream().noneMatch(b -> b.getId().getPath().equals(type+"_plate"))) throw new IllegalArgumentException("The crystal plate type is unknow");
        return ITEMS.getEntries().stream().filter(b -> b.getId().getPath().equals(type+"_plate")).findFirst().get().get();
    }
}
