package net.night.grasses.util;

import java.util.ArrayList;
import java.util.List;

public class DropSpawnScheduler {

    private record TickTask(Runnable task) {
    }

    private static final List<TickTask> scheduledTasks = new ArrayList<>();

    public static void scheduleDropTask(Runnable task) {
        synchronized (scheduledTasks) {
            scheduledTasks.add(new TickTask(task));
        }
    }

    public static void tick() {
        List<TickTask> toExecute;
        synchronized (scheduledTasks) {
            toExecute = new ArrayList<>(scheduledTasks);
            scheduledTasks.clear();
        }
        for (TickTask task : toExecute) {
            task.task.run();
        }
    }
}
