package net.night.grasses.block.bars;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Objects;

import static net.minecraft.world.level.block.Blocks.IRON_BARS;
import static net.night.grasses.data.ModData.matchingBarsWithPlant;
import static net.night.grasses.data.ModMethods.getColorType;
import static net.night.grasses.data.ModMethods.setColorOnItemStack;

public class PlantInBars extends IronBarsBlock {
    public PlantInBars() {
        super(Properties.ofFullCopy(IRON_BARS).mapColor(MapColor.PLANT));
        this.registerDefaultState(this.defaultBlockState());
    }

    @Override
    public ItemStack getCloneItemStack(BlockState blockState, HitResult hitResult, LevelReader levelReader, BlockPos blockPos, Player player) {
        return new ItemStack(IRON_BARS);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (interactionHand != InteractionHand.MAIN_HAND)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);

        ItemStack itemStack = player.getMainHandItem();
        boolean isShears = itemStack.getItem() instanceof ShearsItem;
        boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(itemStack);

        if (isShears && !hasSilkTouch) {
            boolean north = blockState.getValue(NORTH);
            boolean east = blockState.getValue(EAST);
            boolean south = blockState.getValue(SOUTH);
            boolean west = blockState.getValue(WEST);
            Block block = blockState.getBlock();

            if (player instanceof ServerPlayer) {
                ItemStack itemStackContent = new ItemStack(matchingBarsWithPlant.get(blockState.getBlock()));
                if (block instanceof TintedPlantInBars)
                    setColorOnItemStack(itemStackContent, Objects.requireNonNull(getColorType(level, blockPos)));
                blockState = IRON_BARS.defaultBlockState().setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west).setValue(WATERLOGGED, blockState.getValue(WATERLOGGED));

                level.setBlockAndUpdate(blockPos, blockState);

                Block.popResourceFromFace(level, blockPos, Direction.UP,  itemStackContent);
                itemStack.hurtAndBreak(1, player, (e) -> {
                    e.broadcastBreakEvent(interactionHand);
                });
            }
            level.addDestroyBlockEffect(blockPos, blockState);
            level.playSound(null, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            player.swing(interactionHand, true);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        else
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}
