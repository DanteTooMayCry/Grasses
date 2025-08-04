package net.night.grasses.block.plants;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.MethodsLib;
import net.night.grasses.item.DyeingBoneMealItem;
import net.night.grasses.util.ClientPlayerHelper;

import java.util.List;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.SLAB_TYPE;
import static net.night.grasses.data.DataLib.matchingCounterpartsPlants;
import static net.night.grasses.data.MethodsLib.*;

public class TintedSmallDripLeaf extends SmallDripleafBlock implements EntityBlock {

    public TintedSmallDripLeaf() {
        super(Properties.copy(Blocks.SMALL_DRIPLEAF));
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
    public ItemStack getCloneItemStack(BlockState blockState, HitResult hitResult, BlockGetter blockGetter, BlockPos blockPos, Player player) {
        return MethodsLib.getCloneItemStackBE((Level) blockGetter, blockPos, blockState);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockStateOld, boolean pMovedByPiston) {

        onPlaceCounterPart(level, blockPos, blockState);

        super.onPlace(blockState, level, blockPos, blockStateOld, pMovedByPiston);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        getStateForPlacementCounterPartDouble(context, matchingCounterpartsPlants);
        return super.getStateForPlacement(context);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {

        if (!level.isClientSide) {
            if (player.isCreative()) {
                //preventCreativeDropFromBottomPart(level, blockPos, blockState, player);
            } else {
                BlockEntity be = level.getBlockEntity(blockPos);
                if (be instanceof TintedBlockEntity blockEntity) {
                    dropResources(blockState, level, blockPos, blockEntity, player, player.getMainHandItem());
                }
                else
                    dropResources(blockState, level, blockPos, null , player, player.getMainHandItem());
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, this.asItem());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        if (!level.isClientSide()) {
            BlockPos blockPosAbove = blockPos.above();
            BlockState blockstate = DoublePlantBlock.copyWaterloggedFrom(level, blockPosAbove, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(FACING, blockState.getValue(FACING)));
            level.setBlock(blockPosAbove, blockstate, 3);
        }
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {

        if (blockState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
            BlockPos blockPosAbove = blockPos.above();

            ItemStack itemStack = ClientPlayerHelper.getMainHandItem();
            ColorType colorType = getColorType(serverLevel, blockPos);

            if (itemStack.getItem() instanceof DyeingBoneMealItem)
                colorType = getColorTypeFromNBT(itemStack);

            serverLevel.setBlock(blockPosAbove, serverLevel.getFluidState(blockPosAbove).createLegacyBlock(), 18);
            TintedBigDripLeaf.placeWithRandomHeight(serverLevel, randomSource, blockPos, blockState.getValue(FACING), colorType);


        } else {
            BlockPos blockPosBelow = blockPos.below();
            this.performBonemeal(serverLevel, randomSource, blockPosBelow, serverLevel.getBlockState(blockPosBelow));
        }
    }

    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {

        BlockState blockStateBelow = level.getBlockState(blockPos.below());
        if (blockStateBelow.hasProperty(SLAB_TYPE) && blockStateBelow.getValue(SLAB_TYPE) == SlabType.BOTTOM && !GrassesConfig.CommonConfig.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get())
            return false;

        if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return super.canSurvive(blockState, level, blockPos);
        } else {
            return this.mayPlaceOn(blockStateBelow, level, blockPos.below());
        }
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        boolean changeColorPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_PLANTS_COLOR.get();
        boolean changeIntoVanillaPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_TINTED_PLANTS_INTO_NOT_GRASSES.get();

        int interactionResult = MethodsLib.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
                changeColorPermission, changeIntoVanillaPermission, true, SoundEvents.SMALL_DRIPLEAF_BREAK);

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
