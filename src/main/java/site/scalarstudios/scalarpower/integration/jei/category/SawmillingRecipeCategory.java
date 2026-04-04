package site.scalarstudios.scalarpower.integration.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
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
import site.scalarstudios.scalarpower.recipe.SawmillRecipe;
import site.scalarstudios.scalarpower.recipe.ScalarPowerRecipes;

import java.util.Locale;

public class SawmillingRecipeCategory implements IRecipeCategory<RecipeHolder<SawmillRecipe>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ScalarPower.MODID, "textures/gui/jei_generic_1to2.png");
    private static final int TEXTURE_U = 33;
    private static final int BACKGROUND_WIDTH = 122;
    private static final int BACKGROUND_HEIGHT = 39;

    public static final IRecipeHolderType<SawmillRecipe> TYPE = IRecipeHolderType.create(ScalarPowerRecipes.SAWMILLING_RECIPE_TYPE);

    private static final int SECONDARY_OUTPUT_X = 94;
    private static final int CHANCE_TEXT_Y = 31;

    private final IDrawableStatic background;
    private final IDrawable icon;

    public SawmillingRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, TEXTURE_U, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ScalarPowerBlocks.SAWMILL.asItem()));
    }

    @Override
    public IRecipeHolderType<SawmillRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.scalarpower.sawmilling");
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

    @SuppressWarnings("removal")
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<SawmillRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 10)
                .addIngredients(recipe.value().input());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 68, 10)
                .addItemStack(recipe.value().assemble(new SingleRecipeInput(ItemStack.EMPTY)));

        recipe.value().byproductTemplate().ifPresent(byproduct ->
                builder.addSlot(RecipeIngredientRole.OUTPUT, SECONDARY_OUTPUT_X, 10)
                        .addItemStack(byproduct.create().copyWithCount(1)));
    }

    @Override
    public void draw(RecipeHolder<SawmillRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);

        if (recipe.value().byproductTemplate().isEmpty() || recipe.value().byproductChance() <= 0.0F) {
            return;
        }

        String chanceText = formatChance(recipe.value().byproductChance()) + "%";
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
