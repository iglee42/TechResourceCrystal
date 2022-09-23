package fr.iglee42.techresourcecrystal.customize;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class DataGeneratorFactory {

    public static Path ROOT_PATH;

    public static void init() {
        ROOT_PATH = FMLPaths.CONFIGDIR.get().resolve("techresourcescrystal/pack");
    }

    public static DataGenerator createMemoryDataGenerator() {
        return new DataGenerator(ROOT_PATH, ImmutableList.of());
    }
}