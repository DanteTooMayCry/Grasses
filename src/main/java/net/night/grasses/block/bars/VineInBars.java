package net.night.grasses.block.bars;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.night.grasses.data.ModMethods;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static net.night.grasses.init.BlocksRegister.CLICKED_SIDE;

public class VineInBars extends PlantInBars {
    public VineInBars() {
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(CLICKED_SIDE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, CLICKED_SIDE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return pFacing.getAxis().isHorizontal() ? pState.setValue(PROPERTY_BY_DIRECTION.get(pFacing), this.attachsTo(pFacingState, pFacingState.isFaceSturdy(pLevel, pFacingPos, pFacing.getOpposite()))) : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (interactionHand != InteractionHand.MAIN_HAND)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);

        int interactionResult = ModMethods.useOnBarsPlant(blockState, level, blockPos, player, interactionHand, blockHitResult);

        if (interactionResult == 0)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        else
            return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
