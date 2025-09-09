package net.night.grasses.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.night.grasses.Grasses;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DyeingStationRecipe implements Recipe<SimpleContainer> {
    private final List<Ingredient> inputItems;
    private final ItemStack output;

    public DyeingStationRecipe(List<Ingredient> inputItems, ItemStack output) {
        this.inputItems = inputItems;
        this.output = output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {

        NonNullList<Ingredient> list = NonNullList.createWithCapacity(this.inputItems.size());
        list.addAll(inputItems);
        return list;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if(level.isClientSide()) {
            return false;
        }
        return inputItems.get(0).test(container.getItem(1)) && inputItems.get(1).test(container.getItem(2)) && inputItems.get(2).test(container.getItem(3));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public RecipeType<?> getType() {

        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<DyeingStationRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "dyeing";
    }

    public static class Serializer implements RecipeSerializer<DyeingStationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Grasses.MOD_ID, "dyeing");

        public static final Codec<DyeingStationRecipe> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                validateAmount(Ingredient.CODEC_NONEMPTY, 9).fieldOf("ingredients").forGetter(DyeingStationRecipe::getIngredients),
                ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("output").forGetter(r -> r.output)
        ).apply(inst, DyeingStationRecipe::new));

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int max) {
            return ExtraCodecs.validate(ExtraCodecs.validate(
                    delegate.listOf(), list -> list.size() > max ? DataResult.error(() -> "Recipe has too many ingredients!") : DataResult.success(list)
            ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredients!") : DataResult.success(list));
        }

        @Override
        public Codec<DyeingStationRecipe> codec() {
            return CODEC;
        }

        @Override
        public @Nullable DyeingStationRecipe fromNetwork(FriendlyByteBuf friendlyByteBuf) {
            NonNullList<Ingredient> ingredients = NonNullList.withSize(friendlyByteBuf.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < ingredients.size(); i++) {
                ingredients.set(i, Ingredient.fromNetwork(friendlyByteBuf));
            }

            ItemStack output = friendlyByteBuf.readItem();
            return new DyeingStationRecipe(ingredients, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, DyeingStationRecipe colorizingStationRecipe) {
            friendlyByteBuf.writeInt(colorizingStationRecipe.getIngredients().size());

            for (Ingredient ingredient : colorizingStationRecipe.getIngredients()) {
                ingredient.toNetwork(friendlyByteBuf);
            }

            friendlyByteBuf.writeItem(colorizingStationRecipe.getResultItem(null));
        }
    }
}
