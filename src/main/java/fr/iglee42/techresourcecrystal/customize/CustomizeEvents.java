package fr.iglee42.techresourcecrystal.customize;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Timer;
import java.util.TimerTask;

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

    @Mod.EventBusSubscriber(modid = TechResourcesCrystal.MODID)
    public class Commons {

        @SubscribeEvent
        public static void mobInteractEvent(PlayerInteractEvent.EntityInteractSpecific event){
            if (event.getPlayer().getMainHandItem().getItem() == Items.AIR) return;
            for (Crystal c : TypesConstants.TYPES){
                if (event.getPlayer().getMainHandItem().getItem() != c.mobItem()) continue;
                if (event.getTarget().getType() != c.entity()) continue;
                Timer timer = new Timer();
                    if (event.getTarget().getTags().contains("inCooldown")) {
                        event.getPlayer().displayClientMessage(new TextComponent("§cThis mob is in cooldown"), true);
                        return;
                    }
                    event.getPlayer().getMainHandItem().setCount(event.getPlayer().getMainHandItem().getCount() - 1);
                    Block.popResource(event.getTarget().getLevel(), event.getTarget().getOnPos().offset(0, 1, 0), new ItemStack(ModBlock.getFragmentedCrystal(c.name())));
                    event.getTarget().addTag("inCooldown");
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            event.getTarget().removeTag("inCooldown");
                            timer.cancel();
                        }
                    }, 30 * 1000L);
            }
        }

    }
}
