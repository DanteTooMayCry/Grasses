package net.night.grasses.block.potted;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static net.night.grasses.block.plants.TintedSugarCane.biomesColorSourcePropertiesUpdate;
import static net.night.grasses.data.MethodsLib.keepColorType;
import static net.night.grasses.init.BlocksRegister.BIOMES_COLOR_SOURCE;


public class TintedPottedSugarCaneBlock extends TintedPottedPlantBlock {

    public TintedPottedSugarCaneBlock(@Nullable Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> plant) {
        super(emptyPot, plant);
        this.registerDefaultState(this.defaultBlockState().setValue(BIOMES_COLOR_SOURCE, Boolean.TRUE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BIOMES_COLOR_SOURCE);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState oldBlockState, boolean isMoving) {

        boolean biomesColorSource = biomesColorSourcePropertiesUpdate(keepColorType.get(blockPos));
        level.setBlockAndUpdate(blockPos, blockState.setValue(BIOMES_COLOR_SOURCE, biomesColorSource));
        super.onPlace(blockState, level, blockPos, oldBlockState, isMoving);
    }
}
