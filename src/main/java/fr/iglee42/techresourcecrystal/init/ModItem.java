package fr.iglee42.techresourcecrystal.init;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.item.ItemCaptureStone;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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

    public static final RegistryObject<Item> WATER_CRYSTAL_PLATE = ITEMS.register("water_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> AIR_CRYSTAL_PLATE = ITEMS.register("air_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> FIRE_CRYSTAL_PLATE = ITEMS.register("fire_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> EARTH_CRYSTAL_PLATE = ITEMS.register("earth_plate", () -> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));

    public static final RegistryObject<Item> CAPTURE_STONE = ITEMS.register("capture_stone", ()-> new ItemCaptureStone(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> CRYSTALISED_SPAWN_EGG = ITEMS.register("crystalised_spawn_egg", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));

    //Mob Items
    public static final RegistryObject<Item> DOLPHIN_HIDE = ITEMS.register("dolphin_hide", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> DONKEY_LEATHER = ITEMS.register("donkey_leather", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> DROWNED_FLESH = ITEMS.register("drowned_flesh", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> FOX_HIDE = ITEMS.register("fox_hide", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> GOAT_HORN = ITEMS.register("goat_horn", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> NETHER_LEATHER = ITEMS.register("nether_leather", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> SANDED_FLESH = ITEMS.register("sanded_flesh", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> LLAMA_LEATHER = ITEMS.register("llama_leather", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> MULE_LEATHER = ITEMS.register("mule_leather", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> POLAR_BEAR_HIDE = ITEMS.register("polar_bear_hide", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> FREEZE_ARROW = ITEMS.register("freeze_arrow", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> WITHER_BONE = ITEMS.register("wither_bone", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> WOLF_HIDE = ITEMS.register("wolf_hide", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> ZOMBIFIED_PORKCHOP = ITEMS.register("zombified_porkchop", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> FRAGMENTED_EMERALD = ITEMS.register("fragmented_emerald", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    public static final RegistryObject<Item> NETHER_FLESH = ITEMS.register("nether_flesh", ()-> new Item(new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
}
