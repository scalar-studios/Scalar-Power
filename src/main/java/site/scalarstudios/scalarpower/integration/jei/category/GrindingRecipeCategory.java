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
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ScalarPower.MODID, "textures/gui/jei_generic_1to2.png");
    private static final int TEXTURE_U = 33;
    private static final int BACKGROUND_WIDTH = 122;
    private static final int BACKGROUND_HEIGHT = 39;

    public static final IRecipeHolderType<GrindingRecipe> TYPE = IRecipeHolderType.create(ScalarPowerRecipes.GRINDING_RECIPE_TYPE);

    private static final int SECONDARY_OUTPUT_X = 94;
    private static final int CHANCE_TEXT_Y = 31;

    private final IDrawableStatic background;
    private final IDrawable icon;

    public GrindingRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, TEXTURE_U, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
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

        builder.addSlot(RecipeIngredientRole.INPUT, 8, 10)
                .addIngredients(grindingRecipe.input());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 68, 10)
                .addItemStack(grindingRecipe.assemble(new SingleRecipeInput(ItemStack.EMPTY)));

        if (grindingRecipe.bonusChance() > 0.0F) {
            ItemStack bonusPreview = grindingRecipe.assemble(new SingleRecipeInput(ItemStack.EMPTY)).copyWithCount(1);
            builder.addSlot(RecipeIngredientRole.OUTPUT, SECONDARY_OUTPUT_X, 10)
                    .addItemStack(bonusPreview);
        }
    }

    @Override
    public void draw(RecipeHolder<GrindingRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);

        GrindingRecipe grindingRecipe = recipe.value();
        if (grindingRecipe.bonusChance() <= 0.0F) {
            return;
        }

        String chanceText = formatChance(grindingRecipe.bonusChance()) + "%";
        int textX = SECONDARY_OUTPUT_X + 9 - Minecraft.getInstance().font.width(chanceText) / 2;
        guiGraphics.text(Minecraft.getInstance().font, chanceText, textX, CHANCE_TEXT_Y, 0xFF4A4A4A, false);
    }

    private static String formatChance(float chance) {
        float percent = chance * 100.0F;
        if (Math.abs(percent - Math.round(percent)) < 0.0001F) {
            return Integer.toString(Math.round(percent));
        }

        return String.format(Locale.ROOT, "%.1f", percent);
    }
}
