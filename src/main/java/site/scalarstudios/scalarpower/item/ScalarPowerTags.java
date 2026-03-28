package site.scalarstudios.scalarpower.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ScalarPowerTags {
    public static final TagKey<Item> C_RAW_MATERIALS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "raw_materials"));
    public static final TagKey<Item> C_INGOTS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "ingots"));
}
