package fr.iglee42.techresourcecrystal.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCaptureStone extends Item {

    public ItemCaptureStone(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack p_41458_) {
        return containsEntity(p_41458_) ? new TranslatableComponent(getDescriptionId()).append(" (").append(new TranslatableComponent(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(p_41458_.getTag().getString("type"))).getDescriptionId())).append(")") : new TranslatableComponent(getDescriptionId());
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getClickedFace();
        Level worldIn = context.getLevel();
        ItemStack stack = context.getItemInHand();
        if (player.getCommandSenderWorld().isClientSide) return InteractionResult.FAIL;
        if (!containsEntity(stack)) return InteractionResult.FAIL;
        Entity entity = getEntityFromStack(stack, worldIn, true);
        BlockPos blockPos = pos.relative(facing);
        entity.absMoveTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0, 0);
        stack.setTag(new CompoundTag());
        worldIn.addFreshEntity(entity);
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity target, InteractionHand hand) {
        CompoundTag tag = itemStack.getOrCreateTag();
        if (!tag.contains("type")){
            tag.putString("type",target.getType().getRegistryName().toString());
            tag.put("mob",target.serializeNBT());
            itemStack.setTag(tag);
            target.remove(Entity.RemovalReason.KILLED);
        }
        player.swing(hand);
        player.setItemInHand(hand, itemStack);
        return InteractionResult.SUCCESS;
    }

    public boolean containsEntity(ItemStack stack) {
        return !stack.isEmpty() && stack.hasTag() && stack.getTag().contains("mob");
    }

    @Nullable
    public Entity getEntityFromStack(ItemStack stack, Level world, boolean withInfo) {
        if (stack.hasTag()) {
            EntityType type = EntityType.byString(stack.getTag().getString("type")).orElse(null);
            if (type != null) {
                Entity entity = type.create(world);
                if (withInfo) {
                    entity.load(stack.getTag().getCompound("mob"));
                } else if (!type.canSummon()) {
                    return null;
                }
                return entity;
            }
        }
        return null;
    }


}
