package fr.iglee42.techresourcecrystal.customize.generation;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.customize.custompack.PathConstant;

import java.io.File;
import java.io.FileWriter;

public class RecipesGenerator {
    public static void generate() {
        TypesConstants.TYPES.stream().filter(g->!g.isInModBase()).forEach(g -> {
            crystal(g.name());
            plate(g.name());
        });
    }
    private static void crystal(String name){
        try {
            FileWriter writer = new FileWriter(new File(PathConstant.CRYSTALS_RECIPES_PATH.toFile(), name+"_crystal.json"));
            writer.write("{\n" +
                    "  \"type\": \"techresourcescrystal:crystaliser\",\n" +
                    "  \"block\": \"techresourcescrystal:fragmented_"+name+"_crystal_core\",\n" +
                    "  \"properties\": {\n" +
                    "    \"crystal\": 3\n" +
                    "  },\n" +
                    "  \"result\": {\n" +
                    "    \"item\": \"techresourcescrystal:"+name+"_crystal\"\n" +
                    "  }\n" +
                    "}");
            writer.close();
        } catch (Exception exception){
            TechResourcesCrystal.LOGGER.error("An error was detected when recipes generating",exception);
        }
    }
    private static void plate(String name){
        try {
            FileWriter writer = new FileWriter(new File(PathConstant.CRYSTALS_RECIPES_PATH.toFile(), name+"_crystal_plate.json"));
            writer.write("{\n" +
                    "  \"type\": \"minecraft:stonecutting\",\n" +
                    "  \"ingredient\": {\n" +
                    "    \"item\": \"techresourcescrystal:"+name+"_crystal\"\n" +
                    "  },\n" +
                    "  \"result\": \"techresourcescrystal:"+name+"_plate\",\n" +
                    "  \"count\": 4\n" +
                    "}");
            writer.close();
        } catch (Exception exception){
            TechResourcesCrystal.LOGGER.error("An error was detected when recipes generating",exception);
        }
    }
}