package fr.iglee42.techresourcecrystal.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemMobDrop extends Item {
    private EntityType entity;

    public ItemMobDrop(EntityType entity, Item.Properties tab) {
        super(tab);
        this.entity = entity;
    }


    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(Component.translatable("information.dropby").withStyle(ChatFormatting.YELLOW).append(Component.translatable(this.entity.getDescriptionId()).withStyle(ChatFormatting.GOLD)));
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }
}
