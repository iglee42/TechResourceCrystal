package fr.iglee42.techresourcecrystal.init;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.block.entity.CrystaliserBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntity {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TechResourcesCrystal.MODID);

    public static final RegistryObject<BlockEntityType<CrystaliserBlockEntity>> CRYSTALISER = BLOCK_ENTITIES.register("crystaliser",() -> BlockEntityType.Builder.of(CrystaliserBlockEntity::new,ModBlock.CRYSTALISER.get()).build(null));

}
