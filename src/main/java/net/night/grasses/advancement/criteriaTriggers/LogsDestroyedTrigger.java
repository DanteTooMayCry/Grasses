package net.night.grasses.advancement.criteriaTriggers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.night.grasses.init.AdvancementRegister;


public class LogsDestroyedTrigger extends SimpleCriterionTrigger<LogsDestroyedTrigger.TriggerInstance> {

    final ResourceLocation id;

    public LogsDestroyedTrigger(ResourceLocation pId) {
        this.id = pId;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pDeserializationContext) {
        ContextAwarePredicate $$3 = ContextAwarePredicate.fromElement("location", pDeserializationContext, pJson.get("location"), LootContextParamSets.ADVANCEMENT_LOCATION);
        if ($$3 == null) {
            throw new JsonParseException("Failed to parse 'location' field");
        } else {
            return new TriggerInstance(this.id, pPredicate, $$3);
        }
    }

    public void trigger(ServerPlayer pPlayer, BlockPos pPos, ItemStack pStack) {
        ServerLevel $$3 = pPlayer.serverLevel();
        BlockState $$4 = $$3.getBlockState(pPos);
        LootParams $$5 = (new LootParams.Builder($$3)).withParameter(LootContextParams.ORIGIN, pPos.getCenter()).withParameter(LootContextParams.THIS_ENTITY, pPlayer).withParameter(LootContextParams.BLOCK_STATE, $$4).withParameter(LootContextParams.TOOL, pStack).create(LootContextParamSets.ADVANCEMENT_LOCATION);
        LootContext $$6 = (new LootContext.Builder($$5)).create((ResourceLocation)null);
        this.trigger(pPlayer, (p_286596_) -> {
            return p_286596_.matches($$6);
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ContextAwarePredicate location;

        public TriggerInstance(ResourceLocation pCriterion, ContextAwarePredicate pPlayer, ContextAwarePredicate pLocation) {
            super(pCriterion, pPlayer);
            this.location = pLocation;
        }

        private static TriggerInstance itemUsedOnLocation(LocationPredicate.Builder pLocationPredicate, ItemPredicate.Builder pItemPredicate, ResourceLocation pCriterion) {
            ContextAwarePredicate $$3 = ContextAwarePredicate.create(new LootItemCondition[]{LocationCheck.checkLocation(pLocationPredicate).build(), MatchTool.toolMatches(pItemPredicate).build()});
            return new TriggerInstance(pCriterion, ContextAwarePredicate.ANY, $$3);
        }

        public static TriggerInstance itemUsedOnBlock(LocationPredicate.Builder pLocationPredicate, ItemPredicate.Builder pItemPredicate) {
            return itemUsedOnLocation(pLocationPredicate, pItemPredicate, AdvancementRegister.LOGS_DESTROYED_TRIGGER.id);
        }

        public boolean matches(LootContext pContext) {
            return this.location.matches(pContext);
        }

        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject $$1 = super.serializeToJson(pConditions);
            $$1.add("location", this.location.toJson(pConditions));
            return $$1;
        }
    }
}
