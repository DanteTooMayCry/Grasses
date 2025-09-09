package net.night.grasses.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.night.grasses.enums.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.item.AutomaticPrunerItem;
import net.night.grasses.item.DyeingBoneMealItem;
import net.night.grasses.item.DyeingItem;
import net.night.grasses.item.DyeingTool;
import org.jetbrains.annotations.NotNull;

import static net.night.grasses.data.ModData.colorTypeList;
import static net.night.grasses.data.ModMethods.setColorOnItemStack;
import static net.night.grasses.data.ModMethods.setEnchantmentBoolean;
import static net.night.grasses.init.BlocksRegister.DYEING_STATION;

public class AddItemModifier extends LootModifier {

    public static final Codec<AddItemModifier> CODEC = RecordCodecBuilder.create(
            inst -> LootModifier.codecStart(inst)
                    .and(inst.group(
                            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(m -> m.item),
                            Codec.INT.fieldOf("minItems").forGetter(m -> m.minItems),
                            Codec.INT.fieldOf("maxItems").forGetter(m -> m.maxItems),
                            Codec.DOUBLE.fieldOf("probability").forGetter(m -> m.probability)
                    ))
                    .apply(inst, AddItemModifier::new)
    );

    private final Item item;
    private final int minItems;
    private final int maxItems;
    private final double probability;


    public AddItemModifier(LootItemCondition[] conditionsIn, Item item, int minItems, int maxItems, double probability) {
        super(conditionsIn);
        this.item = item;
        this.minItems = minItems;
        this.maxItems = maxItems;
        this.probability = probability;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {

        if (item instanceof DyeingTool && GrassesConfig.COMMON_CONFIG.ALLOW_FIND_DYEING_TOOL.get() || item instanceof DyeingBoneMealItem && GrassesConfig.COMMON_CONFIG.ALLOW_FIND_DYEING_BONEMEAL.get() ||
                item instanceof DyeingItem && GrassesConfig.COMMON_CONFIG.ALLOW_FIND_DYE.get() ||
                item instanceof AutomaticPrunerItem && GrassesConfig.COMMON_CONFIG.ALLOW_FIND_AUTO_PRUNER.get() || item.equals(DYEING_STATION.get().asItem()) && GrassesConfig.COMMON_CONFIG.ALLOW_FIND_DYEING_STATION.get()) {

            int randomCount = context.getRandom().nextInt((maxItems - minItems) + 1) + minItems;
            if (context.getRandom().nextFloat() <= probability) {

                int random = RandomSource.create().nextInt(colorTypeList.size());
                ItemStack itemStack = new ItemStack(item, randomCount);

                if (item instanceof DyeingTool || item instanceof DyeingBoneMealItem || item instanceof DyeingItem) {
                    ColorType colorType = colorTypeList.get(random);
                    setColorOnItemStack(itemStack, colorType);
                }
                if (item instanceof DyeingTool || item instanceof AutomaticPrunerItem) {

                    if (item instanceof AutomaticPrunerItem)
                        setEnchantmentBoolean(itemStack);

                    int durability = item.getMaxDamage(itemStack);
                    float damage = (float) RandomSource.create().nextInt(50) / 100;
                    itemStack.setDamageValue((int) Math.floor(durability*damage));
                }
                generatedLoot.add(itemStack);
            }
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
