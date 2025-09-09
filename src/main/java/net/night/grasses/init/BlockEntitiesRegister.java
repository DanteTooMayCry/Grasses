package net.night.grasses.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.Grasses;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.block.blockEntity.DyeingStationBlockEntity;

import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;

public class BlockEntitiesRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Grasses.MOD_ID);

    public static final RegistryObject<BlockEntityType<DyeingStationBlockEntity>> DYEING_STATION_BE =
            BLOCK_ENTITIES.register("dyeing_station_be", () ->
                    BlockEntityType.Builder.of(DyeingStationBlockEntity::new,
                            DYEING_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<TintedBlockEntity>> TINTED_BE =
            BLOCK_ENTITIES.register("tinted_be", () ->
                    BlockEntityType.Builder.of(TintedBlockEntity::new,
                            ACACIA_LEAVES_BLOCK.get(), AZALEA_LEAVES_BLOCK.get(), BIRCH_LEAVES_BLOCK.get(), CHERRY_LEAVES_BLOCK.get(), DARK_OAK_LEAVES_BLOCK.get(), FLOWERING_AZALEA_LEAVES_BLOCK.get(), JUNGLE_LEAVES_BLOCK.get(), MANGROVE_LEAVES_BLOCK.get(), OAK_LEAVES_BLOCK.get(), SPRUCE_LEAVES_BLOCK.get(),
                            FIR_LEAVES_BLOCK.get(), PINE_LEAVES_BLOCK.get(), MAPLE_LEAVES_BLOCK.get(), REDWOOD_LEAVES_BLOCK.get(), MAHOGANY_LEAVES_BLOCK.get(), JACARANDA_LEAVES_BLOCK.get(), PALM_LEAVES_BLOCK.get(), WILLOW_LEAVES_BLOCK.get(), DEAD_LEAVES_BLOCK.get(), MAGIC_LEAVES_BLOCK.get(), UMBRAN_LEAVES_BLOCK.get(), EMPYREAL_LEAVES_BLOCK.get(), FLOWERING_OAK_LEAVES_BLOCK.get(), ORIGIN_LEAVES_BLOCK.get(), CYPRESS_LEAVES_BLOCK.get(), HELLBARK_LEAVES_BLOCK.get(),
                            GRASS_TINTED.get(), GRASS_TALL_TINTED.get(), FERN_TINTED.get(), FERN_TALL_TINTED.get(), SEAGRASS_TINTED.get(), SEAGRASS_TALL_TINTED.get(), VINE_TINTED.get(), LILY_TINTED.get(), SUGAR_CANE_TINTED.get(), BAMBOO_TINTED.get(), BAMBOO_SAPLING_TINTED.get(), BIG_DRIP_LEAF_TINTED.get(), BIG_DRIP_LEAF_STEM_TINTED.get(), SMALL_DRIP_LEAF_TINTED.get(), KELP_TINTED.get(), KELP_PLANT_TINTED.get(), CACTUS_TINTED.get(), GRASS_IN_BARS.get(), FERN_IN_BARS.get(), VINE_IN_BARS.get(), TINTED_GRASS_IN_BARS.get(), TINTED_FERN_IN_BARS.get(), TINTED_VINE_IN_BARS.get(),
                            BUSH_TINTED.get(), SPROUT_TINTED.get(), CLOVER_TINTED.get(), HUGE_CLOVER_TINTED.get(), HUGE_LILY_PAD_TINTED.get(), WATERLILY_TINTED.get(), HIGH_GRASS_TINTED.get(), HIGH_GRASS_PLANT_TINTED.get(), TINY_CACTUS_TINTED.get(), WATER_GRASS_TINTED.get(), LEAF_PILE_TINTED.get(), WILLOW_VINE_TINTED.get()
                    ).build(null));


    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
