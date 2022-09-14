package fr.iglee42.techresourcecrystal.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
        p_41423_.add(new TextComponent("§e").append(new TranslatableComponent("information.dropby")).append(new TranslatableComponent(this.entity.getDescriptionId())));
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }
}
