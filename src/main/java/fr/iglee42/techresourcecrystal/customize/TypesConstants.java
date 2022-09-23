package fr.iglee42.techresourcecrystal.customize;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModItem;
import fr.iglee42.techresourcesbase.utils.JsonHelper;
import fr.iglee42.techresourcesbase.utils.ModsUtils;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypesConstants {

    public static List<Crystal> TYPES = new ArrayList<>();

    public static void init(){
        readAllFiles();
        registerAllCrystals();
    }

    private static void registerAllCrystals() {
        if (TYPES.isEmpty()) return;
        for (Crystal c : TYPES) {
            String type = c.name();
            ModBlock.createCrystal(type);
            ModItem.createCrystal(type);
            TechResourcesCrystal.LOGGER.info("Registring crystal : " + type);
        }

    }

    private static void readAllFiles() {
        File dir = FMLPaths.CONFIGDIR.get().resolve("techresourcescrystal/crystals/").toFile();
        if (!dir.exists() && dir.mkdirs()){
            dir.mkdir();
        }
        if (!dir.mkdirs() && dir.isDirectory()) {
            File[] files = dir.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
            if (files == null)
                return;

            for (File file : files) {
                JsonObject json;
                InputStreamReader reader = null;

                try {

                    JsonParser parser = new JsonParser();
                    reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                    json = parser.parse(reader).getAsJsonObject();
                    if (json.get("name").getAsString().isEmpty()) throw new NullPointerException("The name can't be empty ! (" + file.getName() + ")" );
                    TYPES.add(new Crystal(json.get("name").getAsString(),JsonHelper.getEntityType(json,"entity"), JsonHelper.getBoolean(json,"hasPlateRecipe"),JsonHelper.getBoolean(json,"hasCrystaliserRecipe"),JsonHelper.getBoolean(json,"hasMobRecipe"),JsonHelper.getBoolean(json,"dropFragmented")));
                    reader.close();
                } catch (Exception e) {
                    TechResourcesCrystal.LOGGER.error("An error occurred while loading minerals", e);
                } finally {
                    IOUtils.closeQuietly(reader);
                }
            }
        }

    }

    public static boolean isValidType(String type){
        if (TYPES.stream().noneMatch(t-> t.name().equals(type))) throw new IllegalArgumentException("Unknow crystal type : " + type);
        return true;
    }

}
