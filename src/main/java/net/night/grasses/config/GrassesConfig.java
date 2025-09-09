package net.night.grasses.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class GrassesConfig {

    public static class CommonConfig {

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHOP_TREE_AT_ONCE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CUT_LEAVES_AT_ONCE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CUT_WART_AT_ONCE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CUT_VINES_AT_ONCE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CONNECT_SAME_KIND_LOG;

        public final ModConfigSpec.ConfigValue<Integer> LOGS_MAX_AMOUNT_AT_ONCE;
        public final ModConfigSpec.ConfigValue<Integer> STEMS_MAX_AMOUNT_AT_ONCE;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_CACTUS;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_MOD_CACTUS;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_SUGAR_CANE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_MOD_SUGAR_CANE;
        /**/public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_CACTUS_LIKE_PLANTS;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_VANILLA_LILY_PAD;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_MOD_LILY_PAD;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_VANILLA_VINES;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_MOD_VINES;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_SMALL_FLOWERS;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_VANILLA_MYCELIUM;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_MOD_MYCELIUM;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_VANILLA_PODZOL;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_MOD_PODZOL;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_VANILLA_SOUL_SAND;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_MOD_SOUL_SAND;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_BONE_MEAL_ON_BOP_PLANTS;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_NOT_GRASSES_LEAVES_INTO_TINTED_SEVERALLY;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_TINTED_LEAVES_INTO_NOT_GRASSES_SEVERALLY;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_NOT_GRASSES_LEAVES_INTO_TINTED_AT_ONCE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_TINTED_LEAVES_INTO_NOT_GRASSES_AT_ONCE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_LEAVES_COLOR_SEVERALLY;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_LEAVES_COLOR_AT_ONCE;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_NOT_GRASSES_PLANTS_INTO_TINTED;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_TINTED_PLANTS_INTO_NOT_GRASSES;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_PLANTS_COLOR;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_NOT_GRASSES_VINES_INTO_TINTED_SEVERALLY;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_TINTED_VINES_INTO_NOT_GRASSES_SEVERALLY;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_NOT_GRASSES_VINES_INTO_TINTED_AT_ONCE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_TINTED_VINES_INTO_NOT_GRASSES_AT_ONCE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_VINES_COLOR_SEVERALLY;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_VINES_COLOR_AT_ONCE;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_POTTED_NOT_GRASSES_PLANTS_INTO_TINTED;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_POTTED_TINTED_PLANTS_INTO_NOT_GRASSES;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_POTTED_PLANT_COLOR;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_SHEAR_ON_TREE_SAPLING;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CRAFT_AUTO_PRUNER;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CRAFT_DYEING_TOOL;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CRAFT_DYEING_BONEMEAL;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CRAFT_DYE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CRAFT_DYEING_STATION;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CRAFT_BAMBOO_PAPER;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CRAFT_ICE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CRAFT_STRING;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CRAFT_END_STONE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CRAFT_END_PORTAL_FRAME;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_SMELT_QUARTZ_FROM_DIORITE;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_FIND_AUTO_PRUNER;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_FIND_DYEING_TOOL;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_FIND_DYEING_BONEMEAL;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_FIND_DYE;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_FIND_DYEING_STATION;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_COLOR_OF_DYEING_TOOL;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_CHANGE_COLOR_OF_DYEING_BONEMEAL;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_APPLY_INFINITY_ON_DYEING_TOOL;

        public final ModConfigSpec.ConfigValue<Integer> CACTUS_MAX_HEIGHT;
        public final ModConfigSpec.ConfigValue<Integer> SUGAR_CANE_MAX_HEIGHT;
        /**/public final ModConfigSpec.ConfigValue<Integer> CACTUS_LIKE_PLANTS_1;
        /**/public final ModConfigSpec.ConfigValue<Integer> CACTUS_LIKE_PLANTS_2;
        /**/public final ModConfigSpec.ConfigValue<Integer> CACTUS_LIKE_PLANTS_3;
        /**/public final ModConfigSpec.ConfigValue<Integer> CACTUS_LIKE_PLANTS_1_CHANCE;
        /**/public final ModConfigSpec.ConfigValue<Integer> CACTUS_LIKE_PLANTS_2_CHANCE;
        /**/public final ModConfigSpec.ConfigValue<Integer> CACTUS_LIKE_PLANTS_3_CHANCE;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_PUT_ANY_TOP_SLAB_FIRST;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_PUT_GRASSES_TOP_SLAB_FIRST;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_SHEAR_ON_STICKY_PISTON;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_SLIME_BALL_ON_PISTON;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_SPREAD_MOD_NYLIUM;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_SPREAD_MOD_PODZOL;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_SHEAR_ON_END_FRAME;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_SHEAR_ON_ER_END_FRAME;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_USE_NETHERITE_AUTO_PRUNER_ON_END_FRAME;

        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_COMPOSTING_BAMBOO;
        public final ModConfigSpec.ConfigValue<Boolean> ALLOW_COMPOSTING_ROTTER_FLESH;

        CommonConfig(ModConfigSpec.Builder builder) {
            builder.comment("Cutting Tree").push("cutting_tree");

            ALLOW_CHOP_TREE_AT_ONCE = builder.comment("Let the entire tree be cut down at once [true/false]")
                    .define("Chop Tree", true);
            ALLOW_CUT_LEAVES_AT_ONCE = builder.comment("Let use auto pruner to cut all the leaves off the tree at once [true/false]")
                    .define("Cut Leaves", true);
            ALLOW_CUT_VINES_AT_ONCE = builder.comment("Let use auto pruner to cut all the vines off the tree at once [true/false]")
                    .define("Cut Vines", true);
            ALLOW_CUT_WART_AT_ONCE = builder.comment("Let use auto pruner to cut all the wart off the huge fungus at once [true/false]")
                    .define("Cut Wart", true);

            ALLOW_CONNECT_SAME_KIND_LOG = builder.comment("Let cut tree when logs not same, but same kind e.g oak_log && stripped_oak_log (work when sprint key is push)  [true/false]")
                    .define("Connect Same Logs", true);

            LOGS_MAX_AMOUNT_AT_ONCE = builder.comment("How many logs can be cut at once (default value let cut every vanilla tree at once. IMPORTANT - large values may cause lag!")
                    .defineInRange("Logs At Once", 150, 10, 500);
            STEMS_MAX_AMOUNT_AT_ONCE = builder.comment("How many stems can be cut at once (default value let cut every vanilla fungus at once. IMPORTANT - large values may cause lag!")
                    .defineInRange("Stems At Once", 30, 5, 100);

            builder.pop();

            builder.comment("Vanilla Bone meal Behaviours").push("vanilla_bonemeal_behaviours");

            ALLOW_USE_BONE_MEAL_ON_CACTUS = builder.comment("Let use bone meal on vanilla cactus [true/false]")
                    .define("BM Vanilla Cactus", true);
            ALLOW_USE_BONE_MEAL_ON_MOD_CACTUS = builder.comment("Let use bone meal on tinted cactus [true/false]")
                    .define("BM Mod Cactus", true);
            ALLOW_USE_BONE_MEAL_ON_SUGAR_CANE = builder.comment("Let use bone meal on vanilla sugar cane [true/false]")
                    .define("BM Vanilla Sugar Cane", true);
            ALLOW_USE_BONE_MEAL_ON_MOD_SUGAR_CANE = builder.comment("Let use bone meal on tinted sugar cane [true/false]")
                    .define("BM Mod Sugar Cane", true);
            ALLOW_USE_BONE_MEAL_ON_CACTUS_LIKE_PLANTS = builder.comment("Let use bone meal on cactus like plants from another mods [true/false]")
                    .define("BM Like Cactus", true);
            ALLOW_USE_BONE_MEAL_ON_VANILLA_LILY_PAD = builder.comment("Let use bone meal on vanilla lily pad [true/false]")
                    .define("BM Vanilla Lily", true);
            ALLOW_USE_BONE_MEAL_ON_MOD_LILY_PAD = builder.comment("Let use bone meal on tinted lily pad [true/false]")
                    .define("BM Mod Lily", true);
            ALLOW_USE_BONE_MEAL_ON_VANILLA_VINES = builder.comment("Let use bone meal on vanilla vines [true/false]")
                    .define("BM Vanilla Vines", true);
            ALLOW_USE_BONE_MEAL_ON_MOD_VINES = builder.comment("Let use bone meal on tinted vines [true/false]")
                    .define("BM Mod Vines", true);
            ALLOW_USE_BONE_MEAL_ON_SMALL_FLOWERS = builder.comment("Let use bone meal on small flowers [true/false]")
                    .define("BM Small Flowers", true);
            ALLOW_USE_BONE_MEAL_ON_VANILLA_MYCELIUM = builder.comment("Let use bone meal on vanilla mycelium [true/false]")
                    .define("BM Vanilla Mycelium", true);
            ALLOW_USE_BONE_MEAL_ON_MOD_MYCELIUM = builder.comment("Let use bone meal on mod mycelium [true/false]")
                    .define("BM Mod Mycelium", true);
            ALLOW_USE_BONE_MEAL_ON_VANILLA_PODZOL = builder.comment("Let use bone meal on vanilla podzol [true/false]")
                    .define("BM Vanilla Podzol", true);
            ALLOW_USE_BONE_MEAL_ON_MOD_PODZOL = builder.comment("Let use bone meal on mod podzol [true/false]")
                    .define("BM Mod Podzol", true);
            ALLOW_USE_BONE_MEAL_ON_VANILLA_SOUL_SAND = builder.comment("Let use bone meal on vanilla soul sand [true/false]")
                    .define("BM Vanilla Soul Sand", true);
            ALLOW_USE_BONE_MEAL_ON_MOD_SOUL_SAND = builder.comment("Let use bone meal on mod soul sand [true/false]")
                    .define("BM Mod Soul Sand", true);

            ALLOW_USE_BONE_MEAL_ON_BOP_PLANTS = builder.comment("Let use bone meal on BOP plants and on their tinted versions [true/false]")
                    .define("BM on BOP Plants", true);

            builder.pop();

            builder.comment("Leaves changing Behaviours").push("leaves_changing_behaviours");

            ALLOW_CHANGE_NOT_GRASSES_LEAVES_INTO_TINTED_SEVERALLY = builder.comment("Let use shears on not 'Grasses' leaves to change into tinted leaves [true/false]")
                    .define("Not 'Grasses' leaves into tinted by shears", true);
            ALLOW_CHANGE_TINTED_LEAVES_INTO_NOT_GRASSES_SEVERALLY = builder.comment("Let use shears on tinted leaves to change into not 'Grasses' leaves [true/false]")
                    .define("Tinted leaves into 'Grasses' by shears", true);
            ALLOW_CHANGE_NOT_GRASSES_LEAVES_INTO_TINTED_AT_ONCE = builder.comment("Let use auto pruner on logs to change not 'Grasses' leaves into tinted leaves [true/false]")
                    .define("Not 'Grasses' leaves into tinted by auto pruner", true);
            ALLOW_CHANGE_TINTED_LEAVES_INTO_NOT_GRASSES_AT_ONCE = builder.comment("Let use auto pruner on logs to change tinted leaves into not 'Grasses' leaves [true/false]")
                    .define("Tinted leaves into not 'Grasses' by auto pruner", true);
            ALLOW_CHANGE_LEAVES_COLOR_SEVERALLY = builder.comment("Let use dyeing tool on tinted leaves to change color [true/false]")
                    .define("CT Change color severally", true);
            ALLOW_CHANGE_LEAVES_COLOR_AT_ONCE = builder.comment("Let use dyeing tool on logs to change tinted leaves color [true/false]")
                    .define("CT Change color at once", true);

            builder.pop();

            builder.comment("Plants changing Behaviours").push("plants_changing_behaviours");

            ALLOW_CHANGE_NOT_GRASSES_PLANTS_INTO_TINTED = builder.comment("Let use shears on not 'Grasses' plants to change into tinted plants (except vines) [true/false]")
                    .define("Not 'Grasses' plants change into tinted", true);
            ALLOW_CHANGE_TINTED_PLANTS_INTO_NOT_GRASSES = builder.comment("Let use shears on tinted plants (except vines) to change into not 'Grasses' [true/false]")
                    .define("Tinted plants change into not 'Grasses'", true);
            ALLOW_CHANGE_PLANTS_COLOR = builder.comment("Let use Dyeing Tool on tinted plants (except vines) to change color [true/false]")
                    .define("CT change plant color", true);

            ALLOW_CHANGE_NOT_GRASSES_VINES_INTO_TINTED_SEVERALLY = builder.comment("Let use shears on not 'Grasses' vines to change into tinted vine severally [true/false]")
                    .define("Change vine into tinted severally", true);
            ALLOW_CHANGE_TINTED_VINES_INTO_NOT_GRASSES_SEVERALLY = builder.comment("Let use shears on tinted vines to change into not 'Grasses' vine severally [true/false]")
                    .define("Change vine into not 'Grasses' severally", true);
            ALLOW_CHANGE_NOT_GRASSES_VINES_INTO_TINTED_AT_ONCE = builder.comment("Let Auto Pruner on log to change not 'Grasses' vine into tinted at once [true/false]")
                    .define("Not 'Grasses' vines into tinted by Auto Pruner", true);
            ALLOW_CHANGE_TINTED_VINES_INTO_NOT_GRASSES_AT_ONCE = builder.comment("Let Auto Pruner on log to change tinted vine into not 'Grasses' at once [true/false]")
                    .define("Tinted vines into not 'Grasses' by auto pruner", true);
            ALLOW_CHANGE_VINES_COLOR_SEVERALLY = builder.comment("Let use Dyeing Tool on tinted vine to change color [true/false]")
                    .define("CT change vine color severally", true);
            ALLOW_CHANGE_VINES_COLOR_AT_ONCE = builder.comment("Let use Dyeing Tool on log to change vines color [true/false]")
                    .define("CT change vines at once", true);

            ALLOW_CHANGE_POTTED_NOT_GRASSES_PLANTS_INTO_TINTED = builder.comment("Let use shears on not 'Grasses' potted plants to change into tinted potted plants [true/false]")
                    .define("Potted plant change into tinted", true);
            ALLOW_CHANGE_POTTED_TINTED_PLANTS_INTO_NOT_GRASSES = builder.comment("Let use shears on tinted potted plants to change into not 'Grasses' potted plant [true/false]")
                    .define("Potted tinted plant change into potted", true);
            ALLOW_CHANGE_POTTED_PLANT_COLOR = builder.comment("Let use Dyeing Tool on tinted potted plants to change color [true/false]")
                    .define("CT potted plant color", true);

            ALLOW_USE_SHEAR_ON_TREE_SAPLING = builder.comment("Let use shears on tree sapling [true/false]")
                    .define("Cut Tree Sapling", true);

            builder.pop();

            builder.comment("Crafting and Loot").push("crafting_and_loot");

            ALLOW_CRAFT_DYEING_BONEMEAL = builder.comment("Let craft Dyeing Bone Meal (still can be obtained when find in chests) [true/false]")
                    .define("Craft Dyeing Bone Meal", true);
            ALLOW_CRAFT_DYE = builder.comment("Let craft Dye (still can be obtained when find in chests) [true/false]")
                    .define("Craft Dyeing Dye", true);
            ALLOW_CRAFT_AUTO_PRUNER = builder.comment("Let craft Auto Pruner (still can be obtained when find in chests) [true/false]")
                    .define("Craft Auto Pruner", true);
            ALLOW_CRAFT_DYEING_TOOL = builder.comment("Let craft Dyeing Tool (still can be obtained when find in chests) [true/false]")
                    .define("Craft Dyeing Tool", true);
            ALLOW_CRAFT_DYEING_STATION = builder.comment("Let craft Dyeing Station (still can be obtained when find in chests) [true/false]")
                    .define("Craft Dyeing Station", true);
            ALLOW_CRAFT_BAMBOO_PAPER = builder.comment("Let craft 3 paper using 6 bamboo sticks) [true/false]")
                    .define("Craft Paper", true);
            ALLOW_CRAFT_ICE = builder.comment("Let craft ice blocks using snow blocks and water bucket) [true/false]")
                    .define("Craft Ice", true);
            ALLOW_CRAFT_STRING = builder.comment("Let craft back 3 string using white wool block) [true/false]")
                    .define("Craft String", true);
            ALLOW_CRAFT_END_STONE = builder.comment("Let craft End Stones using sandstones and ender pearl) [true/false]")
                    .define("Craft End Stone", true);
            ALLOW_CRAFT_END_PORTAL_FRAME = builder.comment("Let craft End Portal Frame using End Stones, Dragon Head, Nether Star and End Crystal) [true/false]")
                    .define("Craft End Portal", true);
            ALLOW_SMELT_QUARTZ_FROM_DIORITE = builder.comment("Let smelt diorite block into quartz) [true/false]")
                    .define("Smelt Diorite", true);

            ALLOW_FIND_AUTO_PRUNER = builder.comment("Let find Auto Pruner (still can be obtained by crafting) [true/false]")
                    .define("Find Auto Pruner", true);
            ALLOW_FIND_DYEING_TOOL = builder.comment("Let find Dyeing Tool (still can be obtained by crafting)  [true/false]")
                    .define("Find Dyeing Tool", true);
            ALLOW_FIND_DYEING_BONEMEAL = builder.comment("Let find Dyeing Bone Meal (still can be obtained by crafting)  [true/false]")
                    .define("Find Dyeing Bone Meal", true);
            ALLOW_FIND_DYE = builder.comment("Let find Dye (still can be obtained by crafting)  [true/false]")
                    .define("Find Dyeing Dye", true);
            ALLOW_FIND_DYEING_STATION = builder.comment("Let find Dyeing Bone Meal  (still can be obtained by crafting)  [true/false]")
                    .define("Find Dyeing Station", true);

            ALLOW_CHANGE_COLOR_OF_DYEING_TOOL = builder.comment("Let change color of tool in Dyeing Station [true/false]")
                    .define("Change Dyeing Tool", true);
            ALLOW_CHANGE_COLOR_OF_DYEING_BONEMEAL = builder.comment("Let change color of tinted bone meal in Dyeing Station [true/false]")
                    .define("Change Dyeing Bone Meal", true);

            ALLOW_APPLY_INFINITY_ON_DYEING_TOOL = builder.comment("Allow enchanting dyeing tool with infinity arrow enchant. [true/false]")
                    .define("Infinity on CT", true);

            builder.pop();

            builder.comment("Max height of some plants").push("max_height");

            CACTUS_MAX_HEIGHT = builder.comment("How many stage can have cactus if use bone meal (vanilla = 3)")
                    .defineInRange("Cactus Stage", 3, 2, 10);
            SUGAR_CANE_MAX_HEIGHT = builder.comment("How many stage can have sugar cane if use bone meal (vanilla = 4)")
                    .defineInRange("Sugar Cane Stage", 4, 2, 12);
            CACTUS_LIKE_PLANTS_1 = builder.comment("How many stage can have cactus like plant from another mods if use bone meal")
                    .defineInRange("Cactus Like Stage 1", 3, 2, 15);
            CACTUS_LIKE_PLANTS_2 = builder.comment("How many stage can have cactus like plant from another mods if use bone meal")
                    .defineInRange("Cactus Like Stage 2", 3, 2, 15);
            CACTUS_LIKE_PLANTS_3 = builder.comment("How many stage can have cactus like plant from another mods if use bone meal")
                    .defineInRange("Cactus Like Stage 3", 3, 2, 15);

            CACTUS_LIKE_PLANTS_1_CHANCE = builder.comment("Percentage chance that bone meal will work")
                    .defineInRange("Cactus Like Chance 1", 100, 1, 100);
            CACTUS_LIKE_PLANTS_2_CHANCE = builder.comment("Percentage chance that bone meal will work")
                    .defineInRange("Cactus Like Chance 2", 100, 1, 100);
            CACTUS_LIKE_PLANTS_3_CHANCE = builder.comment("Percentage chance that bone meal will work")
                    .defineInRange("Cactus Like Chance 3", 100, 1, 100);

            builder.pop();

            builder.comment("Additional").push("additional");

            ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB = builder.comment("By default, the mod obstruct planting plants on the bottom slab. This setting disables it.")
                    .define("Plants on bottom", false);

            ALLOW_PUT_ANY_TOP_SLAB_FIRST = builder.comment("Allow any top slab to be placed first while holding the sprint button.")
                    .define("All Top Slab First", true);

            ALLOW_PUT_GRASSES_TOP_SLAB_FIRST = builder.comment("Allow GRASSES top slab to be placed first while holding the sprint button.")
                    .define("Grasses Top Slab First", false);

            ALLOW_USE_SHEAR_ON_STICKY_PISTON = builder.comment("Let cut sticky layer from sticky piston and turn into piston with obtain slime ball [true/false]")
                    .define("Shears on Sticky Piston", true);
            ALLOW_USE_SLIME_BALL_ON_PISTON = builder.comment("Let put slime ball on piston and turn into sticky piston [true/false]")
                    .define("Slime ball on piston", true);
            ALLOW_SPREAD_MOD_NYLIUM = builder.comment("Let mod Nylium for spread like grass block [true/false]")
                    .define("Spread Mod Nylium", true);
            ALLOW_SPREAD_MOD_PODZOL = builder.comment("Let mod Podzol for spread like grass block [true/false]")
                    .define("Spread Mod Podzol", true);
            ALLOW_USE_SHEAR_ON_END_FRAME = builder.comment("Let use shear on End Portal Frame to extract back Ender Eye [true/false]")
                    .define("Shears on End Frame", true);
            ALLOW_USE_SHEAR_ON_ER_END_FRAME = builder.comment("Let use shear on Ancient Portal Frame from 'End Remastered' mod to extract back Eyes [true/false]")
                    .define("Shears on Ancient Frame", true);
            ALLOW_USE_NETHERITE_AUTO_PRUNER_ON_END_FRAME = builder.comment("Let use Netherite Auto Pruner on End Portal Frame to mine End Portal Frame (works only on Crafted Frame) [true/false]")
                    .define("Shears on End Frame", true);
            ALLOW_COMPOSTING_BAMBOO = builder.comment("Let the bamboo be compostable [true/false]")
                    .define("Compostable Bamboo", true);
            ALLOW_COMPOSTING_ROTTER_FLESH = builder.comment("Let the rotten flesh be compostable [true/false]")
                    .define("Compostable Rotten Flesh", true);
        }

    }
    //Client

    public static class ClientConfig {
        public final ModConfigSpec.ConfigValue<Boolean> DISABLE_VANILLA_PLANTS_OFFSET;


        ClientConfig(ModConfigSpec.Builder builder) {
            builder.comment("General").push("general");

            DISABLE_VANILLA_PLANTS_OFFSET = builder.comment("Turn off Vanilla behaviours that make offset of plants like grass (plants will be centered on block")
                    .define("Disable offset", false);
        }
    }

    public static ModConfigSpec COMMON_CONFIG_SPEC;
    public static CommonConfig COMMON_CONFIG;

    public static ModConfigSpec CLIENT_CONFIG_SPEC;
    public static ClientConfig CLIENT_CONFIG;

    static {
        final Pair<CommonConfig, ModConfigSpec> commmonSpecPair = new ModConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_CONFIG_SPEC = commmonSpecPair.getRight();
        COMMON_CONFIG = commmonSpecPair.getLeft();

        final Pair<ClientConfig, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_CONFIG_SPEC = clientSpecPair.getRight();
        CLIENT_CONFIG = clientSpecPair.getLeft();
    }

}
