package net.night.grasses.block.plants;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.MethodsLib;

import java.util.List;

import static net.minecraft.world.level.block.Blocks.SUGAR_CANE;
import static net.night.grasses.data.DataLib.*;
import static net.night.grasses.data.MethodsLib.*;
import static net.night.grasses.init.BlocksRegister.*;

public class TintedSugarCane extends SugarCaneBlock implements BonemealableBlock, EntityBlock {

    public TintedSugarCane() {
        super(Properties.copy(Blocks.SUGAR_CANE));
        this.registerDefaultState(this.defaultBlockState().setValue(FERTILE, Boolean.TRUE).setValue(BIOMES_COLOR_SOURCE, Boolean.TRUE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FERTILE, BIOMES_COLOR_SOURCE);
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

        getStateForPlacementCounterPart(context, matchingCounterpartsPlants);
        return super.getStateForPlacement(context);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, this.asItem());
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return state.is(this);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {

        if (isFertileState(blockState)) {
            if (serverLevel.isEmptyBlock(blockPos.above())) {
                int i;
                for (i = 1; serverLevel.getBlockState(blockPos.below(i)).is(this); ++i) {
                }

                if (i < 3) {
                    int j = blockState.getValue(AGE);
                    if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(serverLevel, blockPos, blockState, true)) {
                        ColorType colorType = getColorType(serverLevel, blockPos);
                        if (j == 15) {

                            boolean biomesColorSource = biomesColorSourcePropertiesUpdate(colorType);
                            keepData(blockPos.above(), SUGAR_CANE, colorType);

                            serverLevel.setBlockAndUpdate(blockPos.above(), this.defaultBlockState().setValue(BIOMES_COLOR_SOURCE, biomesColorSource));
                            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(serverLevel, blockPos.above(), this.defaultBlockState());
                            serverLevel.setBlock(blockPos, blockState.setValue(AGE, Integer.valueOf(0)), 4);
                        } else
                            serverLevel.setBlock(blockPos, blockState.setValue(AGE, Integer.valueOf(j + 1)), 4);
                    }
                }
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos blockPos, BlockState blockState, boolean isClient) {

        int i = this.getHeightAboveUpToMax(level, blockPos);
        int j = this.getHeightBelowUpToMax(level, blockPos);

        boolean isValid = i + j + 1 < GrassesConfig.CommonConfig.SUGAR_CANE_MAX_HEIGHT.get();

        return GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_MOD_SUGAR_CANE.get() && isValid;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        if(!isFertileState(blockState)) {
            serverLevel.setBlock(blockPos, blockState.setValue(FERTILE, true), 3);
        }
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {


        boolean changeColorPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_PLANTS_COLOR.get();
        boolean changeIntoVanillaPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_TINTED_PLANTS_INTO_NOT_GRASSES.get();

        int interactionResult = MethodsLib.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
                changeColorPermission, changeIntoVanillaPermission, false, SoundEvents.GRASS_BREAK);

        if (interactionResult == 0)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        else
            return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public static boolean biomesColorSourcePropertiesUpdate (ColorType colorType) {

        int index = colorTypeList.indexOf(colorType);

        if (index <=22)
            return true;
        else
            return false;
    }

    protected int getHeightAboveUpToMax(BlockGetter pLevel, BlockPos pPos) {
        int i;
        for(i = 0; i < GrassesConfig.CommonConfig.SUGAR_CANE_MAX_HEIGHT.get() && pLevel.getBlockState(pPos.above(i + 1)).is(SUGAR_CANE_TINTED.get()); ++i) {
        }

        return i;
    }

    protected int getHeightBelowUpToMax(BlockGetter pLevel, BlockPos pPos) {
        int i;
        for(i = 0; i < GrassesConfig.CommonConfig.SUGAR_CANE_MAX_HEIGHT.get() && pLevel.getBlockState(pPos.below(i + 1)).is(SUGAR_CANE_TINTED.get()); ++i) {
        }

        return i;
    }
}
