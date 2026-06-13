package com.example.itemrenderfix.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class InstantSwapExecutor {

    public static void executeInstantSwap(Minecraft client) {
        if (client.player == null || client.gameMode == null) return;

        Inventory inventory = client.player.getInventory();
        // Use the official public method getter to avoid the private access error
        int originalSlot = inventory.selected; 
        int targetSlot = -1;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem().getDescriptionId().contains("spear")) {
                targetSlot = i;
                break;
            }
        }

        if (targetSlot == -1 || targetSlot == originalSlot) return;

        // Synchronize via network packet sequences securely
        inventory.selected = targetSlot;
        client.player.connection.send(new ServerboundSetCarriedItemPacket(targetSlot));

        // Execute the 1-tick lunge simulation frame
        client.gameMode.attack(client.player, client.player);
        client.player.swing(InteractionHand.MAIN_HAND);

        // Revert hotbar alignment back safely
        inventory.selected = originalSlot;
        client.player.connection.send(new ServerboundSetCarriedItemPacket(originalSlot));
    }
}
