package net.night.grasses.datagen.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

public class HasInstanceOfShearsCondition implements LootItemCondition {

    public static final Codec<HasInstanceOfShearsCondition> CODEC = Codec.unit(HasInstanceOfShearsCondition::new);

    public HasInstanceOfShearsCondition() {
    }

    @Override
    public LootItemConditionType getType() {
        return LootItemConditions.HAS_INSTANCE_OF_SHEARS.get();
    }

    @Override
    public boolean test(LootContext context) {
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        return tool != null && tool.getItem() instanceof ShearsItem;
    }

    public static Builder builder() {
        return HasInstanceOfShearsCondition::new;
    }
}
