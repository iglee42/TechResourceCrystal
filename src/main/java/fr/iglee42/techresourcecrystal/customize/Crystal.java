package fr.iglee42.techresourcecrystal.customize;

import net.minecraft.world.entity.EntityType;

public record Crystal(String name, EntityType entity, boolean hasPlateRecipe, boolean hasCrystaliserRecipe, boolean hasMobRecipe, boolean dropFragmented,boolean hasCoreRecipe,boolean isInModBase) {}
