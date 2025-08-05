package net.night.grasses.block.plants;

import net.minecraft.core.BlockPos;
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
import net.minecraftforge.common.PlantType;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.ModMethods;

import java.util.List;

import static net.night.grasses.data.ModData.matchingCounterpartsPlants;
import static net.night.grasses.data.ModMethods.*;
import static net.night.grasses.init.BlocksRegister.*;

public class TintedWaterlily extends WaterlilyBlock implements BonemealableBlock, EntityBlock {

    public TintedWaterlily() {
        super(Properties.copy(Blocks.LILY_PAD));
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
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @javax.annotation.Nullable BlockGetter level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, tooltip, tooltipFlag);
        additionalHoverText(itemStack, tooltip);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState blockState, HitResult hitResult, BlockGetter blockGetter, BlockPos blockPos, Player player) {
        return ModMethods.getCloneItemStackBE((Level) blockGetter, blockPos, blockState);
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
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.WATER;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean pIsClient) {
        if (!GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_MOD_LILY_PAD.get())
            return false;
        else
            return levelReader.getBlockState(blockPos.above()).isAir();
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

        int interactionResult = ModMethods.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
                changeColorPermission, changeIntoVanillaPermission, false, SoundEvents.LILY_PAD_PLACE);

        if (interactionResult == 0)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        else
            return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
