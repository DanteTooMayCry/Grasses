package net.night.grasses.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.management.ManagementFactory;

@OnlyIn(Dist.CLIENT)
@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Unique
    private static boolean launchTime$isShowAlready;

    @Inject(method = "init", at = @At("RETURN"))
    public void inject(CallbackInfo ci) {
        if (launchTime$isShowAlready) {
            return;
        }

        launchTime$isShowAlready = true;
        double timeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        double timeSec = timeMillis/1000;

        int min = (int) Math.floor(timeSec/60);
        int sec = (int) Math.floor(timeSec-(min*60));

        Component Heading = Component.translatable("launch_time.heading");
        Component LaunchTime = Component.translatable("launch_time.min", min).append(Component.translatable("launch_time.sec", sec));

        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            mc.getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT, Heading, LaunchTime));
        });
    }
}
