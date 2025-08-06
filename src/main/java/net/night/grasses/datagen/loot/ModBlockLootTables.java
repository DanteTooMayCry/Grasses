package net.night.grasses.datagen.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.block.plants.bop.TintedHugeLilyPadBOP;
import net.night.grasses.enums.GrassesQuarterProperty;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static biomesoplenty.api.block.BOPBlocks.WATERLILY;
import static net.minecraft.world.item.Items.*;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;

public class ModBlockLootTables extends BlockLootSubProvider {
    public static final List<Item> saplingList = new ArrayList<>();
    public static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    public static final float[] MAPLE_LEAVES_SAPLING_CHANCES = new float[]{0.05F/3, 0.0625F/3, 0.083333336F/3, 0.1F/3};
    private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};
    private static final float[] JUNGLE_LEAVES_SAPLING_CHANCES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
    private static final float[] NORMAL_APPLES_CHANCES = new float[]{0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F};
    public static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    public static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        // Grasses Blocks With and Without SilkTouch drop:
        for (RegistryObject<Block> grassesBlock : grassRegistryBlocksList) {
            this.add(grassesBlock.get(),
                    block -> createSelfDrops(grassesBlock.get(), Blocks.DIRT));
        }
        // Grasses Slabs With and Without SilkTouch drop:
        for(RegistryObject<Block> grassesSlabs : grassRegistrySlabBlocksList){
            this.add(grassesSlabs.get(),
                    block -> createSelfDropsForSlab(grassesSlabs.get(), DIRT_SLAB_BLOCK.get()));
        }

        // Dirt Like:
        this.add(DIRT_SLAB_BLOCK.get(), block -> createSelfDropsForSlab(DIRT_SLAB_BLOCK.get(), DIRT_SLAB_BLOCK.get()));
        this.add(DIRT_PATH_SLAB_BLOCK.get(), block -> dropOtherForSlab(DIRT_PATH_SLAB_BLOCK.get(), DIRT_SLAB_BLOCK.get()));
        this.add(FARMLAND_SLAB_BLOCK.get(), block -> dropOtherForSlab(FARMLAND_SLAB_BLOCK.get(), DIRT_SLAB_BLOCK.get()));
        this.add(COARSE_DIRT_SLAB_BLOCK.get(), block -> createSlabItemTable(COARSE_DIRT_SLAB_BLOCK.get()));
        this.add(ROOTED_DIRT_SLAB_BLOCK.get(), block -> createSlabItemTable(ROOTED_DIRT_SLAB_BLOCK.get()));
        this.add(GROW_MYCELIUM_BLOCK.get(), block -> createSelfDrops(GROW_MYCELIUM_BLOCK.get(), Blocks.DIRT));
        this.add(MYCELIUM_SLAB_BLOCK.get(), block -> createSelfDropsForSlab(MYCELIUM_SLAB_BLOCK.get(), DIRT_SLAB_BLOCK.get()));
        this.add(GROW_PODZOL_BLOCK.get(), block -> createSelfDrops(GROW_PODZOL_BLOCK.get(), Blocks.DIRT));
        this.add(PODZOL_SLAB_BLOCK.get(), block -> createSelfDropsForSlab(PODZOL_SLAB_BLOCK.get(), DIRT_SLAB_BLOCK.get()));

        // Netherrack Like:
        this.add(GROW_CRIMSON_NYLIUM_BLOCK.get(), block -> createSelfDrops(GROW_CRIMSON_NYLIUM_BLOCK.get(), Blocks.NETHERRACK));
        this.add(GROW_WARPED_NYLIUM_BLOCK.get(), block -> createSelfDrops(GROW_WARPED_NYLIUM_BLOCK.get(), Blocks.NETHERRACK));
        this.add(CRIMSON_NYLIUM_SLAB_BLOCK.get(), block -> createSelfDropsForSlab(CRIMSON_NYLIUM_SLAB_BLOCK.get(), NETHERRACK_SLAB_BLOCK.get()));
        this.add(WARPED_NYLIUM_SLAB_BLOCK.get(), block -> createSelfDropsForSlab(WARPED_NYLIUM_SLAB_BLOCK.get(), NETHERRACK_SLAB_BLOCK.get()));
        this.add(NETHERRACK_SLAB_BLOCK.get(), block -> createSlabItemTable(NETHERRACK_SLAB_BLOCK.get()));

        this.add(GRAVEL_SLAB_BLOCK.get(), block -> createSelfDropOrItemForSlabGravel(GRAVEL_SLAB_BLOCK.get()));
        this.add(SAND_SLAB_BLOCK.get(), block -> createSlabItemTable(SAND_SLAB_BLOCK.get()));
        this.add(RED_SAND_SLAB_BLOCK.get(), block -> createSlabItemTable(RED_SAND_SLAB_BLOCK.get()));
        this.add(CLAY_SLAB_BLOCK.get(), block -> createSelfDropOrItemForSlabClay(CLAY_SLAB_BLOCK.get(), CLAY_BALL, ConstantValue.exactly(2.0F), ConstantValue.exactly(4.0F)));

        this.add(SOUL_SAND_SLAB_BLOCK.get(), block -> createSlabItemTable(SOUL_SAND_SLAB_BLOCK.get()));
        this.add(SOUL_SOIL_SLAB_BLOCK.get(), block -> createSlabItemTable(SOUL_SOIL_SLAB_BLOCK.get()));
        this.add(MAGMA_SLAB_BLOCK.get(), block -> createSlabItemTable(MAGMA_SLAB_BLOCK.get()));

        this.add(MOSS_SLAB_BLOCK.get(), block -> createSlabItemTable(MOSS_SLAB_BLOCK.get()));
        this.add(MUD_SLAB_BLOCK.get(), block -> createSlabItemTable(MUD_SLAB_BLOCK.get()));
        this.add(PACKED_MUD_SLAB_BLOCK.get(), block -> createSlabItemTable(PACKED_MUD_SLAB_BLOCK.get()));
        this.add(MUDDY_ROOTS_SLAB_BLOCK.get(), block -> createSlabItemTable(MUDDY_ROOTS_SLAB_BLOCK.get()));

        this.add(ICE_SLAB_BLOCK.get(), block -> createSilkTouchOnlyForSlab(ICE_SLAB_BLOCK.get()));
        this.add(PACKED_ICE_SLAB_BLOCK.get(), block -> createSilkTouchOnlyForSlab(PACKED_ICE_SLAB_BLOCK.get()));
        this.add(BLUE_ICE_SLAB_BLOCK.get(), block -> createSilkTouchOnlyForSlab(BLUE_ICE_SLAB_BLOCK.get()));

        this.dropSelf(DYEING_STATION.get());

        // Leaves With and Without SilkTouch drop
        for(RegistryObject<Block> leavesBlock : grassesLeavesRegistryBlocksList) {
                int index = grassesLeavesRegistryBlocksList.indexOf(leavesBlock);

                if (leavesBlock.get().equals(DARK_OAK_LEAVES_BLOCK.get())) {
                    this.add(DARK_OAK_LEAVES_BLOCK.get(),
                            block -> createTintedLeavesWithAppleDrops(DARK_OAK_LEAVES_BLOCK.get(), Items.DARK_OAK_SAPLING, new Property<?>[]{ALTER}, true, NORMAL_LEAVES_SAPLING_CHANCES));
                } else if (leavesBlock.get().equals(JUNGLE_LEAVES_BLOCK.get())) {
                    this.add(JUNGLE_LEAVES_BLOCK.get(),
                            block -> createTintedLeavesDrops(JUNGLE_LEAVES_BLOCK.get(), Items.JUNGLE_SAPLING, new Property<?>[]{DOUBLE_ALTER}, JUNGLE_LEAVES_SAPLING_CHANCES));
                } else if (leavesBlock.get().equals(OAK_LEAVES_BLOCK.get())) {
                    this.add(OAK_LEAVES_BLOCK.get(),
                            block -> createTintedLeavesWithAppleDrops(OAK_LEAVES_BLOCK.get(), Items.OAK_SAPLING, new Property<?>[]{ALTER}, true, NORMAL_LEAVES_SAPLING_CHANCES));
                } else
                    this.add(leavesBlock.get(),
                            block -> createTintedLeavesDrops(leavesBlock.get(), saplingList.get(index), new Property<?>[]{ALTER}, NORMAL_LEAVES_SAPLING_CHANCES));
        }
        // Grass With and Without SilkTouch drop
        this.add(GRASS_TINTED.get(),
                block -> createGrassDrops(GRASS_TINTED.get()));
        this.add(FERN_TINTED.get(),
                block -> createGrassDrops(FERN_TINTED.get()));
        // Tall Grass With and Without SilkTouch drop
        this.add(GRASS_TALL_TINTED.get(),
                block -> createDoublePlantWithSeedDrops(GRASS_TALL_TINTED.get(), GRASS_TINTED.get()));
        this.add(FERN_TALL_TINTED.get(),
                block -> createDoublePlantWithSeedDrops(FERN_TALL_TINTED.get(), FERN_TINTED.get()));

        // Seagrass With and Without SilkTouch drop
        this.add(SEAGRASS_TINTED.get(),
                block -> createShearsOnlyDrop(SEAGRASS_TINTED.get()));
        this.add(SEAGRASS_TALL_TINTED.get(),
                block -> createDoublePlantShearsDrop(SEAGRASS_TINTED.get()));

        // Vine With and Without SilkTouch drop
        this.add(VINE_TINTED.get(),
                block -> createShearsOnlyDrop(VINE_TINTED.get()));

        this.dropSelf(LILY_TINTED.get());
        this.dropSelf(SUGAR_CANE_TINTED.get());
        this.dropSelf(BAMBOO_TINTED.get());
        this.dropOther(BAMBOO_SAPLING_TINTED.get(), BAMBOO_TINTED.get());
        this.add(SMALL_DRIP_LEAF_TINTED.get(),
                block -> createShearsOnlyDrop(SMALL_DRIP_LEAF_TINTED.get()));
        this.dropSelf(BIG_DRIP_LEAF_TINTED.get());
        this.dropOther(BIG_DRIP_LEAF_STEM_TINTED.get(), BIG_DRIP_LEAF_TINTED.get());
        this.dropSelf(KELP_TINTED.get());
        this.dropOther(KELP_PLANT_TINTED.get(), KELP_TINTED.get());
        this.dropSelf(CACTUS_TINTED.get());

        this.add(GRASS_POTTED_TINTED.get(), block -> createPotFlowerItemTable(GRASS_TINTED.get()));
        this.add(FERN_POTTED_TINTED.get(), block -> createPotFlowerItemTable(FERN_TINTED.get()));
        this.add(SEAGRASS_POTTED_TINTED.get(), block -> createPotFlowerItemTable(SEAGRASS_TINTED.get()));
        this.add(BAMBOO_POTTED_TINTED.get(), block -> createPotFlowerItemTable( BAMBOO_TINTED.get()));
        this.add(SUGAR_CANE_POTTED_TINTED.get(), block -> createPotFlowerDropWithCopyBlockState(SUGAR_CANE_POTTED_TINTED.get(), SUGAR_CANE_TINTED.get(), new Property<?>[] { BIOMES_COLOR_SOURCE }));

        this.add(VINE_POTTED_TINTED.get(), block -> createPotFlowerItemTable(VINE_TINTED.get()));
        this.add(BIG_DRIP_LEAF_POTTED_TINTED.get(), block -> createPotFlowerItemTable(BIG_DRIP_LEAF_TINTED.get()));
        this.add(SMALL_DRIP_LEAF_POTTED_TINTED.get(), block -> createPotFlowerItemTable(SMALL_DRIP_LEAF_TINTED.get()));
        this.add(KELP_POTTED_TINTED.get(), block -> createPotFlowerItemTable(KELP_TINTED.get()));
        this.add(CACTUS_POTTED_TINTED.get(), block -> createPotFlowerItemTable(CACTUS_TINTED.get()));

        this.add(GRASS_POTTED.get(), createPotFlowerItemTable(GRASS));
        this.add(SEAGRASS_POTTED.get(), createPotFlowerItemTable(SEAGRASS));
        this.add(SUGAR_CANE_POTTED.get(), createPotFlowerItemTable(SUGAR_CANE));
        this.add(VINE_POTTED.get(), createPotFlowerItemTable(VINE));
        this.add(BIG_DRIP_LEAF_POTTED.get(), createPotFlowerItemTable(BIG_DRIPLEAF));
        this.add(SMALL_DRIP_LEAF_POTTED.get(), createPotFlowerItemTable(SMALL_DRIPLEAF));
        this.add(KELP_POTTED.get(), createPotFlowerItemTable(KELP));

        this.add(ACACIA_LEAVES_POTTED.get(), createPotFlowerItemTable(ACACIA_LEAVES));
        this.add(AZALEA_LEAVES_POTTED.get(), createPotFlowerItemTable(AZALEA_LEAVES));
        this.add(BIRCH_LEAVES_POTTED.get(), createPotFlowerItemTable(BIRCH_LEAVES));
        this.add(CHERRY_LEAVES_POTTED.get(), createPotFlowerItemTable(CHERRY_LEAVES));
        this.add(DARK_OAK_LEAVES_POTTED.get(), createPotFlowerItemTable(DARK_OAK_LEAVES));
        this.add(FLOWERING_AZALEA_LEAVES_POTTED.get(), createPotFlowerItemTable(FLOWERING_AZALEA_LEAVES));
        this.add(JUNGLE_LEAVES_POTTED.get(), createPotFlowerItemTable(JUNGLE_LEAVES));
        this.add(MANGROVE_LEAVES_POTTED.get(), createPotFlowerItemTable(MANGROVE_LEAVES));
        this.add(OAK_LEAVES_POTTED.get(), createPotFlowerItemTable(OAK_LEAVES));
        this.add(SPRUCE_LEAVES_POTTED.get(), createPotFlowerItemTable(SPRUCE_LEAVES));

        this.add(ACACIA_LEAVES_POTTED_TINTED.get(), block -> createPotFlowerItemTable(ACACIA_LEAVES_BLOCK.get()));
        this.add(AZALEA_LEAVES_POTTED_TINTED.get(), block -> createPotFlowerItemTable(AZALEA_LEAVES_BLOCK.get()));
        this.add(BIRCH_LEAVES_POTTED_TINTED.get(), block -> createPotFlowerItemTable(BIRCH_LEAVES_BLOCK.get()));
        this.add(CHERRY_LEAVES_POTTED_TINTED.get(), block -> createPotFlowerItemTable(CHERRY_LEAVES_BLOCK.get()));
        this.add(DARK_OAK_LEAVES_POTTED_TINTED.get(), block -> createPotFlowerItemTable(DARK_OAK_LEAVES_BLOCK.get()));
        this.add(FLOWERING_AZALEA_LEAVES_POTTED_TINTED.get(), block -> createPotFlowerItemTable(FLOWERING_AZALEA_LEAVES_BLOCK.get()));
        this.add(JUNGLE_LEAVES_POTTED_TINTED.get(), block -> createPotFlowerItemTable(JUNGLE_LEAVES_BLOCK.get()));
        this.add(MANGROVE_LEAVES_POTTED_TINTED.get(), block -> createPotFlowerItemTable(MANGROVE_LEAVES_BLOCK.get()));
        this.add(OAK_LEAVES_POTTED_TINTED.get(), block -> createPotFlowerItemTable(OAK_LEAVES_BLOCK.get()));
        this.add(SPRUCE_LEAVES_POTTED_TINTED.get(), block -> createPotFlowerItemTable(SPRUCE_LEAVES_BLOCK.get()));

        this.add(GRASS_IN_BARS.get(),
                block -> createBarsTable(Blocks.GRASS));

        this.add(FERN_IN_BARS.get(),
                block -> createBarsTable(Blocks.FERN));

        this.add(VINE_IN_BARS.get(),
                block -> createBarsTable(Blocks.VINE));

        this.add(TINTED_GRASS_IN_BARS.get(),
                block -> createBarsTable(GRASS_TINTED.get()));

        this.add(TINTED_FERN_IN_BARS.get(),
                block -> createBarsTable(FERN_TINTED.get()));

        this.add(TINTED_VINE_IN_BARS.get(),
                block -> createBarsTable(VINE_TINTED.get()));

        if (isBOPLoaded) {

            for (RegistryObject<Block> leavesBlock : tintedBOPleavesRegistryBlocksList) {

                if (leavesBlock.get().equals(MAPLE_LEAVES_BLOCK.get())) {
                    this.add(leavesBlock.get(),
                            block -> createTintedLeavesDropsWithOutSaplings(leavesBlock.get(), new Property<?>[]{ALTER}, MAPLE_LEAVES_SAPLING_CHANCES));
                } else if (leavesBlock.get().equals(FLOWERING_OAK_LEAVES_BLOCK.get())) {
                    this.add(FLOWERING_OAK_LEAVES_BLOCK.get(),
                            block -> createTintedLeavesWithAppleDrops(FLOWERING_OAK_LEAVES_BLOCK.get(), AIR.asItem(), new Property<?>[]{ALTER}, false, NORMAL_LEAVES_SAPLING_CHANCES));
                } else
                    this.add(leavesBlock.get(),
                            block -> createTintedLeavesDropsWithOutSaplings(leavesBlock.get(), new Property<?>[]{ALTER}, NORMAL_LEAVES_SAPLING_CHANCES));
            }

            this.add(WILLOW_VINE_TINTED.get(),
                    block -> createShearsOnlyDrop(WILLOW_VINE_TINTED.get()));
            this.add(BUSH_TINTED.get(),
                    block -> createShearsOnlyDrop(BUSH_TINTED.get()));
            this.add(SPROUT_TINTED.get(),
                    block -> createGrassDrops(SPROUT_TINTED.get()));
            this.add(CLOVER_TINTED.get(),
                    block -> createPetalsDrops(CLOVER_TINTED.get()));
            this.add(HUGE_CLOVER_TINTED.get(),
                    block -> createShearsOnlyDrop(HUGE_CLOVER_TINTED.get()));
            this.add(HIGH_GRASS_TINTED.get(),
                    block -> createShearsOnlyDrop(HIGH_GRASS_TINTED.get()));
            this.add(HIGH_GRASS_PLANT_TINTED.get(),
                    block -> createShearsOnlyDrop(HIGH_GRASS_TINTED.get()));
            this.dropSelf(TINY_CACTUS_TINTED.get());
            this.dropSelf(WATERLILY_TINTED.get());
            this.add(HUGE_LILY_PAD_TINTED.get(),
                    block -> createSinglePropConditionTable(HUGE_LILY_PAD_TINTED.get(), TintedHugeLilyPadBOP.GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST, WATERLILY_TINTED.get(), WATERLILY, GrassesQuarterProperty.SOUTH_EAST));
            this.add(WATER_GRASS_TINTED.get(),
                    block -> createShearsOnlyDrop(WATER_GRASS_TINTED.get()));
            this.dropSelf(WATERLILY_TINTED.get());
            this.add(LEAF_PILE_TINTED.get(),
                    block -> createShearsOnlyDrop(LEAF_PILE_TINTED.get()));
        }
    }

    /////////////////////////////////////////////////////////////////////////////

    protected LootTable.Builder createSelfDrops(Block selfBlock, Block defaultBlock) {
        return createSilkTouchDispatchTable(selfBlock,
                this.applyExplosionDecay(selfBlock, LootItem.lootTableItem(defaultBlock)));
    }

    protected LootTable.Builder createSelfDropsForSlab(Block selfBlock, Block defaultBlock) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(selfBlock)
                                .when(HAS_SILK_TOUCH)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(selfBlock)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))
                                .otherwise(this.applyExplosionDecay(selfBlock, LootItem.lootTableItem(defaultBlock))
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(selfBlock)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))
                                )
                        )
                );
    }

    protected LootTable.Builder dropOtherForSlab(Block selfBlock, ItemLike item) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(this.applyExplosionDecay(selfBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(selfBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    protected LootTable.Builder createSelfDropOrItemForSlabGravel(Block selfBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(selfBlock)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(selfBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))
                        .when(HAS_SILK_TOUCH)

                        .otherwise(this.applyExplosionCondition(selfBlock, LootItem.lootTableItem(Items.FLINT)
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(selfBlock)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F))

                                        .otherwise(this.applyExplosionCondition(selfBlock, LootItem.lootTableItem(Items.FLINT)
                                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.071428575F, 0.125F, 0.5F))))
                                        .otherwise(LootItem.lootTableItem(selfBlock)
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(selfBlock)
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))
                                )
                        )
                )
        );
    }

    protected LootTable.Builder createSelfDropOrItemForSlabClay(Block selfBlock, ItemLike itemLike, NumberProvider count, NumberProvider alterCount) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(selfBlock)
                                .when(HAS_SILK_TOUCH)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(selfBlock)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))

                                .otherwise(this.applyExplosionDecay(selfBlock, LootItem.lootTableItem(itemLike).apply(SetItemCountFunction.setCount(count)))
                                        .apply(SetItemCountFunction.setCount(alterCount)
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(selfBlock)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))))


                );
    }

    protected LootTable.Builder createSilkTouchOnlyForSlab(Block selfBlock) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .when(HAS_SILK_TOUCH).setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(selfBlock))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(selfBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))));
    }

    protected LootTable.Builder createTintedLeavesDrops(Block selfBlock, Item sapling, Property<?>[] properties, float... chances) {
        CopyBlockState.Builder blockStateCopyBuilder = CopyBlockState.copyState(selfBlock);

        for(Property<?> property : properties) {
            blockStateCopyBuilder.copy(property);
        }

        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(selfBlock)
                                .when(HAS_SHEARS_OR_SILK_TOUCH)
                                .apply(blockStateCopyBuilder)))
                .withPool(LootPool.lootPool().name(sapling.toString()).setRolls(ConstantValue.exactly(1.0F))
                        .add(this.applyExplosionCondition(selfBlock, LootItem.lootTableItem(sapling)))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances))
                        .when(HAS_NO_SHEARS_OR_SILK_TOUCH))
                .withPool(LootPool.lootPool().name("sticks").setRolls(ConstantValue.exactly(1.0F))
                        .add(this.applyExplosionDecay(selfBlock, LootItem.lootTableItem(Items.STICK).
                                        apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_LEAVES_STICK_CHANCES))
                                .when(HAS_NO_SHEARS_OR_SILK_TOUCH)));

    }

    protected LootTable.Builder createTintedLeavesDropsWithOutSaplings(Block selfBlock, Property<?>[] properties, float... chances) {
        CopyBlockState.Builder blockStateCopyBuilder = CopyBlockState.copyState(selfBlock);

        for(Property<?> property : properties) {
            blockStateCopyBuilder.copy(property);
        }

        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(selfBlock)
                                .when(HAS_SHEARS_OR_SILK_TOUCH)
                                .apply(blockStateCopyBuilder)))
                .withPool(LootPool.lootPool().name("sticks").setRolls(ConstantValue.exactly(1.0F))
                        .add(this.applyExplosionDecay(selfBlock, LootItem.lootTableItem(Items.STICK).
                                        apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_LEAVES_STICK_CHANCES))
                                .when(HAS_NO_SHEARS_OR_SILK_TOUCH)));

    }

    protected LootTable.Builder createTintedLeavesWithAppleDrops(Block selfBlock, Item sapling, Property<?>[] properties, boolean withSaplings, float... chances) {

        LootTable.Builder baseBuilder = createTintedLeavesDrops(selfBlock, sapling, properties, chances);

        if (!withSaplings)
            baseBuilder = createTintedLeavesDropsWithOutSaplings(selfBlock, properties, chances);

        return baseBuilder
                .withPool(LootPool.lootPool().name("apple").setRolls(ConstantValue.exactly(1.0F))
                        .when(HAS_NO_SHEARS_OR_SILK_TOUCH)
                        .add(this.applyExplosionCondition(selfBlock, LootItem.lootTableItem(Items.APPLE))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_APPLES_CHANCES)))
                );
    }

    protected LootTable.Builder createPotFlowerDropWithCopyBlockState(Block selfBlock, ItemLike itemLike, Property<?>[] properties) {
        CopyBlockState.Builder blockStateCopyBuilder = CopyBlockState.copyState(selfBlock);

        for(Property<?> property : properties) {
            blockStateCopyBuilder.copy(property);
        }


        return LootTable.lootTable()
                .withPool(this.applyExplosionCondition(Blocks.FLOWER_POT, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Blocks.FLOWER_POT))))
                .withPool(this.applyExplosionCondition(itemLike, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(itemLike)
                                .apply(blockStateCopyBuilder))));
    }

    protected <T extends Comparable<T> & StringRepresentable> LootTable.Builder createSinglePropConditionTable(Block selfBlock, Property<T> pProperty, T pValue, Block grassesWaterlily, Block bopWaterLily, T pValueLily) {

        return LootTable.lootTable()
                .withPool(this.applyExplosionCondition(selfBlock, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(selfBlock)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(selfBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(pProperty, pValue)))
                                )
                        .add(LootItem.lootTableItem(grassesWaterlily)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(selfBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VARIANT_LILY, 1).hasProperty(pProperty, pValueLily))))
                        .add(LootItem.lootTableItem(bopWaterLily)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(selfBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VARIANT_LILY, 2).hasProperty(pProperty, pValueLily)))

                        )));
    }

    /*protected LootTable.Builder createDiffConditionTable(Block block) {

        return LootTable.lootTable()
                .withPool(this.applyExplosionCondition(Blocks.IRON_BARS, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Blocks.IRON_BARS))
                        .add(LootItem.lootTableItem(block)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.IRON_BARS)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(X, Y))))
                        .add(LootItem.lootTableItem(block)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.IRON_BARS)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(X, Y))))

                ));
    }*/

    protected LootTable.Builder createBarsTable(Block otherBlock) {

        return LootTable.lootTable()
                .withPool(this.applyExplosionCondition(Blocks.IRON_BARS, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Blocks.IRON_BARS))
                        .add(LootItem.lootTableItem(otherBlock))));
    }


    /////////////////////////////////////////////////////////////////////////////

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    static {
        saplingList.add(Items.ACACIA_SAPLING);
        saplingList.add(Items.AZALEA);
        saplingList.add(Items.BIRCH_SAPLING);
        saplingList.add(Items.CHERRY_SAPLING);
        saplingList.add(Items.DARK_OAK_SAPLING);
        saplingList.add(Items.FLOWERING_AZALEA);
        saplingList.add(Items.JUNGLE_SAPLING);
        saplingList.add(Items.MANGROVE_PROPAGULE);
        saplingList.add(Items.OAK_SAPLING);
        saplingList.add(Items.SPRUCE_SAPLING);
    }
}
