package net.night.grasses.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.night.grasses.Grasses;
import net.night.grasses.block.leaves.TintedLeavesBlock;
import net.night.grasses.block.leaves.superclasses.ParentTintedLeavesBlock;
import net.night.grasses.block.leaves.TintedWillowLeavesBlock;
import net.night.grasses.block.plants.TintedVine;
import net.night.grasses.block.plants.bop.*;
import net.night.grasses.block.plants.superclasses.ParentTintedBushBlock;
import net.night.grasses.block.plants.superclasses.ParentTintedHorizontalDirectionalBlock;
import net.night.grasses.enums.GrassesQuarterProperty;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.block.Blocks.LILY_PAD;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.init.BlocksRegister.*;

public class BlocksRegisterBoP {

    public static final BooleanProperty MOSSY = BooleanProperty.create("mossy");
    public static final IntegerProperty VARIANT_LILY = IntegerProperty.create("variant_lily", 0, 2);
    public static final EnumProperty<GrassesQuarterProperty> GRASSES_QUARTER = EnumProperty.create("grasses_quarter", GrassesQuarterProperty.class);

    public static final List<RegistryObject<Block>> tintedBOPleavesRegistryBlocksList   = new ArrayList<>();
    public static final List<Block>                 tintedBOPleavesBlocksList           = new ArrayList<>();
    public static final List<RegistryObject<Block>> tintedBOPplantRegistryBlockList     = new ArrayList<>();
    public static final List<Block>                 tintedBOPPlantBlockList             = new ArrayList<>();

