package fr.iglee42.techresourcecrystal.jei;

import com.google.gson.JsonPrimitive;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.iglee42.igleelib.api.utils.ModsUtils;
import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.customize.crystaliserRecipe.CrystaliserRecipe;
import fr.iglee42.techresourcecrystal.init.ModBlock;
import fr.iglee42.techresourcecrystal.init.ModItem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;

import static fr.iglee42.techresourcecrystal.block.entity.CrystaliserBlockEntity.getPropType;

public class CrystaliserRecipeCategory implements IRecipeCategory<CrystaliserRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(TechResourcesCrystal.MODID, "crystaliser");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(TechResourcesCrystal.MODID, "textures/gui/crystaliser_jei.png");

    public static final RecipeType<CrystaliserRecipe> RECIPE_TYPE = RecipeType.create(TechResourcesCrystal.MODID, "crystaliser",
            CrystaliserRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public CrystaliserRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 92);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlock.CRYSTALISER.get()));
    }


    @Override
    public RecipeType<CrystaliserRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Crystaliser");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(CrystaliserRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        //this.resultArrow.draw(stack,117,18);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull CrystaliserRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        IRecipeSlotBuilder block = builder.addSlot(RecipeIngredientRole.INPUT, 65, 60).addIngredients(recipe.getIngredient());
        block.addTooltipCallback((recipeSlotView, tooltip) -> {
            tooltip.add(Component.literal("Required Properties :").withStyle(ChatFormatting.GOLD));
            recipe.getRequiredProperties().keySet().forEach(s->{
                Block b = Block.byItem(recipe.getIngredient().getItems()[0].getItem());
                JsonPrimitive prim = recipe.getRequiredProperties().get(s);
                String prop = ModsUtils.getUpperName(s,"_");
                if (getPropType(b.getStateDefinition().getProperty(s)) == 1) {
                    tooltip.add(Component.literal(prop).withStyle(ChatFormatting.GREEN).append(Component.literal(" : ").withStyle(ChatFormatting.GRAY)).append(Component.literal(String.valueOf(prim.getAsInt())).withStyle(ChatFormatting.DARK_GREEN)));
                } else if (getPropType(b.getStateDefinition().getProperty(s)) == 0) {
                    tooltip.add(Component.literal(prop).withStyle(ChatFormatting.GREEN).append(Component.literal(" : ").withStyle(ChatFormatting.GRAY)).append(Component.literal(String.valueOf(prim.getAsBoolean())).withStyle(ChatFormatting.DARK_GREEN)));

                } else if (getPropType(b.getStateDefinition().getProperty(s)) == 2){
                    tooltip.add(Component.literal(prop).withStyle(ChatFormatting.GREEN).append(Component.literal(" : ").withStyle(ChatFormatting.GRAY)).append(Component.literal(prim.getAsString()).withStyle(ChatFormatting.DARK_GREEN)));
                }
            });
        });
        builder.addSlot(RecipeIngredientRole.CATALYST, 152, 35).addItemStack(new ItemStack(ModItem.CRYSTALISER_MOLD.get()));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 153, 12).addItemStack(recipe.getResultItem());

    }
}