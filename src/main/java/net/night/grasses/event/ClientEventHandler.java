package net.night.grasses.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.Grasses;
import net.night.grasses.block.blockEntity.renderer.DyeingStationBlockEntityRenderer;
import net.night.grasses.block.blockEntity.screen.DyeingStationScreen;
import net.night.grasses.block.blockEntity.screen.MenuTypesRegister;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.colorManagers.ColorsDefinition;
import net.night.grasses.init.BlocksRegister;
import net.night.grasses.particle.ModParticles;
import net.night.grasses.particle.custom.LargeLeafParticle;
import net.night.grasses.particle.custom.LeafParticle;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static net.night.grasses.data.ModMethods.getColorType;
import static net.night.grasses.init.BlockEntitiesRegister.DYEING_STATION_BE;

@Mod.EventBusSubscriber(modid = Grasses.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    private static BlockState blockStateForColor;
    private static BlockPos blockPosForColor;

    public static Map<ResourceLocation, RegistryObject<Block>> pottedMatching = new HashMap<>();

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(DYEING_STATION_BE.get(), DyeingStationBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

        pottedMatching.put(BlocksRegister.GRASS_TINTED.getId(), BlocksRegister.GRASS_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.FERN_TINTED.getId(), BlocksRegister.FERN_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.SEAGRASS_TINTED.getId(), BlocksRegister.SEAGRASS_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.BAMBOO_TINTED.getId(), BlocksRegister.BAMBOO_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.SUGAR_CANE_TINTED.getId(), BlocksRegister.SUGAR_CANE_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.VINE_TINTED.getId(), BlocksRegister.VINE_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.BIG_DRIP_LEAF_TINTED.getId(), BlocksRegister.BIG_DRIP_LEAF_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.SMALL_DRIP_LEAF_TINTED.getId(), BlocksRegister.SMALL_DRIP_LEAF_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.KELP_TINTED.getId(), BlocksRegister.KELP_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.CACTUS_TINTED.getId(), BlocksRegister.CACTUS_POTTED_TINTED);

        pottedMatching.put(new ResourceLocation("minecraft:grass"), BlocksRegister.GRASS_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:seagrass"), BlocksRegister.SEAGRASS_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:sugar_cane"), BlocksRegister.SUGAR_CANE_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:vine"), BlocksRegister.VINE_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:big_dripleaf"), BlocksRegister.BIG_DRIP_LEAF_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:small_dripleaf"), BlocksRegister.SMALL_DRIP_LEAF_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:kelp"), BlocksRegister.KELP_POTTED);

        pottedMatching.put(new ResourceLocation("minecraft:acacia_leaves"), BlocksRegister.ACACIA_LEAVES_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:azalea_leaves"), BlocksRegister.AZALEA_LEAVES_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:birch_leaves"), BlocksRegister.BIRCH_LEAVES_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:cherry_leaves"), BlocksRegister.CHERRY_LEAVES_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:dark_oak_leaves"), BlocksRegister.DARK_OAK_LEAVES_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:flowering_azalea_leaves"), BlocksRegister.FLOWERING_AZALEA_LEAVES_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:jungle_leaves"), BlocksRegister.JUNGLE_LEAVES_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:mangrove_leaves"), BlocksRegister.MANGROVE_LEAVES_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:oak_leaves"), BlocksRegister.OAK_LEAVES_POTTED);
        pottedMatching.put(new ResourceLocation("minecraft:spruce_leaves"), BlocksRegister.SPRUCE_LEAVES_POTTED);

        pottedMatching.put(BlocksRegister.ACACIA_LEAVES_BLOCK.getId(), BlocksRegister.ACACIA_LEAVES_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.AZALEA_LEAVES_BLOCK.getId(), BlocksRegister.AZALEA_LEAVES_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.BIRCH_LEAVES_BLOCK.getId(), BlocksRegister.BIRCH_LEAVES_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.CHERRY_LEAVES_BLOCK.getId(), BlocksRegister.CHERRY_LEAVES_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.DARK_OAK_LEAVES_BLOCK.getId(), BlocksRegister.DARK_OAK_LEAVES_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.FLOWERING_AZALEA_LEAVES_BLOCK.getId(), BlocksRegister.FLOWERING_AZALEA_LEAVES_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.JUNGLE_LEAVES_BLOCK.getId(), BlocksRegister.JUNGLE_LEAVES_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.MANGROVE_LEAVES_BLOCK.getId(), BlocksRegister.MANGROVE_LEAVES_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.OAK_LEAVES_BLOCK.getId(), BlocksRegister.OAK_LEAVES_POTTED_TINTED);
        pottedMatching.put(BlocksRegister.SPRUCE_LEAVES_BLOCK.getId(), BlocksRegister.SPRUCE_LEAVES_POTTED_TINTED);

        for (Map.Entry<ResourceLocation, RegistryObject<Block>> resource : pottedMatching.entrySet()) {
            event.enqueueWork(() -> {
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(resource.getKey(), resource.getValue());
            });
        }

        MenuScreens.register(MenuTypesRegister.DYEING_STATION_MENU.get(), DyeingStationScreen::new);
    }


    @SubscribeEvent
    static void registerParticleFactories(RegisterParticleProvidersEvent event) {

        event.registerSpriteSet(ModParticles.JACARANDA_LEAVES_PARTICLE.get(), (spriteSet) -> {
            return (p_277217_, pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) -> {
                return new LeafParticle(pLevel, pX, pY, pZ, spriteSet, getColorRGB());
            };
        });
        event.registerSpriteSet(ModParticles.SNOWBLOSSOM_LEAVES_PARTICLE.get(), (spriteSet) -> {
            return (p_277217_, pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) -> {
                return new LeafParticle(pLevel, pX, pY, pZ, spriteSet, getColorRGB());
            };
        });

        event.registerSpriteSet(ModParticles.MAPLE_LEAVES_PARTICLE.get(), (spriteSet) -> {
            return (p_277217_, pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) -> {
                return new LargeLeafParticle(pLevel, pX, pY, pZ, spriteSet, getColorRGB());
            };
        });
    }

    public static void getColor(BlockState blockState, BlockPos blockPos) {
        blockStateForColor = blockState;
        blockPosForColor = blockPos;
    }

    public static Color getColorRGB() {

        Color colorRGB = new Color(255, 255, 255);

        if (blockStateForColor != null) {
            ColorType colorType = getColorType(blockPosForColor);
            int hex = ColorsDefinition.takeColor(colorType, blockStateForColor, null);
            String color = String.valueOf(hex);
            colorRGB = Color.decode(color);
        }

        return colorRGB;
    }
}
