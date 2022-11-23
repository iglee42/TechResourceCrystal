package fr.iglee42.techresourcecrystal.init;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> MINEABLE_PICKAXE = BlockTags.create(new ResourceLocation("minecraft","mineable/pickaxe"));

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(TechResourcesCrystal.MODID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Items {
        public static final TagKey<Item> FRAGMENTED_CRYSTALS = tag("fragmented_crystals");
        public static final TagKey<Item> CRYSTALS = tag("crystals");
        public static final TagKey<Item> PLATES = forgeTag("plate");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(TechResourcesCrystal.MODID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}