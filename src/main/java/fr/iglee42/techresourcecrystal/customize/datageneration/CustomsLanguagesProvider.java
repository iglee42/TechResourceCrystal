package fr.iglee42.techresourcecrystal.customize.datageneration;

import fr.iglee42.igleelib.api.utils.ModsUtils;
import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.customize.Crystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModItem;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;

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
