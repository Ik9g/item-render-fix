package com.example.itemrenderfix;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import com.example.itemrenderfix.util.TickScheduler;

public class ItemRenderFixMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            TickScheduler.onClientTick();
        });
    }
}
