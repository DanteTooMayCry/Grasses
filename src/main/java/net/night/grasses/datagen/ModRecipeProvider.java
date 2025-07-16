package net.night.grasses.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.recipe.recipeConditions.*;

import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.world.item.Items.PAPER;
import static net.minecraft.world.item.Items.STRING;
import static net.minecraft.world.level.block.Blocks.*;
import static net.night.grasses.data.DataLib.ingredientsList;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.ItemsRegister.*;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {


        int j = 0;

        //Start from 1, skip "grow_grass_block"
        for (int i = 1; i < grassRegistryBlocksList.size(); i++) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, grassRegistryBlocksList.get(i).get(), 1)
                    .pattern("   ")
                    .pattern("4D6")
                    .pattern("   ")
                    .define('4', Items.GRASS)
                    .define('D', GRASS_BLOCK)
                    .define('6', (ItemLike) ingredientsList.get(j))
                    .unlockedBy(getHasName((ItemLike) ingredientsList.get(j)), has((ItemLike) ingredientsList.get(j)))
                    .save(consumer);
            j++;
        }
        for (int i = 1; i < grassRegistrySlabBlocksList.size(); i++) {
            standardSlabShape(grassRegistrySlabBlocksList.get(i).get(), grassRegistryBlocksList.get(i).get(), consumer);
        }

        j = 0;
        for (int i = 1; i < grassRegistrySlabBlocksList.size(); i++) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, grassRegistrySlabBlocksList.get(i).get(), 1)
                    .pattern("   ")
                    .pattern("4D6")
                    .pattern("   ")
                    .define('4', Items.GRASS)
                    .define('D', GRASS_SLAB_BLOCK.get())
                    .define('6', (ItemLike) ingredientsList.get(j))
                    .unlockedBy(getHasName((ItemLike) ingredientsList.get(j)), has((ItemLike) ingredientsList.get(j)))
                    .save(consumer, grassRegistrySlabBlocksList.get(i).getId() + "_alter");
            j++;
        }

        // Dirt Like:
        growBlocksShape(grassRegistryBlocksList.get(0).get(), GRASS_BLOCK, consumer);
        standardSlabShape(grassRegistrySlabBlocksList.get(0).get(), GRASS_BLOCK, consumer);
        alterSlabShape(grassRegistrySlabBlocksList.get(0), GROW_GRASS_BLOCK.get(), consumer);
        standardSlabShape(DIRT_SLAB_BLOCK.get(), DIRT, consumer);
        standardSlabShape(COARSE_DIRT_SLAB_BLOCK.get(), COARSE_DIRT, consumer);
        alterCoarseDirt(COARSE_DIRT_SLAB_BLOCK, consumer);
        standardSlabShape(ROOTED_DIRT_SLAB_BLOCK.get(), ROOTED_DIRT, consumer);
        growBlocksShape(GROW_MYCELIUM_BLOCK.get(), MYCELIUM, consumer);
        standardSlabShape(MYCELIUM_SLAB_BLOCK.get(), MYCELIUM, consumer);
        alterSlabShape(MYCELIUM_SLAB_BLOCK, GROW_MYCELIUM_BLOCK.get(), consumer);
        growBlocksShape(GROW_PODZOL_BLOCK.get(), PODZOL, consumer);
        standardSlabShape(PODZOL_SLAB_BLOCK.get(), PODZOL, consumer);
        alterSlabShape(PODZOL_SLAB_BLOCK, GROW_PODZOL_BLOCK.get(), consumer);

        // Netherrack Like:
        growBlocksShape(GROW_CRIMSON_NYLIUM_BLOCK.get(), CRIMSON_NYLIUM, consumer);
        growBlocksShape(GROW_WARPED_NYLIUM_BLOCK.get(), WARPED_NYLIUM, consumer);
        standardSlabShape(CRIMSON_NYLIUM_SLAB_BLOCK.get(), CRIMSON_NYLIUM, consumer);
        standardSlabShape(WARPED_NYLIUM_SLAB_BLOCK.get(), WARPED_NYLIUM, consumer);
        alterSlabShape(CRIMSON_NYLIUM_SLAB_BLOCK, GROW_CRIMSON_NYLIUM_BLOCK.get(), consumer);
        alterSlabShape(WARPED_NYLIUM_SLAB_BLOCK, GROW_WARPED_NYLIUM_BLOCK.get(), consumer);
        standardSlabShape(NETHERRACK_SLAB_BLOCK.get(), NETHERRACK, consumer);

        standardSlabShape(SAND_SLAB_BLOCK.get(), SAND, consumer);
        standardSlabShape(RED_SAND_SLAB_BLOCK.get(), RED_SAND, consumer);
        standardSlabShape(GRAVEL_SLAB_BLOCK.get(), GRAVEL, consumer);
        standardSlabShape(CLAY_SLAB_BLOCK.get(), CLAY, consumer);
        alterBlockFromBalls(CLAY_SLAB_BLOCK, Items.CLAY_BALL, consumer);

        standardSlabShape(SOUL_SAND_SLAB_BLOCK.get(), SOUL_SAND, consumer);
        standardSlabShape(SOUL_SOIL_SLAB_BLOCK.get(), SOUL_SOIL, consumer);
        standardSlabShape(MAGMA_SLAB_BLOCK.get(), MAGMA_BLOCK, consumer);
        alterBlockFromBalls(MAGMA_SLAB_BLOCK, Items.MAGMA_CREAM, consumer);

        standardSlabShape(MOSS_SLAB_BLOCK.get(), MOSS_BLOCK, consumer);
        standardSlabShape(MUD_SLAB_BLOCK.get(), MUD, consumer);
        standardSlabShape(PACKED_MUD_SLAB_BLOCK.get(), PACKED_MUD, consumer);
        standardSlabShape(MUDDY_ROOTS_SLAB_BLOCK.get(), MUDDY_MANGROVE_ROOTS, consumer);

        standardSlabShape(ICE_SLAB_BLOCK.get(), ICE, consumer);
        standardSlabShape(PACKED_ICE_SLAB_BLOCK.get(), PACKED_ICE, consumer);
        alterFullShape(PACKED_ICE_SLAB_BLOCK, ICE_SLAB_BLOCK.get(), consumer);
        standardSlabShape(BLUE_ICE_SLAB_BLOCK.get(), BLUE_ICE, consumer);
        alterFullShape(BLUE_ICE_SLAB_BLOCK, PACKED_ICE_SLAB_BLOCK.get(), consumer);

        ConditionalRecipe.builder().addCondition(DyeingStationRecipeCondition.INSTANCE)
                .addRecipe(conditionalConsumer ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DYEING_STATION.get(), 1)
                                .pattern("DID")
                                .pattern("SIS")
                                .pattern("DDD")
                                .define('D', Blocks.DEEPSLATE)
                                .define('I', Items.IRON_INGOT)
                                .define('S', Blocks.STONE)
                                .unlockedBy(getHasName((ItemLike) Blocks.DEEPSLATE), has((ItemLike) Blocks.DEEPSLATE))
                                .save(conditionalConsumer))
                .build(consumer, DYEING_STATION.getId());

        ConditionalRecipe.builder().addCondition(DiamondPrunerRecipeCondition.INSTANCE)
                .addRecipe(conditionalConsumer ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DIAMOND_AUTO_PRUNER.get(), 1)
                                .pattern("  D")
                                .pattern("RI ")
                                .pattern("I  ")
                                .define('D', Items.DIAMOND)
                                .define('I', Items.IRON_INGOT)
                                .define('R', Items.REDSTONE)
                                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                                .save(conditionalConsumer))
                .build(consumer, DIAMOND_AUTO_PRUNER.getId());

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(DIAMOND_AUTO_PRUNER.get()),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        RecipeCategory.TOOLS,
                        NETHERITE_AUTO_PRUNER.get()).
                unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                .save(consumer, NETHERITE_AUTO_PRUNER.getId() + "_smithing");

        ConditionalRecipe.builder().addCondition(DyeingToolRecipeCondition.INSTANCE)
                .addRecipe(conditionalConsumer ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DYEING_TOOL.get(), 1)
                            .pattern("GIC")
                            .pattern("RBI")
                            .pattern("BR ")
                            .define('G', Items.GLASS_BOTTLE)
                            .define('I', Items.IRON_INGOT)
                            .define('R', Items.REDSTONE)
                            .define('B', Items.BAMBOO)
                            .define('C', Blocks.CRYING_OBSIDIAN)
                            .unlockedBy(getHasName((ItemLike) Blocks.CRYING_OBSIDIAN), has((ItemLike) Blocks.CRYING_OBSIDIAN))
                            .save(conditionalConsumer))
                .build(consumer, DYEING_TOOL.getId());




        ConditionalRecipe.builder().addCondition(BambooPaperRecipeCondition.INSTANCE)
                .addRecipe(conditionalConsumer ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PAPER, 3)
                                .pattern("   ")
                                .pattern("BBB")
                                .pattern("BBB")
                                .define('B', Items.BAMBOO)
                                .unlockedBy(getHasName((ItemLike) Blocks.BAMBOO), has((ItemLike) Blocks.BAMBOO))
                                .save(conditionalConsumer))
                .build(consumer, new ResourceLocation("grasses:item/paper"));


        ConditionalRecipe.builder().addCondition(IceRecipeCondition.INSTANCE)
                .addRecipe(conditionalConsumer ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ICE, 8)
                                .pattern("SSS")
                                .pattern("SWS")
                                .pattern("SSS")
                                .define('S', SNOW_BLOCK)
                                .define('W', Items.WATER_BUCKET)
                                .unlockedBy(getHasName((ItemLike) SNOW_BLOCK), has((ItemLike) SNOW_BLOCK))
                                .save(conditionalConsumer))
                .build(consumer, new ResourceLocation("grasses:block/ice"));

        ConditionalRecipe.builder().addCondition(StringRecipeCondition.INSTANCE)
                .addRecipe(conditionalConsumer ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC , STRING, 3)
                                .pattern("   ")
                                .pattern(" W ")
                                .pattern("   ")
                                .define('W', WHITE_WOOL)
                                .unlockedBy(getHasName((ItemLike) WHITE_WOOL), has((ItemLike) WHITE_WOOL))
                                .save(conditionalConsumer))
                .build(consumer, new ResourceLocation("grasses:item/string"));

        ConditionalRecipe.builder().addCondition(EndStoneRecipeCondition.INSTANCE)
                .addRecipe(conditionalConsumer ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, END_STONE, 8)
                                .pattern("SSS")
                                .pattern("SES")
                                .pattern("SSS")
                                .define('S', SANDSTONE)
                                .define('E', Items.ENDER_PEARL)
                                .unlockedBy(getHasName((ItemLike) Items.ENDER_PEARL), has((ItemLike) Items.ENDER_PEARL))
                                .save(conditionalConsumer))
                .build(consumer, new ResourceLocation("grasses:block/end_stone"));

        ConditionalRecipe.builder().addCondition(EndPortalFrameRecipeCondition.INSTANCE)
                .addRecipe(conditionalConsumer ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, END_PORTAL_FRAME_BLOCK.get(), 4)
                                .pattern("ENE")
                                .pattern("SDS")
                                .pattern("ECE")
                                .define('N', Items.NETHER_STAR)
                                .define('D', Items.DRAGON_HEAD)
                                .define('C', Items.END_CRYSTAL)
                                .define('S', Items.ECHO_SHARD)
                                .define('E', END_STONE)
                                .unlockedBy(getHasName((ItemLike) Items.DRAGON_HEAD), has((ItemLike) Items.DRAGON_HEAD))
                                .save(conditionalConsumer))
                .build(consumer, new ResourceLocation("grasses:block/end_portal_frame_block"));

        ConditionalRecipe.builder().addCondition(QuartzRecipeCondition.INSTANCE)
                .addRecipe(conditionalConsumer ->
                        oreSmelting(conditionalConsumer, List.of(DIORITE), RecipeCategory.MISC, Items.QUARTZ, 0.25f, 100, "quartz"))
                .build(consumer, new ResourceLocation("grasses:item/quartz_from_smelting_diorite"));
        ConditionalRecipe.builder().addCondition(QuartzRecipeCondition.INSTANCE)
                .addRecipe(conditionalConsumer ->
                        oreSmelting(conditionalConsumer, List.of(GRANITE), RecipeCategory.MISC, Items.QUARTZ, 0.25f, 100, "quartz"))
                .build(consumer, new ResourceLocation("grasses:item/quartz_from_smelting_granite"));
        ConditionalRecipe.builder().addCondition(QuartzRecipeCondition.INSTANCE)
                .addRecipe(conditionalConsumer ->
                        oreSmelting(conditionalConsumer, List.of(ANDESITE), RecipeCategory.MISC, Items.QUARTZ, 0.25f, 100, "quartz"))
                .build(consumer, new ResourceLocation("grasses:item/quartz_from_smelting_andesite"));
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private static void growBlocksShape (Block outPutblock, Block inputBlock, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outPutblock, 1)
                .pattern("   ")
                .pattern(" # ")
                .pattern("   ")
                .define('#', (ItemLike) inputBlock)
                .unlockedBy(getHasName((ItemLike) inputBlock), has((ItemLike) inputBlock))
                .save(consumer);
    }

    private static void standardSlabShape (Block outPutblock, Block inputBlock, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outPutblock, 6)
                .pattern("   ")
                .pattern("###")
                .pattern("   ")
                .define('#', (ItemLike) inputBlock)
                .unlockedBy(getHasName((ItemLike) inputBlock), has((ItemLike) inputBlock))
                .save(consumer);
    }
    private static void alterSlabShape(RegistryObject<Block> outPutblock, Block inputBlock, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outPutblock.get(), 6)
                .pattern("   ")
                .pattern("###")
                .pattern("   ")
                .define('#', (ItemLike) inputBlock)
                .unlockedBy(getHasName((ItemLike) inputBlock), has((ItemLike) inputBlock))
                .save(consumer, (outPutblock.getId()).toString().concat("_alter"));
    }

    private static void alterCoarseDirt(RegistryObject<Block> outPutblock, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outPutblock.get(), 1)
                .pattern("DG ")
                .pattern("GD ")
                .pattern("   ")
                .define('D', (ItemLike) DIRT_SLAB_BLOCK.get())
                .define('G', (ItemLike) GRAVEL_SLAB_BLOCK.get())
                .unlockedBy(getHasName((ItemLike) GRAVEL_SLAB_BLOCK.get()), has((ItemLike) GRAVEL_SLAB_BLOCK.get()))
                .save(consumer, (outPutblock.getId()).toString().concat("_alter"));
    }

    private static void alterBlockFromBalls(RegistryObject<Block> outPutblock, Item inputBlock, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outPutblock.get(), 1)
                .pattern("   ")
                .pattern("CC ")
                .pattern("   ")
                .define('C', inputBlock)
                .unlockedBy(getHasName(inputBlock), has(inputBlock))
                .save(consumer, (outPutblock.getId()).toString().concat("_alter"));
    }
    private static void alterFullShape(RegistryObject<Block> outPutblock, Block inputBlock, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outPutblock.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', (ItemLike) inputBlock)
                .unlockedBy(getHasName((ItemLike) inputBlock), has((ItemLike) inputBlock))
                .save(consumer, (outPutblock.getId()).toString().concat("_alter"));
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }
}
