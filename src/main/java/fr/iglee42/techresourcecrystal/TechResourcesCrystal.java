package fr.iglee42.techresourcecrystal;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import fr.iglee42.techresourcecrystal.customize.Crystal;
import fr.iglee42.techresourcecrystal.customize.DataGeneratorFactory;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.customize.custompack.TRCPackFinder;
import fr.iglee42.techresourcecrystal.customize.datageneration.*;
import fr.iglee42.techresourcecrystal.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.data.DataGenerator;
import fr.iglee42.techresourcecrystal.customize.custompack.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TechResourcesCrystal.MODID)
public class TechResourcesCrystal {

    public static final String MODID = "techresourcescrystal";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final CreativeModeTab CRYSTAL_GROUP = new CreativeModeTab(MODID + ".crystal_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItem.FRAGMENTED_WATER_CRYSTAL.get());
        }
    };
    private DataGenerator generator;
    private static boolean hasGenerated;
    private static TechResourcesCrystal instance;
    public TechResourcesCrystal() {
        instance = this;
        TypesConstants.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItem.ITEMS.register(bus);
        ModBlock.BLOCKS.register(bus);
        ModBlockEntity.BLOCK_ENTITIES.register(bus);
        ModSounds.REGISTER.register(bus);
        ModEntityType.ENTITY_TYPES.register(bus);
        DataGeneratorFactory.init();
        prepareData();

        transferTextures();


        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStart);
        MinecraftForge.EVENT_BUS.register(this);


        try {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                Minecraft.getInstance().getResourcePackRepository().addPackFinder(new TRCPackFinder(PackType.RESOURCE));
            }
        } catch (Exception ignored) {
        }


    }

    public static void transferTextures() {
        Path texturesPath = FMLPaths.GAMEDIR.get().resolve("techresourcesTextures");
        File texturesDir = texturesPath.toFile();
        texturesDir.mkdirs();
        for (Crystal c : TypesConstants.TYPES){
            String name = c.name();
            LOGGER.info("Copy Texture for crystal " + name);

            //Directories
            File crystalDir = new File(texturesDir + "/" + name);
            crystalDir.mkdirs();
            File outDir = FMLPaths.CONFIGDIR.get().resolve("techresourcescrystal/pack/assets/techresourcescrystal/textures/"+name).toFile();


            try {
                copyDirectory(crystalDir,outDir);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

    }


    private static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdir();
        }
        for (String f : sourceDirectory.list()) {
            copyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory, f));
        }
    }
    private static void copyDirectoryCompatibityMode(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            copyDirectory(source, destination);
        } else {
            copyFile(source, destination);
        }
    }

    private static void copyFile(File sourceFile, File destinationFile)
            throws IOException {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destinationFile)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }
    private void setup(final FMLCommonSetupEvent event) {
    }

    private void prepareData(){
        generator = DataGeneratorFactory.createMemoryDataGenerator();
        ExistingFileHelper existingFileHelper = new ExistingFileHelper(ImmutableList.of(),ImmutableSet.of(),false,null,null);

        generator.addProvider(new CustomsRecipeProvider(generator));
        if (FMLEnvironment.dist != Dist.DEDICATED_SERVER){
            generator.addProvider(new CustomsBlockModelsProvider(generator,existingFileHelper));
            generator.addProvider(new CustomsItemModelsProvider(generator,existingFileHelper));
            generator.addProvider(new CustomsBlockStatesProvider(generator,existingFileHelper));
            generator.addProvider(new CustomsLanguagesProvider(generator,existingFileHelper));
        }


    }

    public void onServerStart(final ServerAboutToStartEvent event) {
        event.getServer().getPackRepository().addPackFinder(new TRCPackFinder(PackType.DATA));
    }

    public static void generateData() {
        if (!hasGenerated) {
            try {
                if (!ModLoader.isLoadingStateValid()){
                    return;
                }
                instance.generator.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
            hasGenerated = true;
        }
    }

    public static void injectDatapackFinder(PackRepository resourcePacks) {
        if (DistExecutor.unsafeRunForDist(() -> () -> resourcePacks != Minecraft.getInstance().getResourcePackRepository(), () -> () -> true)) {
            resourcePacks.addPackFinder(new TRCPackFinder(PackType.RESOURCE));
        }
    }

}
