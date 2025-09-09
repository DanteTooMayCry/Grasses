package net.night.grasses.datagen.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.fml.ModList;

public class IsModLoaded implements LootItemCondition {

    private final boolean exists;
    private final String modID;

    public IsModLoaded(String modID) {
        this.exists = ModList.get().isLoaded(modID);
        this.modID = modID;
    }

    @Override
    public LootItemConditionType getType() {
        return LootItemConditions.IS_MOD_LOADED.get();
    }

    @Override
    public boolean test(LootContext context) {
        return this.exists;
    }

    public static Builder builder(String modid) {
        return () -> new IsModLoaded(modid);
    }

    public static class ConditionSerializer implements Serializer<IsModLoaded> {
        @Override
        public void serialize(JsonObject json, IsModLoaded instance, JsonSerializationContext ctx) {
            json.addProperty("modid", instance.modID);
        }

        @Override
        public IsModLoaded deserialize(JsonObject json, JsonDeserializationContext ctx) {
            return new IsModLoaded(GsonHelper.getAsString(json, "modid"));
        }
    }
}
