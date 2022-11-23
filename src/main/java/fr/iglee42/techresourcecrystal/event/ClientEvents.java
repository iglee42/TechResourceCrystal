package fr.iglee42.techresourcecrystal.event;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.client.renderer.entity.FreezeArrowRenderer;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModEntityType;
import fr.iglee42.techresourcecrystal.init.ModItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TechResourcesCrystal.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {


    @SubscribeEvent
    public static void clientStuff(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlock.CRYSTALISER.get(), RenderType.cutoutMipped());

        Minecraft.getInstance().getItemColors().register(ClientEvents::getTint, ModItem.CAPTURE_STONE.get());
    }

    public static int getTint(ItemStack stack,int tintIndex){
            SpawnEggItem info = null;
            if (stack.getTag() != null && stack.getTag().contains("type")) {
                ResourceLocation id = new ResourceLocation(stack.getTag().getString("type"));
                info = SpawnEggItem.byId(ForgeRegistries.ENTITY_TYPES.getValue(id));
            }
            return info != null ? (tintIndex == 1 ? info.getColor(0) : (tintIndex == 2 ? info.getColor(1) : 0XFFFFFF )): 0XFFFFFF;
    }

    @SubscribeEvent
    public static void rendererStuff(final EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntityType.FREEZE_ARROW.get(), FreezeArrowRenderer::new);
    }


}
