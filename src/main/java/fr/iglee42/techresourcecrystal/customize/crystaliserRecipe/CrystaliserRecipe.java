package fr.iglee42.techresourcecrystal.customize.crystaliserRecipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fr.iglee42.techresourcecrystal.customize.CustomRecipes;
import fr.iglee42.techresourcesbase.utils.JsonHelper;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CrystaliserRecipe implements Recipe<SimpleContainer> {

     private final ResourceLocation id;
      private final Ingredient ingredient;
      private final HashMap<String, JsonPrimitive> requiredProperties;
      final ItemStack result;

    public CrystaliserRecipe(ResourceLocation id, Ingredient ingredient, HashMap<String,JsonPrimitive> requiredProps, ItemStack result) {
        this.id = id;
        if (!Arrays.stream(ingredient.getItems()).allMatch(it -> it.getItem() instanceof BlockItem)) throw new IllegalArgumentException("In the ingredients list an item is not a block");
        this.ingredient = ingredient;
        this.requiredProperties = requiredProps;
        this.result = result;
    }

    @Override
    public boolean matches(SimpleContainer p_44002_, Level p_44003_) {
        return ingredient.test(p_44002_.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer p_44001_) {
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

    public Ingredient getIngredient() {
        return ingredient;
    }

    public HashMap<String, JsonPrimitive> getRequiredProperties() {
        return requiredProperties;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CustomRecipes.CRYSTALISER_SERIA.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CrystaliserRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "crystaliser";
    }
    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CrystaliserRecipe> {
        public CrystaliserRecipe fromJson(ResourceLocation rs, JsonObject json) {
            Ingredient ingredient = Ingredient.of(JsonHelper.getBlock(json,"block").asItem());
            HashMap<String,JsonPrimitive> requiredProperties = new HashMap<>();
            if (json.has("properties")){
                JsonObject props = GsonHelper.getAsJsonObject(json,"properties");
                props.entrySet().forEach(e -> requiredProperties.put(e.getKey(),e.getValue().getAsJsonPrimitive()));
            }
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            return new CrystaliserRecipe(rs, ingredient, requiredProperties, itemstack);
        }

        public CrystaliserRecipe fromNetwork(ResourceLocation p_44565_, FriendlyByteBuf p_44566_) {
            Ingredient ingredient = Ingredient.fromNetwork(p_44566_);
            ItemStack itemstack = p_44566_.readItem();
            return new CrystaliserRecipe(p_44565_, ingredient, new HashMap<>(), itemstack);
        }

        public void toNetwork(FriendlyByteBuf p_44553_, CrystaliserRecipe p_44554_) {
            p_44554_.ingredient.toNetwork(p_44553_);
            p_44553_.writeItem(p_44554_.result);
        }
    }
}
