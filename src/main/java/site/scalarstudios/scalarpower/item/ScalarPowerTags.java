package site.scalarstudios.scalarpower.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import site.scalarstudios.scalarpower.ScalarPower;

public class ScalarPowerTags {
    public static final TagKey<Item> RAW_IRON_GRINDABLE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ScalarPower.MODID, "raw_iron_grindable"));
    public static final TagKey<Item> RAW_GOLD_GRINDABLE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ScalarPower.MODID, "raw_gold_grindable"));
    public static final TagKey<Item> RAW_COPPER_GRINDABLE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ScalarPower.MODID, "raw_copper_grindable"));
    public static final TagKey<Item> C_RAW_MATERIALS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "raw_materials"));
    public static final TagKey<Item> C_INGOTS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "ingots"));
}
