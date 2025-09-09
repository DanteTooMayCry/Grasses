package net.night.grasses.mixins;

import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import org.spongepowered.asm.mixin.Mixin;

import static net.minecraft.world.level.block.SlabBlock.TYPE;
import static net.minecraft.world.level.block.state.properties.SlabType.BOTTOM;
import static net.minecraft.world.level.block.state.properties.SlabType.TOP;
import static net.neoforged.neoforge.fluids.FluidInteractionRegistry.addInteraction;
import static net.night.grasses.init.BlocksRegister.BLUE_ICE_SLAB_BLOCK;
import static net.night.grasses.init.BlocksRegister.SOUL_SOIL_SLAB_BLOCK;

@Mixin(FluidInteractionRegistry.class)
public abstract class FluidInteractionRegistryMixin {


    static {
        addInteraction(NeoForgeMod.LAVA_TYPE.value(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, currentState) -> level.getBlockState(currentPos.below()).is(SOUL_SOIL_SLAB_BLOCK.get()) && level.getBlockState(currentPos.below()).getValue(TYPE) != BOTTOM && level.getBlockState(relativePos).is(Blocks.BLUE_ICE),
                Blocks.BASALT.defaultBlockState()
        ));

        addInteraction(NeoForgeMod.LAVA_TYPE.value(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, currentState) -> level.getBlockState(currentPos.below()).is(SOUL_SOIL_SLAB_BLOCK.get()) && level.getBlockState(currentPos.below()).getValue(TYPE) != BOTTOM && level.getBlockState(relativePos).is(BLUE_ICE_SLAB_BLOCK.get()) && level.getBlockState(relativePos).getValue(TYPE) != TOP,
                Blocks.BASALT.defaultBlockState()
        ));

        addInteraction(NeoForgeMod.LAVA_TYPE.value(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, currentState) -> level.getBlockState(currentPos.below()).is(Blocks.SOUL_SOIL) && level.getBlockState(relativePos).is(BLUE_ICE_SLAB_BLOCK.get())  && level.getBlockState(relativePos).getValue(TYPE) != TOP,
                Blocks.BASALT.defaultBlockState()
        ));
    }

}
