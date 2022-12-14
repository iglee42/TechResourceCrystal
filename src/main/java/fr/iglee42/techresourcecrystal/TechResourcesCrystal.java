package fr.iglee42.techresourcecrystal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import fr.iglee42.techresourcecrystal.customize.Crystal;
import fr.iglee42.techresourcecrystal.customize.CustomRecipes;
import fr.iglee42.techresourcecrystal.customize.DataGeneratorFactory;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.customize.custompack.PackType;
import fr.iglee42.techresourcecrystal.customize.custompack.TRCPackFinder;
import fr.iglee42.techresourcecrystal.customize.datageneration.*;
import fr.iglee42.techresourcecrystal.init.*;
import fr.iglee42.techresourcecrystal.jei.CrystalsJEIPlugin;
import fr.iglee42.techresourcecrystal.theoneprobe.TOPIMC;
import net.minecraft.client.Minecraft;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TechResourcesCrystal.MODID)
public class TechResourcesCrystal {

    public static final String MODID = "techresourcescrystal";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final CreativeModeTab CRYSTAL_GROUP = new CreativeModeTab(MODID + ".crystal_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItem.UNIFIED_CRYSTAL.get());
        }
    };
    public static final CreativeModeTab MOBS_GROUP = new CreativeModeTab(MODID + ".mobs_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItem.AXOLOTL_SCUTE.get());
        }
    };
    private DataGenerator generator;
    private static boolean hasGenerated;
    private static TechResourcesCrystal instance;
    public static boolean isTOPLoaded;
    public TechResourcesCrystal() {
        instance = this;
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItem.ITEMS.register(bus);
        ModItem.MOBS_ITEMS.register(bus);
        ModBlock.BLOCKS.register(bus);
        ModBlockEntity.BLOCK_ENTITIES.register(bus);
        ModSounds.REGISTER.register(bus);
        ModEntityType.ENTITY_TYPES.register(bus);
        CustomRecipes.SERIALIZER.register(bus);
        DataGeneratorFactory.init();
        TypesConstants.init();

        prepareData();

        transferTextures();


        MinecraftForge.EVENT_BUS.addListener(this::onServerStart);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarted);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStopping);

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
            if (c.isInModBase()) continue;
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


    private void prepareData(){
        generator = DataGeneratorFactory.createMemoryDataGenerator();
        ExistingFileHelper existingFileHelper = new ExistingFileHelper(ImmutableList.of(),ImmutableSet.of(),false,null,null);

        generator.addProvider(true,new CustomsRecipeProvider(generator));
        generator.addProvider(true,new CustomsTagsProvider.Items(generator,new CustomsTagsProvider.Blocks(generator,MODID,existingFileHelper),MODID,existingFileHelper));
        generator.addProvider(true,new CustomsTagsProvider.Blocks(generator,MODID,existingFileHelper));
        if (FMLEnvironment.dist != Dist.DEDICATED_SERVER){
            generator.addProvider(true,new CustomsBlockModelsProvider(generator,existingFileHelper));
            generator.addProvider(true,new CustomsItemModelsProvider(generator,existingFileHelper));
            generator.addProvider(true,new CustomsBlockStatesProvider(generator,existingFileHelper));
            generator.addProvider(true,new CustomsLanguagesProvider(generator,existingFileHelper));

        }


    }

    public void onServerStarted(final ServerStartedEvent event) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                PackRepository repo = event.getServer().getPackRepository();
                List<Pack> packs = Lists.newArrayList(repo.getSelectedPacks());
                event.getServer().reloadResources(packs.stream().map(Pack::getId).collect(Collectors.toList()));
                this.cancel();
            }
        },5000L);
    }
    public void onServerStart(final ServerAboutToStartEvent event) {
        event.getServer().getPackRepository().addPackFinder(new TRCPackFinder(PackType.DATA));

    }

    public void onServerStopping(final ServerStoppingEvent event){
        event.getServer().getAllLevels().forEach(lvl->{
            List<Entity> entities = new ArrayList<>();
            lvl.getEntities().getAll().forEach(e -> entities.add(e));
            entities.stream().filter(e->e.getTags().contains("crystaliserArmorStand")).forEach(e->e.remove(Entity.RemovalReason.KILLED));
        });
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
