package fr.iglee42.techresourcecrystal.jei;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.customize.crystaliserRecipe.CrystaliserRecipe;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JeiPlugin
public class CrystalsJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(TechResourcesCrystal.MODID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                CrystaliserRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlock.CRYSTALISER.get()), new RecipeType<>(CrystaliserRecipeCategory.UID, CrystaliserRecipe.class));
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration){
        RecipeManager rm = Minecraft.getInstance().level.getRecipeManager();
        registration.addRecipes(CrystaliserRecipeCategory.RECIPE_TYPE,
                rm.getAllRecipesFor(CrystaliserRecipe.Type.INSTANCE)
                        .stream()
                        .map(r -> (CrystaliserRecipe) r)
                        .collect(Collectors.toList()));


        registration.addIngredientInfo(new ItemStack(ModBlock.CRYSTALISER.get()),VanillaTypes.ITEM_STACK,Component.literal("\nWhen you fill the crystaliser with lava & mold you can send a redstone signal to launch the crystalization.\nThe crystalization take 30 seconds."));
        List<ItemStack> cores = new ArrayList<>();
        TypesConstants.TYPES.forEach(c->{
            cores.add(new ItemStack(ModBlock.getCrystalCore(c.name())));
            registration.addIngredientInfo(new ItemStack(ModBlock.getFragmentedCrystal(c.name())),VanillaTypes.ITEM_STACK,Component.literal("Obtain when right click on a "+Component.translatable(c.entity().getDescriptionId()).getString() +" with amethyst block. The mob has a 30 seconds cooldown"));
        });
        registration.addIngredientInfo(cores,VanillaTypes.ITEM_STACK,Component.literal("Obtain when right click on the fragmented crystal of the same type with a lava shard"),Component.literal("\n\nTo fill it you need to right click on with the fragmented crystal (item) of the same type."));
        //List<ItemStack> hides = new ArrayList<>();
        //registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, new ArrayList<>(hides));
    }
}
