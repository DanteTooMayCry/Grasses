package net.night.grasses.block.plants;

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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.MethodsLib;
import net.night.grasses.item.DyeingBoneMealItem;
import net.night.grasses.util.ClientPlayerHelper;

import java.util.List;

import static net.minecraft.tags.BlockTags.BAMBOO_PLANTABLE_ON;
import static net.minecraft.world.level.block.Blocks.BAMBOO;
import static net.minecraft.world.level.block.Blocks.BAMBOO_SAPLING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.SLAB_TYPE;
import static net.night.grasses.data.MethodsLib.*;
import static net.night.grasses.init.BlocksRegister.*;

public class TintedBambooSapling extends BambooSaplingBlock implements BonemealableBlock, EntityBlock {
    public TintedBambooSapling() {
        super(Properties.copy(BAMBOO_SAPLING));
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
        return MethodsLib.getCloneItemStackBE((Level) blockGetter, blockPos, BAMBOO_TINTED.get().defaultBlockState());
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockStateOld, boolean pMovedByPiston) {
        onPlaceCounterPart(level, blockPos, blockState);
        super.onPlace(blockState, level, blockPos, blockStateOld, pMovedByPiston);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, BAMBOO_TINTED.get().asItem());
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {

        BlockState blockStateBelow = pLevel.getBlockState(pPos.below());
        if (blockStateBelow.hasProperty(SLAB_TYPE) && blockStateBelow.getValue(SLAB_TYPE) == SlabType.BOTTOM && !GrassesConfig.CommonConfig.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get())
            return false;
        else
            return pLevel.getBlockState(pPos.below()).is(BAMBOO_PLANTABLE_ON);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {

        if(isFertileState(blockState)) {
            if (randomSource.nextInt(3) == 0 && serverLevel.isEmptyBlock(blockPos.above()) && serverLevel.getRawBrightness(blockPos.above(), 0) >= 9) {
                keepData(blockPos.above(), BAMBOO, getColorType(serverLevel, blockPos));
                this.growBamboo(serverLevel, blockPos, blockState);
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (pFacing == Direction.UP && pFacingState.is(BAMBOO_TINTED.get())) {
                keepData(pCurrentPos, BAMBOO, getColorType((Level) pLevel, pCurrentPos));
                pLevel.setBlock(pCurrentPos, BAMBOO_TINTED.get().defaultBlockState(), 2);
            }
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pLevel.getBlockState(pPos.above()).isAir();
    }
    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }
    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        if(!isFertileState(blockState))
            serverLevel.setBlock(blockPos, blockState.setValue(FERTILE, true), 3);
        else {
;
            ItemStack itemStack = ClientPlayerHelper.getMainHandItem();

            if (itemStack.getItem() instanceof DyeingBoneMealItem)
                keepColorType.put(blockPos.above(), getColorTypeFromNBT(itemStack));
            else
                keepColorType.put(blockPos.above(), getColorType(serverLevel, blockPos));
            keepCounterPartType.put(blockPos.above(), BAMBOO.defaultBlockState());
            this.growBamboo(serverLevel, blockPos, blockState);
        }
    }

    protected void growBamboo(Level pLevel, BlockPos pPos, BlockState pState) {

        pLevel.setBlock(pPos.above(), BAMBOO_TINTED.get().defaultBlockState().setValue(BambooStalkBlock.LEAVES, BambooLeaves.SMALL), 3);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        boolean changeColorPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_PLANTS_COLOR.get();
        boolean changeIntoVanillaPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_TINTED_PLANTS_INTO_NOT_GRASSES.get();

        int interactionResult = MethodsLib.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
                changeColorPermission, changeIntoVanillaPermission, false, SoundEvents.BAMBOO_BREAK);

        if (interactionResult == 0)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        else
            return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
