package net.night.grasses.datagen.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

public class HasInstanceOfShearsCondition implements LootItemCondition {

    public HasInstanceOfShearsCondition() {
    }

    @Override
    public @NotNull LootItemConditionType getType() {
        return LootItemConditions.HAS_INSTANCE_OF_SHEARS.get();
    }

    @Override
    public boolean test(LootContext context) {
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        return tool != null && tool.getItem() instanceof ShearsItem;
    }

    public static LootItemCondition.Builder builder() {
        return HasInstanceOfShearsCondition::new;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<HasInstanceOfShearsCondition> {
        @Override
        public void serialize(JsonObject json, HasInstanceOfShearsCondition condition, JsonSerializationContext context) {
        }

        @Override
        public HasInstanceOfShearsCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            return new HasInstanceOfShearsCondition();
        }
    }
}
