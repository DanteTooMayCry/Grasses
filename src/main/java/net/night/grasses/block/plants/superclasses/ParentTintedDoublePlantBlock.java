package net.night.grasses.block.plants.superclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.MethodsLib;

import java.util.List;

import static net.night.grasses.data.DataLib.*;
import static net.night.grasses.data.MethodsLib.*;
import static net.night.grasses.init.BlocksRegister.*;

public class ParentTintedDoublePlantBlock extends DoublePlantBlock implements EntityBlock {

    public ParentTintedDoublePlantBlock(Properties pProperties) {
        super(pProperties);
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

    public static void placeAt(LevelAccessor levelAccessor, BlockState tintedDoublePlantBlock, BlockState blockState, BlockPos blockPos, int flags, ColorType colorType, DoublePlantBlock doublePlantBlock) {

        if (!levelAccessor.isClientSide()) {
            keepData(blockPos, doublePlantBlock, colorType);
            levelAccessor.setBlock(blockPos, copyWaterloggedFrom(levelAccessor, blockPos, tintedDoublePlantBlock), flags);
        }
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

        Block block = blockState.getBlock().equals(FERN_TALL_TINTED.get()) ? FERN_TINTED.get() : GRASS_TINTED.get();
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, block.asItem());
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        boolean changeColorPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_PLANTS_COLOR.get();
        boolean changeIntoVanillaPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_TINTED_PLANTS_INTO_NOT_GRASSES.get();

        int interactionResult = MethodsLib.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
                changeColorPermission, changeIntoVanillaPermission, true, SoundEvents.GRASS_BREAK);

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
