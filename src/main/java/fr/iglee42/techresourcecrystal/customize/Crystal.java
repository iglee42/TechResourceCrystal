package fr.iglee42.techresourcecrystal.customize;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public record Crystal(String name, EntityType entity, Item mobItem, boolean hasPlateRecipe, boolean hasCrystaliserRecipe, boolean hasMobRecipe, boolean dropFragmented, boolean hasCoreRecipe, boolean isInModBase) {}
