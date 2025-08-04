package net.night.grasses.block.plants;

import net.minecraft.BlockUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BigDripleafStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.MethodsLib;
import net.night.grasses.item.DyeingBoneMealItem;
import net.night.grasses.util.ClientPlayerHelper;
import net.night.grasses.util.ModTags;

import java.util.List;
import java.util.Optional;

import static net.minecraft.world.level.block.Blocks.BIG_DRIPLEAF;
import static net.minecraft.world.level.block.Blocks.BIG_DRIPLEAF_STEM;
import static net.night.grasses.data.DataLib.*;
import static net.night.grasses.data.MethodsLib.*;
import static net.night.grasses.init.BlocksRegister.*;

public class TintedBigDripleafStem extends BigDripleafStemBlock implements EntityBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TintedBigDripleafStem() {
        super(Properties.copy(Blocks.BIG_DRIPLEAF_STEM));
        this.registerDefaultState(this.defaultBlockState().setValue(FERTILE, Boolean.TRUE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FERTILE);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TintedBlockEntity(blockPos, blockState);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return MethodsLib.getCloneItemStackBE((Level) blockGetter, blockPos, BIG_DRIP_LEAF_TINTED.get().defaultBlockState());
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockStateOld, boolean pMovedByPiston) {

        onPlaceCounterPart(level, blockPos, blockState);
        super.onPlace(blockState, level, blockPos, blockStateOld, pMovedByPiston);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        getStateForPlacementCounterPart(context, matchingCounterpartsPlants);

        return super.getStateForPlacement(context);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, BIG_DRIP_LEAF_TINTED.get().asItem());
    }


    protected static boolean place(LevelAccessor level, BlockPos blockPos, FluidState fluidState, Direction direction) {
        BlockState blockState = BIG_DRIP_LEAF_STEM_TINTED.get().defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidState.isSourceOfType(Fluids.WATER))).setValue(FACING, direction);
        return level.setBlock(blockPos, blockState, 19);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        BlockState blockstate1 = pLevel.getBlockState(pPos.above());
        return (blockstate.is(this) || blockstate.is(ModTags.Blocks.MOD_BIG_DRIPLEAF_PLACEABLE)) && (blockstate1.is(this) || blockstate1.is(BIG_DRIP_LEAF_TINTED.get()));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        Optional<BlockPos> optional = BlockUtil.getTopConnectedBlock(pLevel, pPos, pState.getBlock(), Direction.UP, BIG_DRIP_LEAF_TINTED.get());
        if (!optional.isPresent()) {
            return false;
        } else {
            BlockPos blockpos = optional.get().above();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            return TintedBigDripLeaf.canPlaceAt(pLevel, blockpos, blockstate);
        }
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {

        Optional<BlockPos> optional = BlockUtil.getTopConnectedBlock(serverLevel, blockPos, blockState.getBlock(), Direction.UP, BIG_DRIP_LEAF_TINTED.get());
        if (optional.isPresent()) {
            BlockPos blockPosOptional = optional.get();
            BlockPos blockPosAbove = blockPosOptional.above();
            Direction direction = blockState.getValue(FACING);
            ItemStack itemStack = ClientPlayerHelper.getMainHandItem();

            keepData(blockPosOptional, BIG_DRIPLEAF_STEM, getColorType(serverLevel, blockPosOptional));

            if (itemStack.getItem() instanceof DyeingBoneMealItem)
                keepData(blockPosAbove, BIG_DRIPLEAF, getColorTypeFromNBT(itemStack));
            else
                keepData(blockPosAbove, BIG_DRIPLEAF, getColorType(serverLevel, blockPos));

            place(serverLevel, blockPosOptional, serverLevel.getFluidState(blockPosOptional), direction);

            TintedBigDripLeaf.place(serverLevel, blockPosAbove, serverLevel.getFluidState(blockPosAbove), direction);
        }
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        boolean changeColorPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_PLANTS_COLOR.get();
        boolean changeIntoVanillaPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_TINTED_PLANTS_INTO_NOT_GRASSES.get();

        int interactionResult = MethodsLib.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
                changeColorPermission, changeIntoVanillaPermission, false, SoundEvents.BIG_DRIPLEAF_BREAK);

        if (interactionResult == 0)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        else
            return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }
}
