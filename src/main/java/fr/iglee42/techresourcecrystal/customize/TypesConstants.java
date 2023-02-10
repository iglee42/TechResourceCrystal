package fr.iglee42.techresourcecrystal.customize;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.iglee42.igleelib.api.utils.JsonHelper;
import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
        TYPES.add(new Crystal("water", EntityType.GUARDIAN, Items.AMETHYST_BLOCK,true,true,true,true,true,true));
        TYPES.add(new Crystal("fire", EntityType.BLAZE,Items.AMETHYST_BLOCK,true,true,true,true,true,true));
        TYPES.add(new Crystal("earth", EntityType.CREEPER,Items.AMETHYST_BLOCK,true,true,true,true,true,true));
        TYPES.add(new Crystal("air", EntityType.PHANTOM,Items.AMETHYST_BLOCK,true,true,true,true,true,true));

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
                    EntityType entity = EntityType.PIG;
                    if (json.has("entity")) entity = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(JsonHelper.getString(json,"entity")));
                    Item it = Blocks.AMETHYST_BLOCK.asItem();
                    if (json.has("mobItem")) it = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JsonHelper.getString(json,"mobItem")));

                    TYPES.add(new Crystal(json.get("name").getAsString(),entity, it, JsonHelper.getBooleanOrDefault(json,"hasPlateRecipe",true),JsonHelper.getBooleanOrDefault(json,"hasCrystaliserRecipe",true),JsonHelper.getBooleanOrDefault(json,"hasMobRecipe",true),JsonHelper.getBooleanOrDefault(json,"dropFragmented",true),JsonHelper.getBooleanOrDefault(json,"hasCoreRecipe",true),false));
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

    public static Crystal getType(String type) {
        if (!isValidType(type)) return null;
        return TYPES.stream().filter(c->c.name().equals(type)).findFirst().get();
    }
}
