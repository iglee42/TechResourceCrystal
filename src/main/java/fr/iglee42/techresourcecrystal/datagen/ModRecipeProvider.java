package fr.iglee42.techresourcecrystal.datagen;

import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModItem;
import fr.iglee42.techresourcecrystal.init.ModTags;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItem.AIR_CRYSTAL.get()),ModItem.AIR_CRYSTAL_PLATE.get(),4)
                .save(p_176532_);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItem.WATER_CRYSTAL.get()),ModItem.WATER_CRYSTAL_PLATE.get(),4)
                .save(p_176532_);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItem.FIRE_CRYSTAL.get()),ModItem.FIRE_CRYSTAL_PLATE.get(),4)
                .save(p_176532_);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItem.EARTH_CRYSTAL.get()),ModItem.EARTH_CRYSTAL_PLATE.get(),4)
                .save(p_176532_);

        ShapedRecipeBuilder.shaped(ModItem.CRYSTALISER_MOLD.get())
                .pattern(" I ")
                .pattern("ICI")
                .pattern(" I ")
                .define('I', Items.IRON_INGOT)
                .define('C', ModTags.Items.FRAGMENTED_CRYSTALS)
                .unlockedBy("unlock", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(p_176532_);
        ShapedRecipeBuilder.shaped(ModBlock.CRYSTALISER.get())
                .pattern("BGB")
                .pattern("GCB")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('C', ModTags.Items.FRAGMENTED_CRYSTALS)
                .define('G', Items.GLASS)
                .define('B', Items.BUCKET)
                .unlockedBy("has_items", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT,Items.GLASS,Items.BUCKET))
                .save(p_176532_);
    }
}
