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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.ModMethods;

import java.util.List;

import static net.night.grasses.data.ModData.*;
import static net.night.grasses.data.ModMethods.*;
import static net.night.grasses.init.BlocksRegister.FERTILE;

public class ParentTintedPinkPetals extends PinkPetalsBlock implements EntityBlock {

    public ParentTintedPinkPetals(Properties properties) {
        super(properties);
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
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        boolean changeColorPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_PLANTS_COLOR.get();
        boolean changeIntoVanillaPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_TINTED_PLANTS_INTO_NOT_GRASSES.get();

        int interactionResult = ModMethods.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
                changeColorPermission, changeIntoVanillaPermission, false, SoundEvents.GRASS_BREAK);

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
