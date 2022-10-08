package fr.iglee42.techresourcecrystal.customize.datageneration;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.customize.Crystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModItem;
import fr.iglee42.techresourcesbase.utils.ModsUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Locale;
import java.util.Objects;

public class CustomsLanguagesProvider extends LanguageProvider {
    public CustomsLanguagesProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TechResourcesCrystal.MODID,"en_us");

    }


    @Override
    public void addTranslations() {
        for (Crystal c : TypesConstants.TYPES){
            if (c.isInModBase()) continue;
            add(ModBlock.getFragmentedCrystal(c.name()),"Fragmented " + ModsUtils.getUpperName(c.name(),"_") + " Crystal");
            add(ModBlock.getCrystalCore(c.name()),"Fragmented " + ModsUtils.getUpperName(c.name(),"_") + " Crystal Core");
            add(ModItem.getFragmentedCrystal(c.name()),"Fragmented " + ModsUtils.getUpperName(c.name(),"_") + " Crystal");
            add(ModItem.getCrystal(c.name()),ModsUtils.getUpperName(c.name(),"_") + " Crystal");
            add(ModItem.getPlate(c.name()),ModsUtils.getUpperName(c.name(),"_") + " Plate");
        }
    }



}
