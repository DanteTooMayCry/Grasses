package net.night.grasses.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.night.grasses.Grasses;
import net.night.grasses.util.ModTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.item.Items.SHEARS;
import static net.night.grasses.init.ItemsRegister.DIAMOND_AUTO_PRUNER;
import static net.night.grasses.init.ItemsRegister.NETHERITE_AUTO_PRUNER;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, Grasses.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(ModTags.Items.SHEARS)
                .add(SHEARS)
                .add(DIAMOND_AUTO_PRUNER.get())
                .add(NETHERITE_AUTO_PRUNER.get());

        this.tag(ModTags.Items.PRUNERS)
                .add(DIAMOND_AUTO_PRUNER.get())
                .add(NETHERITE_AUTO_PRUNER.get());
    }
}
