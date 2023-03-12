package fr.iglee42.techresourcecrystal.customize.generation;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.customize.custompack.PathConstant;

import java.io.File;
import java.io.FileWriter;

import static fr.iglee42.techresourcecrystal.TechResourcesCrystal.MODID;

public class BlockStatesGenerator {
    public static void generate() {
        TypesConstants.TYPES.stream().filter(c->!c.isInModBase()).forEach(c -> {
            blockState("fragmented_"+c.name() + "_crystal");
            core(c.name());
        });
    }
    private static void blockState(String name){
        try {
            FileWriter writer = new FileWriter(new File(PathConstant.BLOCK_STATES_PATH.toFile(), name+".json"));
            writer.write("{\n" +
                    "  \"variants\": {\n" +
                    "    \"\": {\n" +
                    "      \"model\": \""+MODID+":block/"+name+"\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}");
            writer.close();
        } catch (Exception exception){
            TechResourcesCrystal.LOGGER.error("An error was detected when blockstates generating",exception);
        }
    }
    private static void core(String name){
        try {
            FileWriter writer = new FileWriter(new File(PathConstant.BLOCK_STATES_PATH.toFile(), "fragmented_"+name+"_crystal_core.json"));
            writer.write("{\n" +
                    "  \"variants\": {\n" +
                    "    \"crystal=0\": {\n" +
                    "      \"model\": \"techresourcescrystal:block/fragmented_"+name+"_crystal_core\"\n" +
                    "    },\n" +
                    "    \"crystal=1\": {\n" +
                    "      \"model\": \"techresourcescrystal:block/fragment_"+name+"_crystal_layer_1\"\n" +
                    "    },\n" +
                    "    \"crystal=2\": {\n" +
                    "      \"model\": \"techresourcescrystal:block/fragment_"+name+"_crystal_layer_2\"\n" +
                    "    },\n" +
                    "    \"crystal=3\": {\n" +
                    "      \"model\": \"techresourcescrystal:block/fragment_"+name+"_crystal_layer_3\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}");
            writer.close();
        } catch (Exception exception){
            TechResourcesCrystal.LOGGER.error("An error was detected when blockstates generating",exception);
        }
    }
}