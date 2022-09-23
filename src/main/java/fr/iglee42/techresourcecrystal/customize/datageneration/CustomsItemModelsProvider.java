package fr.iglee42.techresourcecrystal.customize.datageneration;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.customize.Crystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcesbase.utils.ModsUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class CustomsItemModelsProvider extends ItemModelProvider {
    public CustomsItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TechResourcesCrystal.MODID,existingFileHelper);

    }


    @Override
    public void registerModels() {
        for (Crystal c : TypesConstants.TYPES){
            fromBlock(Objects.requireNonNull(ModBlock.getFragmentedCrystal(c.name())));
            fromBlock(Objects.requireNonNull(ModBlock.getCrystalCore(c.name())));
            fragmentedCrystal(c.name());
            crystal(c.name());
            plate(c.name());
        }
    }

    private ItemModelBuilder fromBlock(Block block){
        return withExistingParent(block.getRegistryName().getPath(),
                new ResourceLocation(TechResourcesCrystal.MODID,"block/" + block.getRegistryName().getPath()));
    }

    private ItemModelBuilder fragmentedCrystal(String name){
        return withExistingParent("fragmented_"+name+"_crystal_item",
                new ResourceLocation(TechResourcesCrystal.MODID,"item/fragmented_crystal_item")).texture("1",new ResourceLocation(
                TechResourcesCrystal.MODID,name+"/item"));
    }

    private ItemModelBuilder crystal(String name){
        return withExistingParent(name+"_crystal",
                new ResourceLocation(TechResourcesCrystal.MODID,"item/crystal")).texture("1",new ResourceLocation(
                TechResourcesCrystal.MODID,name+"/item"));
    }

    private ItemModelBuilder plate(String name){
        return withExistingParent(name+"_plate",
                new ResourceLocation("item/generated")).texture("layer_0",new ResourceLocation(
                TechResourcesCrystal.MODID,name+"/plate"));
    }

}
