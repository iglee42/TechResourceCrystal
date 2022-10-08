package fr.iglee42.techresourcecrystal.customize.datageneration;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.block.CustomFragmentedCore;
import fr.iglee42.techresourcecrystal.customize.Crystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CustomsBlockStatesProvider extends BlockStateProvider {
    public CustomsBlockStatesProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TechResourcesCrystal.MODID,existingFileHelper);
    }

    @Override
    public void registerStatesAndModels() {
        for (Crystal c : TypesConstants.TYPES){
            if (c.isInModBase()) continue;
            String name = c.name();
            getVariantBuilder(ModBlock.getCrystalCore(name))
                    .partialState().with(CustomFragmentedCore.CRYSTAL,0).modelForState().modelFile(models().getExistingFile(new ResourceLocation(TechResourcesCrystal.MODID,"fragmented_"+name+"_crystal_core"))).addModel()
                    .partialState().with(CustomFragmentedCore.CRYSTAL,1).modelForState().modelFile(models().getExistingFile(new ResourceLocation(TechResourcesCrystal.MODID,"fragment_"+name+"_crystal_layer_1"))).addModel()
                    .partialState().with(CustomFragmentedCore.CRYSTAL,2).modelForState().modelFile(models().getExistingFile(new ResourceLocation(TechResourcesCrystal.MODID,"fragment_"+name+"_crystal_layer_2"))).addModel()
                    .partialState().with(CustomFragmentedCore.CRYSTAL,3).modelForState().modelFile(models().getExistingFile(new ResourceLocation(TechResourcesCrystal.MODID,"fragment_"+name+"_crystal_layer_3"))).addModel();
            this.getVariantBuilder(ModBlock.getFragmentedCrystal(name))
                    .forAllStates(state ->
                            ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(TechResourcesCrystal.MODID, "fragmented_" + name + "_crystal"))).build()
                    );
        }
    }
}
