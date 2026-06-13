package com.example.itemrenderfix.util;

import java.util.ArrayList;
import java.util.List;

public class TickScheduler {
    private static final List<Runnable> tasks = new ArrayList<>();

    public static void onClientTick() {
        if (!tasks.isEmpty()) {
            List<Runnable> currentTasks = new ArrayList<>(tasks);
            tasks.clear();
            for (Runnable task : currentTasks) {
                task.run();
            }
        }
    }
}
