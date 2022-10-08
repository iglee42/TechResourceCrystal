package fr.iglee42.techresourcecrystal.jei;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class CrystalsJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(TechResourcesCrystal.MODID,"jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration){
        registration.addIngredientInfo(new ItemStack(ModBlock.CRYSTALISER.get()),VanillaTypes.ITEM_STACK,new TextComponent("\n\nYou must place it above a fully fragmented crystal core"),new TextComponent("\n\nYou need to fill the crystaliser with 2 lava buckets and add a crystaliser mold"),new TextComponent("\n\nWhen you fill the crystaliser with lava & mold you can send a redstone signal to launch the crystallization.\nThe crystallization take 30 seconds."));
        List<ItemStack> cores = new ArrayList<>();
        TypesConstants.TYPES.forEach(c->{
            cores.add(new ItemStack(ModBlock.getCrystalCore(c.name())));
            registration.addIngredientInfo(new ItemStack(ModBlock.getFragmentedCrystal(c.name())),VanillaTypes.ITEM_STACK,new TextComponent("Obtain when right click on a "+new TranslatableComponent(c.entity().getDescriptionId()).getString() +" with amethyst block. The mob has a 30 seconds cooldown"));
        });
        registration.addIngredientInfo(cores,VanillaTypes.ITEM_STACK,new TextComponent("Obtain when right click on the fragmented crystal of the same type with a lava shard"),new TextComponent("\n\nTo fill it you need to right click on with the fragmented crystal (item) of the same type."));
        //List<ItemStack> hides = new ArrayList<>();
        //registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, new ArrayList<>(hides));
    }
}
