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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CrystaliserRecipeBuilder {
   private final Ingredient block;
   private final Item result;
   private final Advancement.Builder advancement = Advancement.Builder.advancement();
   private final RecipeSerializer<?> type;

   public CrystaliserRecipeBuilder(RecipeSerializer<?> p_126381_, Ingredient p_126382_,  Item p_126384_) {
      this.type = p_126381_;
      this.block = p_126382_;
      
      this.result = p_126384_;
   }

   public static CrystaliserRecipeBuilder smithing(Ingredient p_126386_,  Item p_126388_) {
      return new CrystaliserRecipeBuilder(CustomRecipes.CRYSTALISER_SERIA, p_126386_, p_126388_);
   }

   public CrystaliserRecipeBuilder unlocks(String p_126390_, CriterionTriggerInstance p_126391_) {
      this.advancement.addCriterion(p_126390_, p_126391_);
      return this;
   }

   public void save(Consumer<FinishedRecipe> p_126393_, String p_126394_) {
      this.save(p_126393_, new ResourceLocation(p_126394_));
   }

   public void save(Consumer<FinishedRecipe> p_126396_, ResourceLocation p_126397_) {
      this.ensureValid(p_126397_);
      this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_126397_)).rewards(AdvancementRewards.Builder.recipe(p_126397_)).requirements(RequirementsStrategy.OR);
      p_126396_.accept(new CrystaliserRecipeBuilder.Result(p_126397_, this.type, this.block, this.result, this.advancement, new ResourceLocation(p_126397_.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + p_126397_.getPath())));
   }

   private void ensureValid(ResourceLocation p_126399_) {
      if (this.advancement.getCriteria().isEmpty()) {
         throw new IllegalStateException("No way of obtaining recipe " + p_126399_);
      }
   }

   public static class Result implements FinishedRecipe {
      private final ResourceLocation id;
      private final Ingredient block;
      private final Item result;
      private final Advancement.Builder advancement;
      private final ResourceLocation advancementId;
      private final RecipeSerializer<?> type;

      public Result(ResourceLocation p_126408_, RecipeSerializer<?> p_126409_, Ingredient p_126410_, Item p_126412_, Advancement.Builder p_126413_, ResourceLocation p_126414_) {
         this.id = p_126408_;
         this.type = p_126409_;
         this.block = p_126410_;
         this.result = p_126412_;
         this.advancement = p_126413_;
         this.advancementId = p_126414_;
      }

      public void serializeRecipeData(JsonObject p_126416_) {
         p_126416_.add("block", this.block.toJson());
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("item", Registry.ITEM.getKey(this.result).toString());
         p_126416_.add("result", jsonobject);
      }

      public ResourceLocation getId() {
         return this.id;
      }

      public RecipeSerializer<?> getType() {
         return this.type;
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