package net.night.grasses.block.plants.bop;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.night.grasses.block.plants.superclasses.ParentTintedDoublePlantBlock;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.item.DyeingBoneMealItem;

import javax.annotation.Nullable;

import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.data.ModData.matchingCounterpartsPlants;
import static net.night.grasses.data.ModMethods.*;

public class TintedDoubleWaterPlantBOP extends ParentTintedDoublePlantBlock implements SimpleWaterloggedBlock, BonemealableBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TintedDoubleWaterPlantBOP() {
        super(Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XZ));
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (isBOPLoaded)
            getStateForPlacementCounterPartDouble(context, matchingCounterpartsPlants);

        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos clickedBlockPos = context.getClickedPos();
        return clickedBlockPos.getY() < 255 && context.getLevel().getBlockState(clickedBlockPos.above()).canBeReplaced(context) ? this.defaultBlockState().setValue(WATERLOGGED, fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8) : null;
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockStateFacing, LevelAccessor level, BlockPos blockPosCurrent, BlockPos blockPosFacing) {
        if (blockState.getValue(WATERLOGGED)) {
            level.scheduleTick(blockPosCurrent, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return direction == Direction.DOWN && !this.canSurvive(blockState, level, blockPosCurrent) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, blockStateFacing, level, blockPosCurrent, blockPosFacing);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        BlockPos blockPosAbove = blockPos.above();
        level.setBlock(blockPosAbove, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, false), 3);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            BlockPos posBelow = pos.below();
            BlockState existingState = worldIn.getBlockState(pos);
            Block existingBlock = existingState.getBlock();
            return (existingBlock == this || existingState.liquid()) && this.isExposed(worldIn, pos.above()) && worldIn.getBlockState(posBelow).isFaceSturdy(worldIn, posBelow, Direction.UP);
        } else {
            BlockState blockstate = worldIn.getBlockState(pos.below());
            if (state.getBlock() != this) {
                return worldIn.isEmptyBlock(pos);
            } else {
                return this.isExposed(worldIn, pos) && blockstate.getBlock() == this && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER && (Boolean)blockstate.getValue(WATERLOGGED);
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos blockPos, BlockState blockState, boolean pIsClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {

        ItemStack itemStackToPop = new ItemStack(this);

        ColorType colorType = getColorType(serverLevel, blockPos);
        if (itemStackToPop.getItem() instanceof DyeingBoneMealItem)
            colorType =  getColorTypeFromNBT(itemStackToPop);

        assert colorType != null;
        setColorOnItemStack(itemStackToPop, colorType);

        popResource(serverLevel, blockPos, itemStackToPop);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected boolean isExposed(LevelReader world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock() == this ? !(Boolean)state.getValue(WATERLOGGED) : world.isEmptyBlock(pos);
    }
}
