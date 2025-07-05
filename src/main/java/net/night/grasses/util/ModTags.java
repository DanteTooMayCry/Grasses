package net.night.grasses.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.night.grasses.Grasses;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> ALL_MOD_GRASS_BLOCKS          = tag("all_mod_grass_blocks");
        public static final TagKey<Block> ALL_MOD_GRASS_SLABS           = tag("all_mod_grass_slabs");
        public static final TagKey<Block> ALL_MOD_GRASS                 = tag("all_mod_grass");
        public static final TagKey<Block> ALL_MOD_LEAVES                = tag("all_mod_leaves");
        public static final TagKey<Block> OTHER_GRASS_BLOCKS            = tag("other_grass_blocks");
        public static final TagKey<Block> BLACKLIST_LOGS                = tag("blacklist_logs");
        public static final TagKey<Block> LEAVES_LOGS_ADVANCEMENTS      = tag("leaves_logs_adv");

        public static final TagKey<Block> PODZOL_BLOCKS                 = tag("podzol_blocks");
        public static final TagKey<Block> MYCELIUM_BLOCKS               = tag("mycelium_blocks");
        public static final TagKey<Block> NYLIUM_SLAB_BLOCKS            = tag("nylium_slab_blocks");
        public static final TagKey<Block> NYLIUM_BLOCKS                 = tag("nylium_blocks");
        public static final TagKey<Block> CRIMSON_NYLIUM_BLOCKS         = tag("crimson_nylium_blocks");
        public static final TagKey<Block> WARPED_NYLIUM_BLOCKS          = tag("warped_nylium_blocks");
        public static final TagKey<Block> BLOCKS_ON_CRIMSON_FUNGUS      = tag("blocks_on_crimson_fungus");
        public static final TagKey<Block> BLOCKS_ON_WARPED_FUNGUS       = tag("blocks_on_warped_fungus");
        public static final TagKey<Block> STEMS                         = tag("stems");
        public static final TagKey<Block> NETHERRACK_BASE_BLOCKS        = tag("netherrack_base_block");
        public static final TagKey<Block> SOUL_SAND_BLOCKS              = tag("soul_sand_blocks");
        public static final TagKey<Block> BONE_MEAL_ABLE_BLOCKS         = tag("bone_meal_able_blocks");
        public static final TagKey<Block> UNDERWATER_TINTED_BONEMEALS   = tag("underwater_tinted_bonemeals");
        public static final TagKey<Block> MOD_BIG_DRIPLEAF_PLACEABLE    = tag("mod_big_dripleaf_placeable");

        public static final TagKey<Block> CACTUS_LIKE_PLANTS_1          = tag("cactus_like_plants_1");
        public static final TagKey<Block> CACTUS_LIKE_PLANTS_2          = tag("cactus_like_plants_2");
        public static final TagKey<Block> CACTUS_LIKE_PLANTS_3          = tag("cactus_like_plants_3");

        public static final TagKey<Block> CONVERTABLE_SLAB_TO_MUD       = tag("convertable_slab_to_mud");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Grasses.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item>  SHEARS                        = tag("shears");
        public static final TagKey<Item>  PRUNERS                       = tag("pruners");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Grasses.MOD_ID, name));
        }
    }
}