    public static final DeferredRegister<Block> BLOCKS_BOP =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Grasses.MOD_ID);

    public static final RegistryObject<Block> FIR_LEAVES_BLOCK              = registerBlock("fir", 32);
    public static final RegistryObject<Block> PINE_LEAVES_BLOCK             = registerBlock("pine", 32);
    public static final RegistryObject<Block> MAPLE_LEAVES_BLOCK            = registerBlock("maple", 32);
    public static final RegistryObject<Block> REDWOOD_LEAVES_BLOCK          = registerBlock("redwood", 32);
    public static final RegistryObject<Block> MAHOGANY_LEAVES_BLOCK         = registerBlock("mahogany", 32);
    public static final RegistryObject<Block> JACARANDA_LEAVES_BLOCK        = registerBlock("jacaranda", 32);
    public static final RegistryObject<Block> PALM_LEAVES_BLOCK             = registerBlock("palm", 32);
    public static final RegistryObject<Block> WILLOW_LEAVES_BLOCK           = registerBlock("willow", 322);
    public static final RegistryObject<Block> DEAD_LEAVES_BLOCK             = registerBlock("dead", 32);
    public static final RegistryObject<Block> MAGIC_LEAVES_BLOCK            = registerBlock("magic", 32);
    public static final RegistryObject<Block> UMBRAN_LEAVES_BLOCK           = registerBlock("umbran", 32);
    public static final RegistryObject<Block> EMPYREAL_LEAVES_BLOCK         = registerBlock("empyreal", 32);
    public static final RegistryObject<Block> FLOWERING_OAK_LEAVES_BLOCK    = registerBlock("flowering_oak", 32);
    public static final RegistryObject<Block> ORIGIN_LEAVES_BLOCK           = registerBlock("origin", 32);
    public static final RegistryObject<Block> CYPRESS_LEAVES_BLOCK          = registerBlock("cypress", 32);
    public static final RegistryObject<Block> HELLBARK_LEAVES_BLOCK         = registerBlock("hellbark", 32);

    public static final RegistryObject<Block> BUSH_TINTED                   = registerBlock("bush_tinted", 400);
    public static final RegistryObject<Block> SPROUT_TINTED                 = registerBlock("sprout_tinted", 400);
    public static final RegistryObject<Block> CLOVER_TINTED                 = registerBlock("clover_tinted", 401);
    public static final RegistryObject<Block> HUGE_CLOVER_TINTED            = registerBlock("huge_clover_tinted", 402);
    public static final RegistryObject<Block> HIGH_GRASS_TINTED             = registerBlock("high_grass_tinted", 403);
    public static final RegistryObject<Block> HIGH_GRASS_PLANT_TINTED       = registerBlock("high_grass_plant_tinted", 404);
    public static final RegistryObject<Block> HUGE_LILY_PAD_TINTED          = registerBlock("huge_lily_pad_tinted", 405);
    public static final RegistryObject<Block> WATER_GRASS_TINTED            = registerBlock("water_grass_tinted", 406);
    public static final RegistryObject<Block> TINY_CACTUS_TINTED            = registerBlock("tiny_cactus_tinted", 407);
    public static final RegistryObject<Block> WATERLILY_TINTED              = registerBlock("waterlily_tinted", 408);
    public static final RegistryObject<Block> LEAF_PILE_TINTED              = registerBlock("leaf_pile_tinted", 409);

    public static final RegistryObject<Block> WILLOW_VINE_TINTED            = registerBlock("willow_vine_tinted", 37);


    private static RegistryObject<Block> registerBlock(String name, int type) {

        RegistryObject<Block> toReturn;

        if (type == 32) {
            name = name.concat("_leaves_block");
            toReturn = BLOCKS.register(name, TintedLeavesBlock::new);
            addToLeavesList(toReturn);
        } else if (type == 322) { //32.2
            name = name.concat("_leaves_block");
            toReturn = BLOCKS.register(name, TintedWillowLeavesBlock::new);
            addToLeavesList(toReturn);
        } else if (type == 37) {
            toReturn = BLOCKS.register(name, TintedVine::new);
            addToPlantsList(toReturn);
        } else if (type == 400) {
            toReturn = BLOCKS.register(name, TintedFoliageBOP::new);
            addToPlantsList(toReturn);
        } else if (type == 401) {
            toReturn = BLOCKS.register(name, TintedCloverBOP::new);
            addToPlantsList(toReturn);
        } else if (type == 402) {
            toReturn = BLOCKS.register(name, () -> new ParentTintedHorizontalDirectionalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).ignitedByLava().instabreak().sound(SoundType.PINK_PETALS)));
            addToPlantsList(toReturn);
        } else if (type == 403) {
            toReturn = BLOCKS.register(name, TintedHighGrassBOP::new);
            addToPlantsList(toReturn);
        } else if (type == 404) {
            toReturn = BLOCKS.register(name, TintedHighGrassPlantBOP::new);
            addToPlantsList(toReturn);
        } else if (type == 405) {
            toReturn = BLOCKS.register(name, TintedHugeLilyPadBOP::new);
            addToPlantsList(toReturn);
        } else if (type == 406) {
            toReturn = BLOCKS.register(name, TintedDoubleWaterPlantBOP::new);
            addToPlantsList(toReturn);
        } else if (type == 407) {
            toReturn = BLOCKS.register(name, TintedTinyCactusBOP::new);
            addToPlantsList(toReturn);
        } else if (type == 408) {
            toReturn = BLOCKS.register(name, TintedWaterlilyBOP::new);
            addToPlantsList(toReturn);
        } else if (type == 409) {
            toReturn = BLOCKS.register(name, TintedLeafPileBOP::new);
            addToPlantsList(toReturn);
        }

        else
            toReturn = null;

        if (type == 405 || type == 408)
            registerBlockItemLily(name, toReturn);
        else
            registerModBlockItem(name, toReturn);
        return toReturn;
    }

    private static void addToLeavesList (RegistryObject<Block> toReturn) {
        allLeavesRegistryBlocksList.add(toReturn);
        tintedBOPleavesRegistryBlocksList.add(toReturn);

    }
    private static void addToPlantsList (RegistryObject<Block> toReturn) {
        plantRegistryBlockList.add(toReturn);
        tintedBOPplantRegistryBlockList.add(toReturn);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS_BOP.register(eventBus);
    }

}
