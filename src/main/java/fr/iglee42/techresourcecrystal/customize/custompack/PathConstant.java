package fr.iglee42.techresourcecrystal.customize.custompack;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Path;

public class PathConstant {


    public static Path ROOT_PATH;
    public static Path ASSETS_PATH;
    public static Path DATAS_PATH;

    public static Path LANGS_PATH;
    public static Path TEXTURES_PATH;
    public static Path RECIPES_PATH;

    public static Path BLOCK_STATES_PATH;
    public static Path MODELS_PATH;
    public static Path ITEM_MODELS_PATH;
    public static Path BLOCK_MODELS_PATH;
    public static Path CRYSTALS_RECIPES_PATH;

    public static void init() {
        try {
            TechResourcesCrystal.deleteDirectory(FMLPaths.CONFIGDIR.get().resolve("techresourcescrystal/pack"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ROOT_PATH = FMLPaths.CONFIGDIR.get().resolve("techresourcescrystal/pack");
        ASSETS_PATH = ROOT_PATH.resolve("assets/techresourcescrystal");
        DATAS_PATH = ROOT_PATH.resolve("data/techresourcescrystal");

        BLOCK_STATES_PATH = ASSETS_PATH.resolve("blockstates");
        LANGS_PATH = ASSETS_PATH.resolve("lang");
        TEXTURES_PATH = ASSETS_PATH.resolve("textures");
        MODELS_PATH = ASSETS_PATH.resolve("models");

        RECIPES_PATH = DATAS_PATH.resolve("recipes");

        ITEM_MODELS_PATH = MODELS_PATH.resolve("item");
        BLOCK_MODELS_PATH = MODELS_PATH.resolve("block");
        CRYSTALS_RECIPES_PATH = RECIPES_PATH.resolve("crystals");

        CRYSTALS_RECIPES_PATH.toFile().mkdirs();
        BLOCK_STATES_PATH.toFile().mkdirs();
        LANGS_PATH.toFile().mkdirs();
        TEXTURES_PATH.toFile().mkdirs();
        ITEM_MODELS_PATH.toFile().mkdirs();
        BLOCK_MODELS_PATH.toFile().mkdirs();
    }

}