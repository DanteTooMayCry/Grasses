package net.night.grasses.datagen.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.fml.ModList;

public class IsModLoaded implements LootItemCondition {

    //public static final MapCodec<IsModLoaded> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
    //      Codec.STRING.fieldOf("mod_id").forGetter(o -> o.modID)
    //).apply(instance, IsModLoaded::new));

    public static final Codec<IsModLoaded> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("mod_id").forGetter(m -> m.modID)
    ).apply(instance, IsModLoaded::new));


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
}
