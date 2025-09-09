package net.night.grasses.compat;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.night.grasses.block.dirtLike.*;
import net.night.grasses.block.leaves.superclasses.ParentTintedLeavesBlock;
import net.night.grasses.block.netherrackLike.GrassesNyliumBlock;
import net.night.grasses.block.netherrackLike.NyliumSlabBlock;
import net.night.grasses.block.plants.*;
import net.night.grasses.block.bars.TintedPlantInBars;
import net.night.grasses.block.plants.superclasses.*;
import net.night.grasses.block.potted.TintedPottedPlantBlock;
import net.night.grasses.compat.Jade.provider.ColorStateProvider;
import net.night.grasses.compat.Jade.provider.FertileStateProvider;
import net.night.grasses.compat.Jade.provider.TreeInfoProvider;
import snownee.jade.api.*;

import static net.night.grasses.Grasses.isBOPLoaded;


@WailaPlugin
public class JadeGrassesPlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {

    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, GrassesBlock.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, GrassesSlabBlock.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, MyceliumSlabBlock.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, GrassesMyceliumBlock.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, PodzolSlabBlock.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, GrassesPodzolBlock.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, GrassesNyliumBlock.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, NyliumSlabBlock.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, TintedGrassPlant.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, TintedSeaGrass.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, TintedWaterlily.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, TintedVine.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, TintedSugarCane.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, TintedBamboo.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, TintedBambooSapling.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, ParentTintedGrowingPlantHeadBlock.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, ParentTintedGrowingPlantBodyBlock.class);
        registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, TintedCactus.class);

        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, ParentTintedDoublePlantBlock.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, ParentTintedLeavesBlock.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedPottedPlantBlock.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedSeaGrass.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedGrassPlant.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedTallSeaGrass.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedVine.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedWaterlily.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedSugarCane.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedBamboo.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedBambooSapling.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedBigDripLeaf.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedBigDripleafStem.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedSmallDripLeaf.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedKelp.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedKelpPlant.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedCactus.class);
        registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, TintedPlantInBars.class);

        registration.registerBlockComponent(TreeInfoProvider.LOG_INSTANCE, RotatedPillarBlock.class);

        if (isBOPLoaded) {
            registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, ParentTintedBushBlock.class);
            registration.registerBlockComponent(FertileStateProvider.FERTILE_INSTANCE, ParentTintedPinkPetals.class);

            registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, ParentTintedBushBlock.class);
            registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, ParentTintedPinkPetals.class);
            registration.registerBlockComponent(ColorStateProvider.COLOR_INSTANCE, ParentTintedHorizontalDirectionalBlock.class);
        }


    }

}
