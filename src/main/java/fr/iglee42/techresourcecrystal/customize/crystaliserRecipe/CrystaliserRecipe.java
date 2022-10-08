package fr.iglee42.techresourcecrystal.customize.crystaliserRecipe;

import com.google.gson.JsonObject;
import fr.iglee42.techresourcecrystal.block.entity.CrystaliserBlockEntity;
import fr.iglee42.techresourcecrystal.customize.CustomRecipes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class CrystaliserRecipe implements Recipe<CrystaliserBlockEntity> {

     private final ResourceLocation id;
      final Ingredient ingredient;
      final ItemStack result;

    public CrystaliserRecipe( ResourceLocation id, Ingredient ingredient, ItemStack result) {
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    public boolean matches(CrystaliserBlockEntity p_44002_, Level p_44003_) {
        return ingredient.test(p_44002_.getItem(0));
    }

    @Override
    public ItemStack assemble(CrystaliserBlockEntity p_44001_) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CustomRecipes.CRYSTALISER_SERIA;
    }

    @Override
    public RecipeType<?> getType() {
        return CustomRecipes.CRYSTALISER;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CrystaliserRecipe> {
        public CrystaliserRecipe fromJson(ResourceLocation p_44562_, JsonObject p_44563_) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(p_44563_, "block"));
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(p_44563_, "result"));
            return new CrystaliserRecipe(p_44562_, ingredient, itemstack);
        }

        public CrystaliserRecipe fromNetwork(ResourceLocation p_44565_, FriendlyByteBuf p_44566_) {
            Ingredient ingredient = Ingredient.fromNetwork(p_44566_);
            ItemStack itemstack = p_44566_.readItem();
            return new CrystaliserRecipe(p_44565_, ingredient, itemstack);
        }

        public void toNetwork(FriendlyByteBuf p_44553_, CrystaliserRecipe p_44554_) {
            p_44554_.ingredient.toNetwork(p_44553_);
            p_44553_.writeItem(p_44554_.result);
        }
    }
}
