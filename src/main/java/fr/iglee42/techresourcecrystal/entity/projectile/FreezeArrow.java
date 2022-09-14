package fr.iglee42.techresourcecrystal.entity.projectile;

import fr.iglee42.techresourcecrystal.init.ModEntityType;
import fr.iglee42.techresourcecrystal.init.ModItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FreezeArrow extends AbstractArrow {

    private final Item arrowItem;

    public FreezeArrow(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
        this.arrowItem = ModItem.FREEZE_ARROW.get();
    }

    public FreezeArrow(LivingEntity entity, Level level) {
        super(ModEntityType.FREEZE_ARROW.get(), entity, level);
        this.arrowItem = ModItem.FREEZE_ARROW.get();
    }


    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(arrowItem);
    }
}
