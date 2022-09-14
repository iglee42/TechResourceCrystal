package fr.iglee42.techresourcecrystal.event;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.init.ModItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TechResourcesCrystal.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EasterClientsEvents {


    @SubscribeEvent
    public static void clientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItem.MOB_MOLD.get(),
                    new ResourceLocation(TechResourcesCrystal.MODID, "easter"), (stack, level, living, id) -> (stack.getDisplayName().getString().equals("[LIMachi]") ? 1.0F : 0.0F));
            ItemProperties.register(ModItem.GOAT_HORN.get(),
                    new ResourceLocation(TechResourcesCrystal.MODID, "easter2"), (stack, level, living, id) -> (stack.getDisplayName().getString().equals("[MLDEG]") ? 1.0F : 0.0F));
        });

    }

}
