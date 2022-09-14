package fr.iglee42.techresourcecrystal;

import fr.iglee42.techresourcecrystal.init.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public TechResourcesCrystal() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItem.ITEMS.register(bus);
        ModBlock.BLOCKS.register(bus);
        ModTiles.TILES.register(bus);
        ModSounds.REGISTER.register(bus);
        ModEntityType.ENTITY_TYPES.register(bus);



        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

}
