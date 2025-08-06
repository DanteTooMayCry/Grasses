package net.night.grasses.datagen.loot;

import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;

public record ModSaplingData(Map<Item, Item> saplingForLeaves, float[] normalLeavesSaplingChances, Map<Item, float[]> unNormalLeavesSaplingChances,
                             Map<Item, List<Item>> leavesWithMoreThanOneSapling, boolean isModLoaded) {

}
