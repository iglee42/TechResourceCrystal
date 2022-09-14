package fr.iglee42.techresourcecrystal.item;

import fr.iglee42.techresourcecrystal.init.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ItemGoatHorn extends ItemMobDrop {
    public ItemGoatHorn(Item.Properties props) {
        super(EntityType.GOAT,props);
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 140;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        SoundEvent soundevent = ModSounds.ITEM_GOAT_HORN_BASE.get();
        if (itemstack.getDisplayName().getString().equals("[MLDEG]")){
            if (new Random().nextInt(2) == 0){
                soundevent = ModSounds.ITEM_GOAT_HORN_EASTER_0.get();
            } else {
                soundevent = ModSounds.ITEM_GOAT_HORN_EASTER_1.get();
            }
        }
        float f = 256/ 16.0F;
        level.playSound(player, player.blockPosition(), soundevent, SoundSource.RECORDS, f, 1.0F);
        player.getCooldowns().addCooldown(this, 140);
        return InteractionResultHolder.consume(itemstack);

    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        if (p_41421_.getDisplayName().getString().equals("[MLDEG]")){ p_41423_.add(new TextComponent("Gne Gne Drakonic"));p_41423_.add(new TextComponent("https://www.youtube.com/c/MrMldeg"));}
        else p_41423_.add(new TextComponent("Ponder"));
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }
}
