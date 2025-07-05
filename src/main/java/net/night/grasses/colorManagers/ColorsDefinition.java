package net.night.grasses.colorManagers;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.night.grasses.block.leaves.superclasses.ParentTintedLeavesBlock;
import net.night.grasses.block.plants.TintedVine;

import java.util.concurrent.atomic.AtomicReference;

import static net.night.grasses.data.DataLib.*;
import static net.night.grasses.init.BlocksRegister.*;

public class ColorsDefinition {


    public static int takeColor (ColorType colorType, BlockState blockState, ItemStack itemStack) {

        AtomicReference<String> color = new AtomicReference<>("");

        if (itemStack != null) {
            switch (colorType) { // case state
                case BADLANDS:      color.set("#9e814d"); break;
                case BIRCH:         color.set("#6ba941"); break;
                case CHERRY:        color.set("#b6db61"); break;
                case DARK:          color.set("#59ae30"); break;
                case DESERT:        color.set("#aea42a"); break;
                case DRIPSTONEBE:   color.set("#8bd58a"); break;
                case FOREST:        color.set("#79c05a"); break; //
                case JUNGLE:        color.set("#30bb0b"); break;
                case LUSHBE:        color.set("#b9b75b"); break;
                case MANGROVE:      color.set("#8db127"); break;
                case MEADOW:        color.set("#63a948"); break;
                case MUSHROOM:      color.set("#2bbb0f"); break;
                case OCEAN:         color.set("#71a74d"); break;
                case PLAINS:        color.set("#77ab2f"); break;
                case PINETAIGA:     color.set("#68a55f"); break;
                case SNOWYBEACH:    color.set("#64a278"); break;
                case SNOWYPLAINS:   color.set("#60a17b"); break;
                case SPARSEJUNGLE:  color.set("#3eb80f"); break;
                case STONYPEAKS:    color.set("#82ac1e"); break;
                case SWAMP:         color.set("#6a7039"); break;
                case SWAMPCOLD:     color.set("#4C763C"); break;
                case TAIGA:         color.set("#68a464"); break;
                case WINDSWEPT:     color.set("#6da36b"); break;
            }
        }
        if (blockState != null && (blockState.getBlock() instanceof ParentTintedLeavesBlock || blockState.getBlock() instanceof TintedVine || blockState.is(VINE_POTTED_TINTED.get()) || tintedLeavesPottedBlockList.contains(blockState.getBlock()))) {

            switch (colorType) {
                case BADLANDS:      color.set("#9e814d"); break;
                case BIRCH:         color.set("#6ba941"); break;
                case CHERRY:        color.set("#b6db61"); break;
                case DARK:          color.set("#59ae30"); break;
                case DESERT:        color.set("#aea42a"); break;
                case DRIPSTONEBE:   color.set("#8bd58a"); break;
                case FOREST:        color.set("#79c05a"); break;
                case JUNGLE:        color.set("#30bb0b"); break;
                case LUSHBE:        color.set("#b9b75b"); break;
                case MANGROVE:      color.set("#8db127"); break;
                case MEADOW:        color.set("#63a948"); break;
                case MUSHROOM:      color.set("#2bbb0f"); break;
                case OCEAN:         color.set("#71a74d"); break;
                case PLAINS:        color.set("#77ab2f"); break;
                case PINETAIGA:     color.set("#68a55f"); break;
                case SNOWYBEACH:    color.set("#64a278"); break;
                case SNOWYPLAINS:   color.set("#60a17b"); break;
                case SPARSEJUNGLE:  color.set("#3eb80f"); break;
                case STONYPEAKS:    color.set("#82ac1e"); break;
                case SWAMP:         color.set("#6a7039"); break;
                case SWAMPCOLD:     color.set("#4c763c"); break;
                case TAIGA:         color.set("#68a464"); break;
                case WINDSWEPT:     color.set("#6da36b"); break;
            }
        }
        else if (blockState != null && sugarCaneTintedPlantsList.contains(blockState.getBlock())) {
            switch (colorType) {
                case BADLANDS:      color.set("#90814d"); break; //
                case BIRCH:         color.set("#88bb67"); break; //
                case CHERRY:        color.set("#b6db61"); break; //
                case DARK:          color.set("#507a32"); break; //
                case DESERT:        color.set("#bfb755"); break; //
                case DRIPSTONEBE:   color.set("#8bd58a"); break;
                case FOREST:        color.set("#79c05a"); break; //
                case JUNGLE:        color.set("#59c93c"); break; //
                case LUSHBE:        color.set("#b9b75b"); break;
                case MANGROVE:      color.set("#8db127"); break; //*
                case MEADOW:        color.set("#63a948"); break; //*
                case MUSHROOM:      color.set("#55c93f"); break; //
                case OCEAN:         color.set("#8eb971"); break; //
                case PLAINS:        color.set("#91bd59"); break; //
                case PINETAIGA:     color.set("#86b87f"); break; //
                case SNOWYBEACH:    color.set("#83b593"); break; //
                case SNOWYPLAINS:   color.set("#80b497"); break; //
                case SPARSEJUNGLE:  color.set("#64c73f"); break; //
                case STONYPEAKS:    color.set("#82ac1e"); break; //*
                case SWAMP:         color.set("#6a7039"); break; //
                case SWAMPCOLD:     color.set("#4c763c"); break; //
                case TAIGA:         color.set("#86b783"); break; //
                case WINDSWEPT:     color.set("#8ab689"); break; //
            }
        }
        else if (blockState != null && standardTintedPlantsList.contains(blockState.getBlock())) {
            switch (colorType) {
                case BADLANDS:      color.set("#90814d"); break;
                case BIRCH:         color.set("#88bb67"); break;
                case CHERRY:        color.set("#b6db61"); break;
                case DARK:          color.set("#507a32"); break;
                case DESERT:        color.set("#bfb755"); break;
                case DRIPSTONEBE:   color.set("#8bd58a"); break;
                case FOREST:        color.set("#79c05a"); break;
                case JUNGLE:        color.set("#59c93c"); break;
                case LUSHBE:        color.set("#b9b75b"); break;
                case MANGROVE:      color.set("#8db127"); break;
                case MEADOW:        color.set("#83bb6d"); break;
                case MUSHROOM:      color.set("#55c93f"); break;
                case OCEAN:         color.set("#8eb971"); break;
                case PLAINS:        color.set("#91bd59"); break;
                case PINETAIGA:     color.set("#86b87f"); break;
                case SNOWYBEACH:    color.set("#83b593"); break;
                case SNOWYPLAINS:   color.set("#80b497"); break;
                case SPARSEJUNGLE:  color.set("#64c73f"); break;
                case STONYPEAKS:    color.set("#9abe4b"); break;
                case SWAMP:         color.set("#6A7039"); break;
                case SWAMPCOLD:     color.set("#4C763C"); break;
                case TAIGA:         color.set("#86b783"); break;
                case WINDSWEPT:     color.set("#8ab689"); break;
            }
        }

        switch (colorType) {
            case WHITE:         color.set("#F9FFFE"); break;
            case RED:           color.set("#B02E26"); break;
            case ORANGE:        color.set("#F9801D"); break;
            case PINK:          color.set("#F38BAA"); break;
            case YELLOW:        color.set("#FED83D"); break;
            case LIME:          color.set("#80C71F"); break;
            case GREEN:         color.set("#5E7C16"); break;
            case LIGHTBLUE:     color.set("#3AB3DA"); break;
            case CYAN:          color.set("#169C9C"); break;
            case BLUE:          color.set("#3C44AA"); break;
            case MAGENTA:       color.set("#C74EBD"); break;
            case PURPLE:        color.set("#8932B8"); break;
            case BROWN:         color.set("#835432"); break;
            case GRAY:          color.set("#474F52"); break;
            case LIGHTGRAY:     color.set("#9D9D97"); break;
            case BLACK:         color.set("#1D1D21"); break;
            case QUARTZ:        color.set("#E3D4D1"); break;
            case COPPER:        color.set("#B4684D"); break;
            case IRON:          color.set("#CECACA"); break;
            case GOLD:          color.set("#DEB12D"); break;
            case DIAMOND:       color.set("#2CBAA8"); break;
            case EMERALD:       color.set("#47A036"); break;
            case NETHERITE:     color.set("#443A3B"); break;
            case REDSTONE:      color.set("#971607"); break;
            case AMETHYST:      color.set("#9A5CC6"); break;
            case LAPIS:         color.set("#21497B"); break;
            case LIGHTLIME:     color.set("#00b300"); break;
            case DARKGREEN:     color.set("#013220"); break;
            case XMAS:          color.set("#006400"); break;
            case MALACHITE:     color.set("#006633"); break;
            case GLASSBOTTLE:   color.set("#1f7e45"); break;
            case LIVINGGREEN:   color.set("#0d6245"); break;
            case BETTERCHERRY:  color.set("#5ccb7d"); break;
            default: break;
        }
        return Integer.decode(color.get());
    }
}
