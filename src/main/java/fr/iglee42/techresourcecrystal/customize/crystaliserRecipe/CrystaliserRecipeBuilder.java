package fr.iglee42.techresourcecrystal.customize.crystaliserRecipe;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;

import fr.iglee42.techresourcecrystal.customize.CustomRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class CrystaliserRecipeBuilder implements RecipeBuilder {
   private final Ingredient block;
   private final CustomJsonProperty[] properties;
   private final Item result;
   private final int count;
   private final Advancement.Builder advancement = Advancement.Builder.advancement();

   public CrystaliserRecipeBuilder(Ingredient ingredient,  ItemLike result,int count,CustomJsonProperty... props) {
      this.block = ingredient;
      this.properties = props;
      this.result = result.asItem();
      this.count = count;
   }



   public void save(Consumer<FinishedRecipe> p_126393_, String p_126394_) {
      this.save(p_126393_, new ResourceLocation(p_126394_));
   }

   @Override
   public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
      this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
      return this;
   }

   @Override
   public RecipeBuilder group(@Nullable String pGroupName) {
      return this;
   }

   @Override
   public Item getResult() {
      return result;
   }

   @Override
   public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
      this.advancement.parent(new ResourceLocation("recipes/root"))
              .addCriterion("has_the_recipe",
                      RecipeUnlockedTrigger.unlocked(pRecipeId))
              .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

      pFinishedRecipeConsumer.accept(new CrystaliserRecipeBuilder.Result(pRecipeId, this.result, this.count, this.block,properties,
              this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/"+ pRecipeId.getPath())));

   }

   public static class Result implements FinishedRecipe {
      private final ResourceLocation id;
      private final Item result;
      private final Ingredient ingredient;
      private final CustomJsonProperty[] properties;
      private final int count;
      private final Advancement.Builder advancement;
      private final ResourceLocation advancementId;

      public Result(ResourceLocation pId, Item pResult, int pCount, Ingredient ingredient,CustomJsonProperty[] props ,Advancement.Builder pAdvancement,
                    ResourceLocation pAdvancementId) {
         this.id = pId;
         this.result = pResult;
         this.count = pCount;
         this.ingredient = ingredient;
         this.properties = props;
         this.advancement = pAdvancement;
         this.advancementId = pAdvancementId;
      }

      public void serializeRecipeData(JsonObject end) {
         end.addProperty("block", Registry.BLOCK.getKey(Block.byItem(this.ingredient.getItems()[0].getItem())).toString());
         if (this.properties != null && this.properties.length > 0){
            JsonObject properties = new JsonObject();
            for (CustomJsonProperty property : this.properties) {
               properties.add(property.name(), property.value());
            }
            end.add("properties",properties);
         }
         JsonObject result = new JsonObject();
         result.addProperty("item", Registry.ITEM.getKey(this.result).toString());
         if (this.count > 1) {
            result.addProperty("count", this.count);
         }
         end.add("result", result);

      }

      public ResourceLocation getId() {
         return this.id;
      }

      public RecipeSerializer<?> getType() {
         return CustomRecipes.CRYSTALISER_SERIA.get();
      }

      @Nullable
      public JsonObject serializeAdvancement() {
         return this.advancement.serializeToJson();
      }

      @Nullable
      public ResourceLocation getAdvancementId() {
         return this.advancementId;
      }
   }
}