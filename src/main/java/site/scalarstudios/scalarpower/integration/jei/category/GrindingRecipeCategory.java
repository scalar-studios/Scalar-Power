package site.scalarstudios.scalarpower.integration.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import site.scalarstudios.scalarpower.ScalarPower;
import site.scalarstudios.scalarpower.block.ScalarPowerBlocks;
import site.scalarstudios.scalarpower.recipe.GrindingRecipe;
import site.scalarstudios.scalarpower.recipe.ScalarPowerRecipes;

import java.util.Locale;

public class GrindingRecipeCategory implements IRecipeCategory<RecipeHolder<GrindingRecipe>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ScalarPower.MODID, "textures/gui/jei_generic_1to1.png");
    private static final int BACKGROUND_WIDTH = 176;
    private static final int BACKGROUND_HEIGHT = 82;

    public static final IRecipeHolderType<GrindingRecipe> TYPE = IRecipeHolderType.create(ScalarPowerRecipes.GRINDING_RECIPE_TYPE);

    private final IDrawableStatic background;
    private final IDrawable icon;

    public GrindingRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ScalarPowerBlocks.GRINDER.asItem()));
    }

    @Override
    public IRecipeHolderType<GrindingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.scalarpower.grinding");
    }

    @Override
    public int getWidth() {
        return BACKGROUND_WIDTH;
    }

    @Override
    public int getHeight() {
        return BACKGROUND_HEIGHT;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @SuppressWarnings("removal") // This will need to be addressed at some point
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<GrindingRecipe> recipe, IFocusGroup focuses) {
        GrindingRecipe grindingRecipe = recipe.value();

        builder.addSlot(RecipeIngredientRole.INPUT, 56, 35)
                .addIngredients(grindingRecipe.input());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 35)
                .addItemStack(grindingRecipe.assemble(new SingleRecipeInput(ItemStack.EMPTY)));
    }

    @Override
    public void draw(RecipeHolder<GrindingRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);

        GrindingRecipe grindingRecipe = recipe.value();
        if (grindingRecipe.bonusChance() <= 0.0F) {
            return;
        }

        String chanceText = formatChance(grindingRecipe.bonusChance());
        Component bonusText = Component.translatable(
                "jei.scalarpower.grinding.bonus_output",
                chanceText,
                grindingRecipe.bonusCount());
        guiGraphics.text(Minecraft.getInstance().font, bonusText, 8, 64, 0xFF4A4A4A, false);
    }

    private static String formatChance(float bonusChance) {
        float percent = bonusChance * 100.0F;
        if (Math.abs(percent - Math.round(percent)) < 0.0001F) {
            return Integer.toString(Math.round(percent));
        }

        return String.format(Locale.ROOT, "%.1f", percent);
    }
}
