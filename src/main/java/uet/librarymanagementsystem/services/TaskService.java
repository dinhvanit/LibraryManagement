package uet.librarymanagementsystem.services;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskService {
    private final int THREAD_POOL_SIZE;
    private final ExecutorService executor;

    public TaskService() {
        this.THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        System.out.println("Thread pool size: " + THREAD_POOL_SIZE);
    }

    // Phương thức chạy một Callable và trả về Future
    public <T> Future<T> runTask(Callable<T> task) {
        return executor.submit(task); // Trả về Future từ executor
    }

    public void runTask(Runnable task) {
        executor.submit(task);
    }

    // Đóng thread pool
    public void shutdown() {
        executor.shutdown();
    }
}

