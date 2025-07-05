package net.night.grasses.init;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.night.grasses.Grasses;
import net.night.grasses.advancement.criteriaTriggers.LogsDestroyedTrigger;

@Mod.EventBusSubscriber(modid = Grasses.MOD_ID)
public class AdvancementRegister {

    public static final LogsDestroyedTrigger LOGS_DESTROYED_TRIGGER = CriteriaTriggers.register(new LogsDestroyedTrigger(new ResourceLocation(Grasses.MOD_ID,"logs_destroyed")));
}
