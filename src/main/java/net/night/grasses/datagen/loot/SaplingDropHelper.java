package net.night.grasses.datagen.loot;

import biomesoplenty.api.item.BOPItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static biomesoplenty.api.block.BOPBlocks.*;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.init.BlocksRegister.BIRCH_LEAVES_BLOCK;
import static net.night.grasses.init.BlocksRegister.CHERRY_LEAVES_BLOCK;
import static net.night.grasses.init.BlocksRegisterBoP.*;

public class SaplingDropHelper {

    //BOP
    public static final Map<Item, Item> matchingBOPSaplingLeaves = new HashMap<>();
    public static final float[] NORMAL_LEAVES_SAPLING_CHANCES = ModBlockLootTables.NORMAL_LEAVES_SAPLING_CHANCES;
    public static final float[] MAPLE_LEAVES_SAPLING_CHANCES = new float[]{0.05F/3, 0.0625F/3, 0.083333336F/3, 0.1F/3};
    public static final Map<Item, float[]> unNormalBOPLeavesChances = new HashMap<>();
    public static final Map<Item, List<Item>> leavesBOPWithMoreThanOneSapling = new HashMap<>();

    public static final List<ModSaplingData> knownMods = new ArrayList<>();

    static {
        unNormalBOPLeavesChances.put(MAPLE_LEAVES_BLOCK.get().asItem(), MAPLE_LEAVES_SAPLING_CHANCES);
        leavesBOPWithMoreThanOneSapling.put(MAPLE_LEAVES_BLOCK.get().asItem(), List.of(BOPItems.RED_MAPLE_SAPLING, BOPItems.ORANGE_MAPLE_SAPLING, BOPItems.YELLOW_MAPLE_SAPLING));
    }

    static {
        knownMods.add(new ModSaplingData(matchingBOPSaplingLeaves, NORMAL_LEAVES_SAPLING_CHANCES, unNormalBOPLeavesChances, leavesBOPWithMoreThanOneSapling, isBOPLoaded));
        //knownMods.add(new ModSaplingData(matchingOtherModSaplingLeaves, OTHER_MOD_SAPLING_CHANCES, otherCustomChances, isOtherModLoaded));
    }


    static {
        if (isBOPLoaded) {
            matchingBOPSaplingLeaves.put(FIR_LEAVES_BLOCK.get().asItem(), FIR_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(PINE_LEAVES_BLOCK.get().asItem(), PINE_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(MAPLE_LEAVES_BLOCK.get().asItem(), RED_MAPLE_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(REDWOOD_LEAVES_BLOCK.get().asItem(), REDWOOD_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(MAHOGANY_LEAVES_BLOCK.get().asItem(), MAHOGANY_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(JACARANDA_LEAVES_BLOCK.get().asItem(), JACARANDA_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(PALM_LEAVES_BLOCK.get().asItem(), PALM_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(WILLOW_LEAVES_BLOCK.get().asItem(), WILLOW_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(DEAD_LEAVES_BLOCK.get().asItem(), DEAD_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(MAGIC_LEAVES_BLOCK.get().asItem(), MAGIC_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(UMBRAN_LEAVES_BLOCK.get().asItem(), UMBRAN_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(EMPYREAL_LEAVES_BLOCK.get().asItem(), EMPYREAL_LEAVES.asItem());
            matchingBOPSaplingLeaves.put(BIRCH_LEAVES_BLOCK.get().asItem(), EMPYREAL_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(CHERRY_LEAVES_BLOCK.get().asItem(), SNOWBLOSSOM_LEAVES.asItem());
            matchingBOPSaplingLeaves.put(FLOWERING_OAK_LEAVES_BLOCK.get().asItem(), FLOWERING_OAK_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(ORIGIN_LEAVES_BLOCK.get().asItem(), ORIGIN_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(CYPRESS_LEAVES_BLOCK.get().asItem(), CYPRESS_SAPLING.asItem());
            matchingBOPSaplingLeaves.put(HELLBARK_LEAVES_BLOCK.get().asItem(), HELLBARK_SAPLING.asItem());
        }
    }
}
