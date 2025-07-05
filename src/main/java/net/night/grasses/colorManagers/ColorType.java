package net.night.grasses.colorManagers;

import net.minecraft.util.StringRepresentable;

public enum ColorType implements StringRepresentable {
    BADLANDS("badlands"),
    BIRCH("birch"),
    CHERRY("cherry"),
    DARK("dark"),
    DESERT("desert"),
    DRIPSTONEBE("dripstonebe"),
    JUNGLE("jungle"),
    LUSHBE("lushbe"),
    MANGROVE("mangrove"),
    MEADOW("meadow"),
    MUSHROOM("mushroom"),
    OCEAN("ocean"),
    PLAINS("plains"),
    PINETAIGA("pinetaiga"),
    SNOWYBEACH("snowybeach"),
    SNOWYPLAINS("snowyplains"),
    SPARSEJUNGLE("sparsejungle"),
    STONYPEAKS("stonypeaks"),
    SWAMP("swamp"),
    TAIGA("taiga"),
    WINDSWEPT("windswept"),
    WHITE("white"),
    RED("red"),
    ORANGE("orange"),
    PINK("pink"),
    YELLOW("yellow"),
    LIME("lime"),
    GREEN("green"),
    LIGHTBLUE("lightblue"),
    CYAN("cyan"),
    BLUE("blue"),
    MAGENTA("magenta"),
    PURPLE("purple"),
    BROWN("brown"),
    GRAY("gray"),
    LIGHTGRAY("lightgray"),
    BLACK("black"),
    QUARTZ("quartz"),
    COPPER("copper"),
    IRON("iron"),
    GOLD("gold"),
    DIAMOND("diamond"),
    EMERALD("emerald"),
    NETHERITE("netherite"),
    REDSTONE("redstone"),
    AMETHYST("amethyst"),
    LAPIS("lapis"),
    SWAMPCOLD("swampcold"),
    FOREST("forest"),
    LIGHTLIME("lightlime"),
    DARKGREEN("darkgreen"),
    XMAS("xmas"),
    MALACHITE("malachite"),
    GLASSBOTTLE("glassbottle"),
    LIVINGGREEN("livinggreen"),
    BETTERCHERRY("bettercherry");
    //STANDARD("standard"),
    //COLD("cold"),
    //FROZEN("frozen"),
    //LUKEWARM("lukewarm"),
    //WARM("warm");


    private final String name;

    ColorType(String pName) {
        this.name = pName;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}



