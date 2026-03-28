package site.scalarstudios.scalarpower.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class GrindingRecipe extends SingleItemRecipe {
    public static final MapCodec<GrindingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                            Ingredient.CODEC.fieldOf("ingredient").forGetter(SingleItemRecipe::input),
                            ItemStackTemplate.CODEC.fieldOf("result").forGetter(GrindingRecipe::resultTemplate),
                            Codec.floatRange(0.0F, 1.0F).optionalFieldOf("bonus_chance", 0.0F).forGetter(GrindingRecipe::bonusChance),
                            Codec.intRange(1, 64).optionalFieldOf("bonus_count", 1).forGetter(GrindingRecipe::bonusCount))
                    .apply(i, GrindingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GrindingRecipe> STREAM_CODEC = StreamCodec.composite(
            Recipe.CommonInfo.STREAM_CODEC,
            o -> o.commonInfo,
            Ingredient.CONTENTS_STREAM_CODEC,
            SingleItemRecipe::input,
            ItemStackTemplate.STREAM_CODEC,
            GrindingRecipe::resultTemplate,
            ByteBufCodecs.FLOAT,
            GrindingRecipe::bonusChance,
            ByteBufCodecs.VAR_INT,
            GrindingRecipe::bonusCount,
            GrindingRecipe::new);

    public static final RecipeSerializer<GrindingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final float bonusChance;
    private final int bonusCount;

    public GrindingRecipe(Recipe.CommonInfo commonInfo, Ingredient ingredient, ItemStackTemplate result) {
        this(commonInfo, ingredient, result, 0.0F, 1);
    }

    public GrindingRecipe(Recipe.CommonInfo commonInfo, Ingredient ingredient, ItemStackTemplate result, float bonusChance, int bonusCount) {
        super(commonInfo, ingredient, result);
        this.bonusChance = bonusChance;
        this.bonusCount = bonusCount;
    }

    public float bonusChance() {
        return bonusChance;
    }

    public int bonusCount() {
        return bonusCount;
    }

    private ItemStackTemplate resultTemplate() {
        return result();
    }

    @Override
    public RecipeType<GrindingRecipe> getType() {
        return ScalarPowerRecipes.GRINDING_RECIPE_TYPE;
    }

    @Override
    public RecipeSerializer<GrindingRecipe> getSerializer() {
        return ScalarPowerRecipes.GRINDING_RECIPE_SERIALIZER;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}

