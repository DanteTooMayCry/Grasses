package net.night.grasses.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.night.grasses.block.other.ModEndPortalFrameBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.night.grasses.init.BlocksRegister.END_PORTAL_FRAME_BLOCK;

@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin extends Item {

    public EnderEyeItemMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(at = @At("HEAD"), method = "useOn", cancellable = true)
    public void useOn(UseOnContext pContext, CallbackInfoReturnable<InteractionResult> cir) {

        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);

        if (blockstate.is(END_PORTAL_FRAME_BLOCK.get()) && !blockstate.getValue(EndPortalFrameBlock.HAS_EYE)) {
            if (level.isClientSide) {
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else {
                BlockState blockstate1 = blockstate.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(true));
                Block.pushEntitiesUp(blockstate, blockstate1, level, blockpos);
                level.setBlock(blockpos, blockstate1, 2);
                level.updateNeighbourForOutputSignal(blockpos, END_PORTAL_FRAME_BLOCK.get());
                pContext.getItemInHand().shrink(1);
                level.levelEvent(1503, blockpos, 0);
                BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = ModEndPortalFrameBlock.getOrCreatePortalShape().find(level, blockpos);
                if (blockpattern$blockpatternmatch != null) {
                    BlockPos blockpos1 = blockpattern$blockpatternmatch.getFrontTopLeft().offset(-3, 0, -3);

                    for(int i = 0; i < 3; ++i) {
                        for(int j = 0; j < 3; ++j) {
                            level.setBlock(blockpos1.offset(i, 0, j), Blocks.END_PORTAL.defaultBlockState(), 2);
                        }
                    }

                    level.globalLevelEvent(1038, blockpos1.offset(1, 0, 1), 0);
                }

                cir.setReturnValue(InteractionResult.CONSUME);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    public void use(Level pLevel, Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.NONE);
        if (blockhitresult.getType() == HitResult.Type.BLOCK && pLevel.getBlockState(blockhitresult.getBlockPos()).is(END_PORTAL_FRAME_BLOCK.get())) {
            cir.setReturnValue(InteractionResultHolder.pass(itemstack));
        }
    }
}
