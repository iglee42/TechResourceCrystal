package fr.iglee42.techresourcecrystal.customize.datageneration;

import fr.iglee42.igleelib.api.utils.ModsUtils;
import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.customize.Crystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class CustomsBlockModelsProvider extends BlockModelProvider {
    public CustomsBlockModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TechResourcesCrystal.MODID,existingFileHelper);

    }


    @Override
    public void registerModels() {
        for (Crystal c : TypesConstants.TYPES){
            if (c.isInModBase()) continue;
            fragmentedBlock(Objects.requireNonNull(ModBlock.getFragmentedCrystal(c.name())));
            layerCore(Objects.requireNonNull(ModBlock.getFragmentedCrystal(c.name())),1);
            layerCore(Objects.requireNonNull(ModBlock.getFragmentedCrystal(c.name())),2);
            layerCore(Objects.requireNonNull(ModBlock.getFragmentedCrystal(c.name())),3);
            core(Objects.requireNonNull(ModBlock.getFragmentedCrystal(c.name())));
        }
    }

    private BlockModelBuilder fragmentedBlock(Block block) {
        return withExistingParent(Registry.BLOCK.getKey(block).getPath(),
                new ResourceLocation("block/cube_all")).texture("all",
                new ResourceLocation(TechResourcesCrystal.MODID,ModsUtils.split(Registry.BLOCK.getKey(block).getPath(),"_")[1]+"/block"));
    }
    private BlockModelBuilder layerCore(Block block,int layer) {
        return withExistingParent("fragment_"+ ModsUtils.split(Registry.BLOCK.getKey(block).getPath(),"_")[1] + "_crystal_layer_"+layer,
                new ResourceLocation("block/cube_all")).texture("all",
                new ResourceLocation(TechResourcesCrystal.MODID,ModsUtils.split(Registry.BLOCK.getKey(block).getPath(),"_")[1] + "/core/crystal_"+layer));
    }
    private BlockModelBuilder core(Block block) {
        return withExistingParent(Registry.BLOCK.getKey(block).getPath()+ "_core",
                new ResourceLocation("block/cube_all")).texture("all",
                new ResourceLocation(TechResourcesCrystal.MODID, ModsUtils.split(Registry.BLOCK.getKey(block).getPath(),"_")[1] + "/core/empty"));
    }
}
