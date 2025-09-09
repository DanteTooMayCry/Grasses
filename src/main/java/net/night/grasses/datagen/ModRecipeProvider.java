package net.night.grasses.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.night.grasses.Grasses;
import net.night.grasses.recipe.recipeConditions.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.minecraft.world.item.Items.PAPER;
import static net.minecraft.world.item.Items.STRING;
import static net.minecraft.world.level.block.Blocks.*;
import static net.night.grasses.data.ModData.ingredientsList;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.ItemsRegister.*;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {


        int j = 0;

        //Start from 1, skip "grow_grass_block"
        for (int i = 1; i < grassRegistryBlocksList.size(); i++) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, grassRegistryBlocksList.get(i).get(), 1)
                    .pattern("   ")
                    .pattern("4D6")
                    .pattern("   ")
                    .define('4', Items.SHORT_GRASS)
                    .define('D', GRASS_BLOCK)
                    .define('6', (ItemLike) ingredientsList.get(j))
                    .unlockedBy(getHasName((ItemLike) ingredientsList.get(j)), has((ItemLike) ingredientsList.get(j)))
                    .save(recipeOutput);
            j++;
        }
        for (int i = 1; i < grassRegistrySlabBlocksList.size(); i++) {
            standardSlabShape(grassRegistrySlabBlocksList.get(i).get(), grassRegistryBlocksList.get(i).get(), recipeOutput);
        }

        j = 0;
        for (int i = 1; i < grassRegistrySlabBlocksList.size(); i++) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, grassRegistrySlabBlocksList.get(i).get(), 1)
                    .pattern("   ")
                    .pattern("4D6")
                    .pattern("   ")
                    .define('4', Items.SHORT_GRASS)
                    .define('D', GRASS_SLAB_BLOCK.get())
                    .define('6', (ItemLike) ingredientsList.get(j))
                    .unlockedBy(getHasName((ItemLike) ingredientsList.get(j)), has((ItemLike) ingredientsList.get(j)))
                    .save(recipeOutput, grassRegistrySlabBlocksList.get(i).getId() + "_alter");
            j++;
        }

        // Dirt Like:
        growBlocksShape(grassRegistryBlocksList.get(0).get(), GRASS_BLOCK, recipeOutput);
        standardSlabShape(grassRegistrySlabBlocksList.get(0).get(), GRASS_BLOCK, recipeOutput);
        alterSlabShape(grassRegistrySlabBlocksList.get(0), GROW_GRASS_BLOCK.get(), recipeOutput);
        standardSlabShape(DIRT_SLAB_BLOCK.get(), DIRT, recipeOutput);
        standardSlabShape(COARSE_DIRT_SLAB_BLOCK.get(), COARSE_DIRT, recipeOutput);
        alterCoarseDirt(COARSE_DIRT_SLAB_BLOCK, recipeOutput);
        standardSlabShape(ROOTED_DIRT_SLAB_BLOCK.get(), ROOTED_DIRT, recipeOutput);
        growBlocksShape(GROW_MYCELIUM_BLOCK.get(), MYCELIUM, recipeOutput);
        standardSlabShape(MYCELIUM_SLAB_BLOCK.get(), MYCELIUM, recipeOutput);
        alterSlabShape(MYCELIUM_SLAB_BLOCK, GROW_MYCELIUM_BLOCK.get(), recipeOutput);
        growBlocksShape(GROW_PODZOL_BLOCK.get(), PODZOL, recipeOutput);
        standardSlabShape(PODZOL_SLAB_BLOCK.get(), PODZOL, recipeOutput);
        alterSlabShape(PODZOL_SLAB_BLOCK, GROW_PODZOL_BLOCK.get(), recipeOutput);

        // Netherrack Like:
        growBlocksShape(GROW_CRIMSON_NYLIUM_BLOCK.get(), CRIMSON_NYLIUM, recipeOutput);
        growBlocksShape(GROW_WARPED_NYLIUM_BLOCK.get(), WARPED_NYLIUM, recipeOutput);
        standardSlabShape(CRIMSON_NYLIUM_SLAB_BLOCK.get(), CRIMSON_NYLIUM, recipeOutput);
        standardSlabShape(WARPED_NYLIUM_SLAB_BLOCK.get(), WARPED_NYLIUM, recipeOutput);
        alterSlabShape(CRIMSON_NYLIUM_SLAB_BLOCK, GROW_CRIMSON_NYLIUM_BLOCK.get(), recipeOutput);
        alterSlabShape(WARPED_NYLIUM_SLAB_BLOCK, GROW_WARPED_NYLIUM_BLOCK.get(), recipeOutput);
        standardSlabShape(NETHERRACK_SLAB_BLOCK.get(), NETHERRACK, recipeOutput);

        standardSlabShape(SAND_SLAB_BLOCK.get(), SAND, recipeOutput);
        standardSlabShape(RED_SAND_SLAB_BLOCK.get(), RED_SAND, recipeOutput);
        standardSlabShape(GRAVEL_SLAB_BLOCK.get(), GRAVEL, recipeOutput);
        standardSlabShape(CLAY_SLAB_BLOCK.get(), CLAY, recipeOutput);
        alterBlockFromBalls(CLAY_SLAB_BLOCK, Items.CLAY_BALL, recipeOutput);

        standardSlabShape(SOUL_SAND_SLAB_BLOCK.get(), SOUL_SAND, recipeOutput);
        standardSlabShape(SOUL_SOIL_SLAB_BLOCK.get(), SOUL_SOIL, recipeOutput);
        standardSlabShape(MAGMA_SLAB_BLOCK.get(), MAGMA_BLOCK, recipeOutput);
        alterBlockFromBalls(MAGMA_SLAB_BLOCK, Items.MAGMA_CREAM, recipeOutput);

        standardSlabShape(MOSS_SLAB_BLOCK.get(), MOSS_BLOCK, recipeOutput);
        standardSlabShape(MUD_SLAB_BLOCK.get(), MUD, recipeOutput);
        standardSlabShape(PACKED_MUD_SLAB_BLOCK.get(), PACKED_MUD, recipeOutput);
        standardSlabShape(MUDDY_ROOTS_SLAB_BLOCK.get(), MUDDY_MANGROVE_ROOTS, recipeOutput);

        standardSlabShape(ICE_SLAB_BLOCK.get(), ICE, recipeOutput);
        standardSlabShape(PACKED_ICE_SLAB_BLOCK.get(), PACKED_ICE, recipeOutput);
        alterFullShape(PACKED_ICE_SLAB_BLOCK, ICE_SLAB_BLOCK.get(), recipeOutput);
        standardSlabShape(BLUE_ICE_SLAB_BLOCK.get(), BLUE_ICE, recipeOutput);
        alterFullShape(BLUE_ICE_SLAB_BLOCK, PACKED_ICE_SLAB_BLOCK.get(), recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DYEING_STATION.get(), 1)
                .pattern("DID")
                .pattern("SIS")
                .pattern("DDD")
                .define('D', Blocks.DEEPSLATE)
                .define('I', Items.IRON_INGOT)
                .define('S', Blocks.STONE)
                .unlockedBy(getHasName((ItemLike) Blocks.DEEPSLATE), has((ItemLike) Blocks.DEEPSLATE))
                .save(recipeOutput.withConditions(DyeingStationRecipeCondition.INSTANCE), DYEING_STATION.getId());


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DIAMOND_AUTO_PRUNER.get(), 1)
                .pattern("  D")
                .pattern("RI ")
                .pattern("I  ")
                .define('D', Items.DIAMOND)
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .save(recipeOutput.withConditions(DiamondPrunerRecipeCondition.INSTANCE), DIAMOND_AUTO_PRUNER.getId());

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(DIAMOND_AUTO_PRUNER.get()),
                Ingredient.of(Items.NETHERITE_INGOT),
                RecipeCategory.TOOLS,
                NETHERITE_AUTO_PRUNER.get()).
                unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                .save(recipeOutput, NETHERITE_AUTO_PRUNER.getId() + "_smithing");


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
                .save(recipeOutput.withConditions(DyeingToolRecipeCondition.INSTANCE), DYEING_TOOL.getId());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PAPER, 3)
                .pattern("   ")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', Items.BAMBOO)
                .unlockedBy(getHasName((ItemLike) Blocks.BAMBOO), has((ItemLike) Blocks.BAMBOO))
                .save(recipeOutput.withConditions(BambooPaperRecipeCondition.INSTANCE), new ResourceLocation("grasses:item/paper"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ICE, 8)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', SNOW_BLOCK)
                .define('W', Items.WATER_BUCKET)
                .unlockedBy(getHasName((ItemLike) SNOW_BLOCK), has((ItemLike) SNOW_BLOCK))
                .save(recipeOutput.withConditions(IceRecipeCondition.INSTANCE), new ResourceLocation("grasses:block/ice"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC , STRING, 3)
                .pattern("   ")
                .pattern(" W ")
                .pattern("   ")
                .define('W', WHITE_WOOL)
                .unlockedBy(getHasName((ItemLike) WHITE_WOOL), has((ItemLike) WHITE_WOOL))
                .save(recipeOutput.withConditions(StringRecipeCondition.INSTANCE), new ResourceLocation("grasses:item/string"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, END_STONE, 8)
                .pattern("SSS")
                .pattern("SES")
                .pattern("SSS")
                .define('S', SANDSTONE)
                .define('E', Items.ENDER_PEARL)
                .unlockedBy(getHasName((ItemLike) Items.ENDER_PEARL), has((ItemLike) Items.ENDER_PEARL))
                .save(recipeOutput.withConditions(EndStoneRecipeCondition.INSTANCE), new ResourceLocation("grasses:block/end_stone"));

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
                .save(recipeOutput.withConditions(EndPortalFrameRecipeCondition.INSTANCE), new ResourceLocation("grasses:block/end_portal_frame_block"));

        List<ItemLike> QUARTZ_SMELTABLES = List.of(DIORITE, GRANITE, ANDESITE);

        oreSmelting(recipeOutput.withConditions(QuartzRecipeCondition.INSTANCE), QUARTZ_SMELTABLES, RecipeCategory.MISC, Items.QUARTZ, 0.25f, 100, "quartz");
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, Grasses.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    private static void growBlocksShape (Block outPutblock, Block inputBlock, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outPutblock, 1)
                .pattern("   ")
                .pattern(" # ")
                .pattern("   ")
                .define('#', (ItemLike) inputBlock)
                .unlockedBy(getHasName((ItemLike) inputBlock), has((ItemLike) inputBlock))
                .save(recipeOutput);
    }

    private static void standardSlabShape (Block outPutblock, Block inputBlock, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outPutblock, 6)
                .pattern("   ")
                .pattern("###")
                .pattern("   ")
                .define('#', (ItemLike) inputBlock)
                .unlockedBy(getHasName((ItemLike) inputBlock), has((ItemLike) inputBlock))
                .save(recipeOutput);
    }
    private static void alterSlabShape(DeferredBlock<Block> outPutblock, Block inputBlock, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outPutblock.get(), 6)
                .pattern("   ")
                .pattern("###")
                .pattern("   ")
                .define('#', (ItemLike) inputBlock)
                .unlockedBy(getHasName((ItemLike) inputBlock), has((ItemLike) inputBlock))
                .save(recipeOutput, (outPutblock.getId()).toString().concat("_alter"));
    }

    private static void alterCoarseDirt(DeferredBlock<Block> outPutblock, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outPutblock.get(), 1)
                .pattern("DG ")
                .pattern("GD ")
                .pattern("   ")
                .define('D', (ItemLike) DIRT_SLAB_BLOCK.get())
                .define('G', (ItemLike) GRAVEL_SLAB_BLOCK.get())
                .unlockedBy(getHasName((ItemLike) GRAVEL_SLAB_BLOCK.get()), has((ItemLike) GRAVEL_SLAB_BLOCK.get()))
                .save(recipeOutput, (outPutblock.getId()).toString().concat("_alter"));
    }

    private static void alterBlockFromBalls(DeferredBlock<Block> outPutblock, Item inputBlock, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outPutblock.get(), 1)
                .pattern("   ")
                .pattern("CC ")
                .pattern("   ")
                .define('C', inputBlock)
                .unlockedBy(getHasName(inputBlock), has(inputBlock))
                .save(recipeOutput, (outPutblock.getId()).toString().concat("_alter"));
    }
    private static void alterFullShape(DeferredBlock<Block> outPutblock, Block inputBlock, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outPutblock.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', (ItemLike) inputBlock)
                .unlockedBy(getHasName((ItemLike) inputBlock), has((ItemLike) inputBlock))
                .save(recipeOutput, (outPutblock.getId()).toString().concat("_alter"));
    }
}
