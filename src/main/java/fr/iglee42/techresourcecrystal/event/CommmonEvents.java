package fr.iglee42.techresourcecrystal.event;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
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

    @SubscribeEvent
    public static void onMobDrops(LivingDropsEvent event) {
        addMobDrop(EntityType.BAT,event,ModItem.BAT_WING.get(),1,1,null);
        addMobDrop(EntityType.CAT,event,ModItem.CAT_HIDE.get(),1,0.5F,null);
        addMobDrop(EntityType.ZOMBIFIED_PIGLIN,event,ModItem.NETHER_FLESH.get(),1,1,Items.ROTTEN_FLESH);
        addMobDrop(EntityType.ZOGLIN,event,ModItem.ZOMBIFIED_PORKCHOP.get(),1,1,Items.ROTTEN_FLESH);
        addMobDrop(EntityType.WOLF,event,ModItem.WOLF_HIDE.get(),1,0.5F,null);
        addMobDrop(EntityType.WITHER_SKELETON,event,ModItem.WITHER_BONE.get(),1,0.5F,Items.BONE);
        addMobDrop(EntityType.POLAR_BEAR,event,ModItem.POLAR_BEAR_FUR.get(),1,0.5F,null);
        addMobDrop(EntityType.MULE,event,ModItem.MULE_LEATHER.get(),1,1.5F,Items.LEATHER);
        addMobDrop(EntityType.LLAMA,event,ModItem.LLAMA_LEATHER.get(),1,0.5F,Items.LEATHER);
        addMobDrop(EntityType.HUSK,event,ModItem.SANDED_FLESH.get(),2,1,Items.ROTTEN_FLESH);
        addMobDrop(EntityType.HOGLIN,event,ModItem.NETHER_LEATHER.get(),1,0.5F,Items.LEATHER);
        addMobDrop(EntityType.GOAT,event,ModItem.GOAT_HORN.get(),0,0.5F,null);
        addMobDrop(EntityType.FOX,event,ModItem.FOX_FUR.get(),1,0.5F,null);
        addMobDrop(EntityType.DROWNED,event,ModItem.DROWNED_FLESH.get(),1,1,Items.ROTTEN_FLESH);
        addMobDrop(EntityType.DOLPHIN,event,ModItem.DOLPHIN_HIDE.get(),1,0.5F,null);
        addMobDrop(EntityType.DONKEY,event,ModItem.DONKEY_LEATHER.get(),1,0.5F,Items.LEATHER);
        addMobDrop(EntityType.ELDER_GUARDIAN,event,ModBlock.PRIMSARINE_SPONGE.get().asItem(),1,0.5F,Items.WET_SPONGE);
    }

    private static void addMobDrop(EntityType type, LivingDropsEvent event, Item it, int baseCount, float perLooting, Item removed){
        if (event.getEntity().getType() != type) return;
        ItemStack stack = new ItemStack(it, (int) (baseCount + (perLooting * event.getLootingLevel())));
        ItemEntity drop = new ItemEntity(event.getEntity().level, event.getEntity().blockPosition().getX(), event.getEntity().blockPosition().getY(), event.getEntity().blockPosition().getZ(), stack);

        if (removed != null) event.getDrops().stream().filter(i->i.getItem().getItem() == removed).findAny().ifPresent(i-> event.getDrops().remove(i));

        event.getDrops().add(drop);
    }


}
