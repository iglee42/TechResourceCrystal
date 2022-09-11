package fr.iglee42.techresourcecrystal.event;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModItem;
import fr.iglee42.techresourcecrystal.utils.InventoryUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Timer;
import java.util.TimerTask;

@Mod.EventBusSubscriber(modid = TechResourcesCrystal.MODID)
public class CommmonEvents {

    @SubscribeEvent
    public static void entityInteract(final PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getPlayer().getMainHandItem().getItem() == Items.AIR) return;
        if (event.getPlayer().getMainHandItem().getItem() == Items.AMETHYST_BLOCK) {

            Timer timer = new Timer();
            if (event.getTarget().getType() == EntityType.BLAZE) {
                if (event.getTarget().getTags().contains("inCooldown")) {
                    event.getPlayer().displayClientMessage(new TextComponent("§cThis mob is in cooldown"), true);
                    return;
                }
                event.getPlayer().getMainHandItem().setCount(event.getPlayer().getMainHandItem().getCount() - 1);
                Block.popResource(event.getTarget().getLevel(), event.getTarget().getOnPos().offset(0, 1, 0), new ItemStack(ModBlock.FRAGMENTED_FIRE_CRYSTAL.get()));
                event.getTarget().addTag("inCooldown");
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        event.getTarget().removeTag("inCooldown");
                        timer.cancel();
                    }
                }, 30 * 1000L);
                //event.getTarget().remove(Entity.RemovalReason.KILLED);
            } else if (event.getTarget().getType() == EntityType.CREEPER) {
                if (event.getTarget().getTags().contains("inCooldown")) {
                    event.getPlayer().displayClientMessage(new TextComponent("§cThis mob is in cooldown"), true);
                    return;
                }
                event.getPlayer().getMainHandItem().setCount(event.getPlayer().getMainHandItem().getCount() - 1);
                Block.popResource(event.getTarget().getLevel(), event.getTarget().getOnPos().offset(0, 1, 0), new ItemStack(ModBlock.FRAGMENTED_EARTH_CRYSTAL.get()));
                event.getTarget().addTag("inCooldown");
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        event.getTarget().removeTag("inCooldown");
                        timer.cancel();
                    }
                }, 30 * 1000L);
                //event.getTarget().remove(Entity.RemovalReason.KILLED);
            } else if (event.getTarget().getType() == EntityType.GUARDIAN) {
                if (event.getTarget().getTags().contains("inCooldown")) {
                    event.getPlayer().displayClientMessage(new TextComponent("§cThis mob is in cooldown"), true);
                    return;
                }
                event.getPlayer().getMainHandItem().setCount(event.getPlayer().getMainHandItem().getCount() - 1);
                Block.popResource(event.getTarget().getLevel(), event.getTarget().getOnPos().offset(0, 1, 0), new ItemStack(ModBlock.FRAGMENTED_WATER_CRYSTAL.get()));
                event.getTarget().addTag("inCooldown");
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        event.getTarget().removeTag("inCooldown");
                        timer.cancel();
                    }
                }, 30 * 1000L);
                //event.getTarget().remove(Entity.RemovalReason.KILLED);
            } else if (event.getTarget().getType() == EntityType.PHANTOM) {
                if (event.getTarget().getTags().contains("inCooldown")) {
                    event.getPlayer().displayClientMessage(new TextComponent("§cThis mob is in cooldown"), true);
                    return;
                }
                event.getPlayer().getMainHandItem().setCount(event.getPlayer().getMainHandItem().getCount() - 1);
                Block.popResource(event.getTarget().getLevel(), event.getTarget().getOnPos().offset(0, 1, 0), new ItemStack(ModBlock.FRAGMENTED_AIR_CRYSTAL.get()));
                event.getTarget().addTag("inCooldown");
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        event.getTarget().removeTag("inCooldown");
                        timer.cancel();
                    }
                }, 30 * 1000L);
                //event.getTarget().remove(Entity.RemovalReason.KILLED);
            }
        }


    }

}
