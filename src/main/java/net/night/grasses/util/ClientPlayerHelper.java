package net.night.grasses.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;

public class ClientPlayerHelper {

    public static @Nullable LocalPlayer getPlayer() {
        return Minecraft.getInstance().player;
    }

    public static ItemStack getMainHandItem() {
        LocalPlayer player = getPlayer();
        if (player != null) {
            return player.getMainHandItem();
        } else {
            return ItemStack.EMPTY;
        }
    }
    public static void sendClientMessage(net.minecraft.network.chat.Component message, boolean actionBar) {
        LocalPlayer player = getPlayer();
        if (player != null) {
            player.displayClientMessage(message, actionBar);
        } else {
            //System.out.println("[ClientPlayerHelper] Warning: player is null, message not sent: " + message.getString());
        }
    }
}
