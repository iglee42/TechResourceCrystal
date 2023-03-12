package fr.iglee42.techresourcecrystal.customize.generation;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.customize.custompack.PathConstant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static fr.iglee42.igleelib.api.utils.ModsUtils.getUpperName;

public class LangsGenerator {

    private static Map<String,String> langs = new HashMap<>();
    public static void generate() {
        TypesConstants.TYPES.stream().filter(g->!g.isInModBase()).forEach(g->{
            langs.put("block.techresourcescrystal.fragmented_"+g.name() + "_crystal","Fragmented "+getUpperName(g.name()," ") +" Crystal");
            langs.put("block.techresourcescrystal.fragmented_"+g.name() + "_crystal_core","Fragmented "+getUpperName(g.name()," ") +" Crystal Core");
            langs.put("item.techresourcescrystal.fragmented_"+g.name() + "_crystal_item","Fragmented "+getUpperName(g.name()," ") +" Crystal");
            langs.put("item.techresourcescrystal."+g.name() + "_crystal",getUpperName(g.name()," ") +" Crystal");
            langs.put("item.techresourcescrystal."+g.name() + "_plate",getUpperName(g.name()," ") +" Plate");
        });



        try {
            FileWriter writer = new FileWriter(new File(PathConstant.LANGS_PATH.toFile(), "en_us.json"));
            writer.write("{\n");
            AtomicInteger index = new AtomicInteger(-1);
            langs.forEach((key,translation) -> {
                try {
                    index.getAndIncrement();
                    writer.write("  \"" + key + "\": \"" + translation + "\"" + (index.get() != langs.size() - 1? ",":"") + "\n");

                } catch (IOException e) {
                    TechResourcesCrystal.LOGGER.error("An error was detected when langs generating",e);
                }
            });
            writer.write("}");
            writer.close();
        } catch (Exception exception){
            TechResourcesCrystal.LOGGER.error("An error was detected when langs generating",exception);
        }
    }
}