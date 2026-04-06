package site.scalarstudios.scalarpower.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;

import java.util.Optional;

/**
 * Recipe type for the Macerator and Double Macerator.
 *
 * Primary output is always produced (ore doubling via chunks).
 * Secondary output (byproduct) is produced with a configurable chance.
 */
public class MaceratingRecipe extends SingleItemRecipe {
    public static final MapCodec<MaceratingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                            Ingredient.CODEC.fieldOf("ingredient").forGetter(SingleItemRecipe::input),
                            ItemStackTemplate.CODEC.fieldOf("result").forGetter(MaceratingRecipe::resultTemplate),
                            ItemStackTemplate.CODEC.optionalFieldOf("byproduct").forGetter(MaceratingRecipe::byproductTemplate),
                            Codec.floatRange(0.0F, 1.0F).optionalFieldOf("byproduct_chance", 0.0F).forGetter(MaceratingRecipe::byproductChance))
                    .apply(i, MaceratingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MaceratingRecipe> STREAM_CODEC = StreamCodec.composite(
            Recipe.CommonInfo.STREAM_CODEC,
            o -> o.commonInfo,
            Ingredient.CONTENTS_STREAM_CODEC,
            SingleItemRecipe::input,
            ItemStackTemplate.STREAM_CODEC,
            MaceratingRecipe::resultTemplate,
            ByteBufCodecs.optional(ItemStackTemplate.STREAM_CODEC),
            MaceratingRecipe::byproductTemplate,
            ByteBufCodecs.FLOAT,
            MaceratingRecipe::byproductChance,
            MaceratingRecipe::new);

    public static final RecipeSerializer<MaceratingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final Optional<ItemStackTemplate> byproduct;
    private final float byproductChance;

    public MaceratingRecipe(Recipe.CommonInfo commonInfo, Ingredient ingredient, ItemStackTemplate result,
                             Optional<ItemStackTemplate> byproduct, float byproductChance) {
        super(commonInfo, ingredient, result);
        this.byproduct = byproduct;
        this.byproductChance = byproductChance;
    }

    public Optional<ItemStackTemplate> byproductTemplate() {
        return byproduct;
    }

    public float byproductChance() {
        return byproductChance;
    }

    public ItemStack assembleByproduct() {
        return byproduct.map(ItemStackTemplate::create).orElse(ItemStack.EMPTY);
    }

    private ItemStackTemplate resultTemplate() {
        return result();
    }

    @Override
    public RecipeType<MaceratingRecipe> getType() {
        return ScalarPowerRecipes.MACERATING_RECIPE_TYPE;
    }

    @Override
    public RecipeSerializer<MaceratingRecipe> getSerializer() {
        return ScalarPowerRecipes.MACERATING_RECIPE_SERIALIZER;
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

