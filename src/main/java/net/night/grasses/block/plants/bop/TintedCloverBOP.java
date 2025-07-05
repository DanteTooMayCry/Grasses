package net.night.grasses.block.plants.bop;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.night.grasses.block.plants.superclasses.ParentTintedPinkPetals;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.item.DyeingBoneMealItem;

import static biomesoplenty.api.block.BOPBlocks.CLOVER;
import static biomesoplenty.api.block.BOPBlocks.HUGE_CLOVER_PETAL;
import static net.minecraft.tags.BlockTags.DIRT;
import static net.minecraft.tags.BlockTags.LEAVES;
import static net.minecraft.world.level.block.Blocks.AIR;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.data.MethodsLib.*;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.HUGE_CLOVER_TINTED;

public class TintedCloverBOP extends ParentTintedPinkPetals {
    public TintedCloverBOP() {
        super(Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY));
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {

        if(!isFertileState(blockState))
            return true;
        else
            return (double)randomSource.nextFloat() < 0.4;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {

        int i = blockState.getValue(AMOUNT);
        ItemStack itemStack = Minecraft.getInstance().player.getMainHandItem();
        ColorType colorType = getColorType(serverLevel, blockPos);
        if (itemStack.getItem() instanceof DyeingBoneMealItem)
            colorType = getColorTypeFromNBT(itemStack);

        if(!isFertileState(blockState)) {
            serverLevel.setBlock(blockPos, blockState.setValue(FERTILE, true), 3);
        } else if (i < 4) {
            keepData(blockPos, CLOVER, colorType);
            serverLevel.setBlock(blockPos, blockState.setValue(AMOUNT, i + 1), 2);
        } else {
            if (isBOPLoaded && checkSpace(serverLevel, blockPos) && checkBlocksBelow(serverLevel, blockPos.below()) && isFertileState(blockState)) {

                BlockState hugeClover = HUGE_CLOVER_TINTED.get().defaultBlockState();

                keepData(blockPos, HUGE_CLOVER_PETAL, colorType);
                keepData(blockPos.south(), HUGE_CLOVER_PETAL, colorType);
                keepData(blockPos.east(), HUGE_CLOVER_PETAL, colorType);
                keepData(blockPos.south().east(), HUGE_CLOVER_PETAL, colorType);

                serverLevel.setBlock(blockPos, hugeClover.setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH), 19);
                serverLevel.setBlock(blockPos.south(), hugeClover.setValue(HorizontalDirectionalBlock.FACING, Direction.WEST), 19);
                serverLevel.setBlock(blockPos.east(), hugeClover.setValue(HorizontalDirectionalBlock.FACING, Direction.EAST), 19);
                serverLevel.setBlock(blockPos.south().east(), hugeClover.setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH), 19);
            }
            else {
                ItemStack itemStackToPop = new ItemStack(this);
                assert colorType != null;
                setColorOnItemStack(itemStackToPop, colorType);
                popResource(serverLevel, blockPos, itemStackToPop);
            }
        }
    }

    private boolean checkSpace(ServerLevel level, BlockPos blockPos)
    {
        for (int x = 0; x <= 1; x++)
        {
            for (int z = 0; z <= 1; z++)
            {
                BlockPos blockPosOffset = blockPos.offset(x, 0, z);
                BlockState blockState = level.getBlockState(blockPosOffset);

                if (!blockState.is(AIR) && !(blockState.getBlock() instanceof BushBlock) && !blockState.is(LEAVES)) {
                    return false;
                }
            }
        }

        return true;
    }
    private boolean checkBlocksBelow(ServerLevel level, BlockPos blockPosBelow)
    {
        for (int x = 0; x <= 1; x++)
        {
            for (int z = 0; z <= 1; z++)
            {
                BlockPos blockPosOffset = blockPosBelow.offset(x, 0, z);
                BlockState blockState = level.getBlockState(blockPosOffset);
                if (blockState.is(DIRT))
                    return true;
            }
        }

        return false;
    }
}
