package fr.iglee42.techresourcecrystal.init;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.block.*;
import fr.iglee42.techresourcecrystal.block.CustomFragmentedCore;
import fr.iglee42.techresourcecrystal.block.CustomFragmentedCrystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlock {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TechResourcesCrystal.MODID);

    //public static final RegistryObject<Block> FRAGMENTED_WATER_CRYSTAL = createBlock("fragmented_water_crystal", BlockFragmentedWaterCrystal::new);
    //public static final RegistryObject<Block> FRAGMENTED_AIR_CRYSTAL = createBlock("fragmented_air_crystal", BlockFragmentedAirCrystal::new);
    //public static final RegistryObject<Block> FRAGMENTED_FIRE_CRYSTAL = createBlock("fragmented_fire_crystal", BlockFragmentedFireCrystal::new);
    //public static final RegistryObject<Block> FRAGMENTED_EARTH_CRYSTAL = createBlock("fragmented_earth_crystal", BlockFragmentedEarthCrystal::new);
    //public static final RegistryObject<Block> FRAGMENTED_WATER_CRYSTAL_CORE = createBlock("fragmented_water_crystal_core", BlockFragmentedWaterCrystalCore::new);
    //public static final RegistryObject<Block> FRAGMENTED_AIR_CRYSTAL_CORE = createBlock("fragmented_air_crystal_core", BlockFragmentedAirCrystalCore::new);
    //public static final RegistryObject<Block> FRAGMENTED_FIRE_CRYSTAL_CORE = createBlock("fragmented_fire_crystal_core", BlockFragmentedFireCrystalCore::new);
    //public static final RegistryObject<Block> FRAGMENTED_EARTH_CRYSTAL_CORE = createBlock("fragmented_earth_crystal_core", BlockFragmentedEarthCrystalCore::new);

    public static final RegistryObject<Block> CRYSTALISER = createBlock("crystaliser", BlockCrystaliser::new);

   // public static final RegistryObject<Block> ROCK_CRYSTAL = createBlock("rock_crystal",BlockRockCrystal::new);
   public static final RegistryObject<Block> PRIMSARINE_SPONGE = createBlock("prismarine_sponge", BlockPrismarineSponge::new);

    public static void createCrystal(String type)
    {
        if (!TypesConstants.isValidType(type)) return;
        RegistryObject<Block> core = BLOCKS.register("fragmented_"+type+"_crystal_core", () -> new CustomFragmentedCore(type));
        ModItem.ITEMS.register("fragmented_"+type+"_crystal_core", () -> new BlockItem(core.get(), new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
        RegistryObject<Block> fragment = BLOCKS.register("fragmented_"+type+"_crystal", () -> new CustomFragmentedCrystal(type));
        ModItem.ITEMS.register("fragmented_"+type+"_crystal", () -> new BlockItem(fragment.get(), new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
    }


    public static RegistryObject<Block> createBlock(String name, Supplier<? extends Block> supplier)
    {
        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        ModItem.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(TechResourcesCrystal.CRYSTAL_GROUP)));
        return block;
    }
    public static RegistryObject<Block> createBlockWithoutItem(String name, Supplier<? extends Block> supplier)
    {
        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        return block;
    }

    public static Block getCrystalCore(String type){
        if (!TypesConstants.isValidType(type)) return null;
        if (BLOCKS.getEntries().stream().noneMatch(b -> b.getId().getPath().equals("fragmented_"+type+"_crystal_core"))) throw new IllegalArgumentException("The crystal core type is unknow");
        return BLOCKS.getEntries().stream().filter(b -> b.getId().getPath().equals("fragmented_"+type+"_crystal_core")).findFirst().get().get();
    }
    public static Block getFragmentedCrystal(String type){
        if (!TypesConstants.isValidType(type)) return null;
//        if (BLOCKS.getEntries().stream().noneMatch(b -> b.getId().getPath().equals("fragmented_"+type+"_crystal"))) throw new IllegalArgumentException("The fragmented crystal type is unknow");
        return BLOCKS.getEntries().stream().filter(b -> b.getId().getPath().equals("fragmented_"+type+"_crystal")).findFirst().get().get();
    }

}

