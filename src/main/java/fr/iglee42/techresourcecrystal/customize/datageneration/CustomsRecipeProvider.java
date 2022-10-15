package fr.iglee42.techresourcecrystal.customize.datageneration;

import com.google.gson.JsonPrimitive;
import fr.iglee42.techresourcecrystal.customize.Crystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.customize.crystaliserRecipe.CrystaliserRecipeBuilder;
import fr.iglee42.techresourcecrystal.customize.crystaliserRecipe.CustomJsonProperty;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModItem;
import fr.iglee42.techresourcecrystal.init.ModTags;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.Objects;
import java.util.function.Consumer;

public class CustomsRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public CustomsRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
       for (Crystal type : TypesConstants.TYPES) {
           if (type.hasCrystaliserRecipe()){
               new CrystaliserRecipeBuilder(Ingredient.of(ModBlock.getCrystalCore(type.name())),ModItem.getCrystal(type.name()),1,new CustomJsonProperty("crystal",new JsonPrimitive(3)))
                       .save(consumer);
           }
           if (type.isInModBase()) continue;
           if (type.hasPlateRecipe()) {
               SingleItemRecipeBuilder.stonecutting(
                       Ingredient.of(ModItem.getCrystal(type.name())),
                       Objects.requireNonNull(ModItem.getPlate(type.name())),
                       4)
               .unlockedBy("has_" + type.name() + "_crystal", inventoryTrigger(ItemPredicate.Builder.item().of(ModItem.getCrystal(type.name())).build()))
               .save(consumer);
           }

       }
    }
}
