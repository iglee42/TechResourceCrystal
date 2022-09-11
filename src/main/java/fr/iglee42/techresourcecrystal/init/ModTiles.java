package fr.iglee42.techresourcecrystal.init;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTiles {

    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, TechResourcesCrystal.MODID);

}
