package edu.gatech.thundercats.watermap.util;

import javafx.concurrent.Task;

import java.util.function.Consumer;

/**
 * Created by Matthieu Gay-Bellile on 10/31/16.
 */
public class TaskHelper {
    /**
     * Runs given task.
     *
     * @param task        Task given to run
     * @param onSucceeded What to do when task succeeds
     * @param <T>         Made Generic
     */
    public static <T> void run(final Task<T> task,
                               final Consumer<Task<T>> onSucceeded) {
        run(task, onSucceeded, null);
    }

    /**
     * Runs given task.
     *
     * @param task        Task given to run
     * @param onSucceeded What to do when task succeeds
     * @param onFailed    What to do when task fails
     * @param <T>         Made Generic
     */
    public static <T> void run(final Task<T> task,
                               final Consumer<Task<T>> onSucceeded,
                               final Consumer<Task<T>> onFailed) {
        run(task, onSucceeded, onFailed, null);
    }

    /**
     * Runs the given Task.
     *
     * @param task        Task given to run
     * @param onSucceeded What to do when task succeeds
     * @param onFailed    What to do when task fails
     * @param onCancelled What to do when task is cancelled
     * @param <T>         Made Generic
     */
    public static <T> void run(final Task<T> task,
                               final Consumer<Task<T>> onSucceeded,
                               final Consumer<Task<T>> onFailed,
                               final Consumer<Task<T>> onCancelled) {
        task.setOnSucceeded(workerStateEvent -> onSucceeded.accept(task));
        task.setOnFailed(workerStateEvent -> onFailed.accept(task));
        task.setOnCancelled(workerStateEvent -> onCancelled.accept(task));
        new Thread(task).start();
    }
}
