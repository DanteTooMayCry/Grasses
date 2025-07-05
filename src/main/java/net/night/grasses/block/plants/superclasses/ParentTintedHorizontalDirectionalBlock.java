package net.night.grasses.block.plants.superclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.MethodsLib;

import java.util.List;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.SLAB_TYPE;
import static net.night.grasses.data.DataLib.matchingCounterpartsPlants;
import static net.night.grasses.data.MethodsLib.*;

public class ParentTintedHorizontalDirectionalBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape SHAPE = Block.box(0.0, 13.0, 0.0, 16.0, 15.0, 16.0);

    public ParentTintedHorizontalDirectionalBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TintedBlockEntity(blockPos, blockState);
    }

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
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, this.asItem());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState directionState, LevelAccessor levelAccessor, BlockPos currentPos, BlockPos directionPos) {
        return !blockState.canSurvive(levelAccessor, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, directionState, levelAccessor, currentPos, directionPos);
    }

    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.is(BlockTags.DIRT);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        BlockPos blockPosBelow = blockPos.below();
        BlockState blockStateBelow = level.getBlockState(blockPosBelow);
        if (blockStateBelow.hasProperty(SLAB_TYPE) && blockStateBelow.getValue(SLAB_TYPE) == SlabType.BOTTOM && !GrassesConfig.CommonConfig.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get())
            return false;
        return this.mayPlaceOn(level.getBlockState(blockPosBelow), level, blockPosBelow);
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
