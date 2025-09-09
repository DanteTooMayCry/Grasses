package net.night.grasses.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BigDripleafBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.enums.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.ModMethods;
import net.night.grasses.init.BlocksRegister;
import net.night.grasses.item.DyeingBoneMealItem;
import net.night.grasses.util.ClientPlayerHelper;
import net.night.grasses.util.ModTags;

import java.util.List;

import static net.minecraft.world.level.block.Blocks.BIG_DRIPLEAF;
import static net.minecraft.world.level.block.Blocks.BIG_DRIPLEAF_STEM;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.SLAB_TYPE;
import static net.night.grasses.data.ModData.matchingCounterpartsPlants;
import static net.night.grasses.data.ModMethods.*;
import static net.night.grasses.init.BlocksRegister.BIG_DRIP_LEAF_STEM_TINTED;
import static net.night.grasses.init.BlocksRegister.SMALL_DRIP_LEAF_TINTED;

public class TintedBigDripLeaf extends BigDripleafBlock implements EntityBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TintedBigDripLeaf() {
        super(Properties.ofFullCopy(BIG_DRIPLEAF));
        this.registerDefaultState(this.defaultBlockState());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TintedBlockEntity(blockPos, blockState);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @javax.annotation.Nullable BlockGetter level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, tooltip, tooltipFlag);
        additionalHoverText(itemStack, tooltip);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState blockState, HitResult hitResult, LevelReader levelReader, BlockPos blockPos, Player player) {

        return ModMethods.getCloneItemStackBE((Level) levelReader, blockPos, blockState);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockStateOld, boolean pMovedByPiston) {

        onPlaceCounterPart(level, blockPos, blockState);
        super.onPlace(blockState, level, blockPos, blockStateOld, pMovedByPiston);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Level level = context.getLevel();
        BlockPos blockPosClicked = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockPosClicked.below());
        FluidState fluidstate = level.getFluidState(blockPosClicked);
        boolean flag = blockstate.is(BlocksRegister.BIG_DRIP_LEAF_TINTED.get()) || blockstate.is(BlocksRegister.BIG_DRIP_LEAF_STEM_TINTED.get());

        getStateForPlacementCounterPart(context, matchingCounterpartsPlants);
        if (flag) {
            keepColorType.put(blockPosClicked.below(), getColorType(level, blockPosClicked.below()));
            keepCounterPartType.put(blockPosClicked.below(), BIG_DRIP_LEAF_STEM_TINTED.get().defaultBlockState());
        }

        return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidstate.isSourceOfType(Fluids.WATER))).setValue(FACING, flag ? blockstate.getValue(FACING) : context.getHorizontalDirection().getOpposite());
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, this.asItem());
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {

        BlockState blockStateBelow = level.getBlockState(blockPos.below());
        if (blockStateBelow.hasProperty(SLAB_TYPE) && blockStateBelow.getValue(SLAB_TYPE) == SlabType.BOTTOM && !GrassesConfig.COMMON_CONFIG.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get())
            return false;

        return blockStateBelow.is(this) || blockStateBelow.is(BIG_DRIP_LEAF_STEM_TINTED.get()) || blockStateBelow.is(ModTags.Blocks.MOD_BIG_DRIPLEAF_PLACEABLE);
    }

    protected static boolean place(LevelAccessor level, BlockPos blockPosAbove, FluidState fluidStateAbove, Direction direction) {
        BlockState blockStateAbove = BlocksRegister.BIG_DRIP_LEAF_TINTED.get().defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidStateAbove.isSourceOfType(Fluids.WATER))).setValue(FACING, direction);
        return level.setBlock(blockPosAbove, blockStateAbove, 3);
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        BlockPos blockPosAbove = blockPos.above();
        BlockState blockStateAbove = serverLevel.getBlockState(blockPosAbove);
        ItemStack itemStack = ClientPlayerHelper.getMainHandItem();

        if (canPlaceAt(serverLevel, blockPosAbove, blockStateAbove)) {
            Direction direction = blockState.getValue(FACING);

            keepData(blockPos, BIG_DRIPLEAF_STEM, getColorType(serverLevel, blockPos));

            TintedBigDripleafStem.place(serverLevel, blockPos, blockState.getFluidState(), direction);

            if (itemStack.getItem() instanceof DyeingBoneMealItem)
                keepData(blockPos.above(), BIG_DRIPLEAF, getColorTypeFromNBT(itemStack));
            else
                keepData(blockPos.above(), BIG_DRIPLEAF, keepColorType.get(blockPos));

            place(serverLevel, blockPosAbove, blockStateAbove.getFluidState(), direction);
        }
    }

    public static void placeWithRandomHeight(LevelAccessor level, RandomSource pRandom, BlockPos blockPos, Direction pDirection, ColorType colorType) {
        int i = Mth.nextInt(pRandom, 2, 5);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = blockPos.mutable();
        int j = 0;

        while(j < i && canPlaceAt(level, blockpos$mutableblockpos, level.getBlockState(blockpos$mutableblockpos))) {
            ++j;
            blockpos$mutableblockpos.move(Direction.UP);
        }

        int k = blockPos.getY() + j - 1;
        blockpos$mutableblockpos.setY(blockPos.getY());

        while(blockpos$mutableblockpos.getY() < k) {
            keepData(blockpos$mutableblockpos.immutable(), BIG_DRIPLEAF_STEM, colorType);
            TintedBigDripleafStem.place(level, blockpos$mutableblockpos, level.getFluidState(blockpos$mutableblockpos), pDirection);
            blockpos$mutableblockpos.move(Direction.UP);
        }

        keepData(blockpos$mutableblockpos.immutable(), BIG_DRIPLEAF, colorType);
        TintedBigDripLeaf.place(level, blockpos$mutableblockpos, level.getFluidState(blockpos$mutableblockpos), pDirection);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pDirection == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (pState.getValue(WATERLOGGED)) {
                pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
            }

            return pDirection == Direction.UP && pNeighborState.is(this) ? BlocksRegister.BIG_DRIP_LEAF_STEM_TINTED.get().withPropertiesOf(pState) : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
        }
    }

    private static boolean canReplace(BlockState pState) {
        return pState.isAir() || pState.is(Blocks.WATER) || pState.is(SMALL_DRIP_LEAF_TINTED.get());
    }

    protected static boolean canPlaceAt(LevelHeightAccessor pLevel, BlockPos pPos, BlockState pState) {
        return !pLevel.isOutsideBuildHeight(pPos) && canReplace(pState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        boolean changeColorPermission = GrassesConfig.COMMON_CONFIG.ALLOW_CHANGE_PLANTS_COLOR.get();
        boolean changeIntoVanillaPermission = GrassesConfig.COMMON_CONFIG.ALLOW_CHANGE_TINTED_PLANTS_INTO_NOT_GRASSES.get();

        int interactionResult = ModMethods.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
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
