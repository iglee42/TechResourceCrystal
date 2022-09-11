package fr.iglee42.techresourcecrystal.jei;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.TextComponent;
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
        registration.addIngredientInfo(new ItemStack(ModBlock.CRYSTALISER.get()),VanillaTypes.ITEM_STACK,new TextComponent("You need to place above a full fragmented crystal core"),new TextComponent("You need to fill the crystaliser with 2 lava buckets and add a crystaliser mold"),new TextComponent("When you fill the crystaliser with lava & mold you can send a redstone signal to launch the crystallization.\nThe crystallization take 30 seconds."));
        registration.addIngredientInfo(new ItemStack(ModBlock.FRAGMENTED_FIRE_CRYSTAL.get()),VanillaTypes.ITEM_STACK,new TextComponent("Obtain when right click on blaze with amethyst block. The mob has a 30 seconds cooldown"));
        registration.addIngredientInfo(new ItemStack(ModBlock.FRAGMENTED_AIR_CRYSTAL.get()),VanillaTypes.ITEM_STACK,new TextComponent("Obtain when right click on phantom with amethyst block. The mob has a 30 seconds cooldown"));
        registration.addIngredientInfo(new ItemStack(ModBlock.FRAGMENTED_EARTH_CRYSTAL.get()),VanillaTypes.ITEM_STACK,new TextComponent("Obtain when right click on creeper with amethyst block. The mob has a 30 seconds cooldown"));
        registration.addIngredientInfo(new ItemStack(ModBlock.FRAGMENTED_WATER_CRYSTAL.get()),VanillaTypes.ITEM_STACK,new TextComponent("Obtain when right click on guardian with amethyst block. The mob has a 30 seconds cooldown"));
        List<ItemStack> cores = new ArrayList<>();
        cores.add(new ItemStack(ModBlock.FRAGMENTED_EARTH_CRYSTAL_CORE.get()));
        cores.add(new ItemStack(ModBlock.FRAGMENTED_WATER_CRYSTAL_CORE.get()));
        cores.add(new ItemStack(ModBlock.FRAGMENTED_FIRE_CRYSTAL_CORE.get()));
        cores.add(new ItemStack(ModBlock.FRAGMENTED_AIR_CRYSTAL_CORE.get()));
        registration.addIngredientInfo(cores,VanillaTypes.ITEM_STACK,new TextComponent("Obtain when right click on the fragmented crystal of the same type with a lava shard"));
        //List<ItemStack> hides = new ArrayList<>();
        //registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, new ArrayList<>(hides));
    }
}
