package fr.iglee42.techresourcecrystal.customize;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.event.ClientEvents;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CustomizeEvents {

    @Mod.EventBusSubscriber(modid = TechResourcesCrystal.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class Client {


        @SubscribeEvent
        public static void clientStuff(final FMLClientSetupEvent event) {
            for (Crystal c : TypesConstants.TYPES){
                ItemBlockRenderTypes.setRenderLayer(ModBlock.getCrystalCore(c.name()),RenderType.translucent());
            }
        }
    }
}
