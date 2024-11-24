package uet.librarymanagementsystem.services;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskService {
    private static TaskService instance; // Instance duy nhất của TaskService
    private final ExecutorService executor;

    // Constructor private để ngăn khởi tạo từ bên ngoài
    private TaskService() {
        int threadPoolSize = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
        System.out.println("Thread pool size: " + threadPoolSize);
    }

    // Truy cập instance duy nhất của TaskService
    public static synchronized TaskService getInstance() {
        if (instance == null) {
            instance = new TaskService();
        }
        return instance;
    }

    // Chạy một Callable và trả về Future
    public <T> Future<T> runTask(Callable<T> task) {
        return executor.submit(task);
    }

    // Chạy một Runnable
    public void runTask(Runnable task) {
        executor.execute(task); // Sử dụng execute thay vì submit cho Runnable
    }

    // Đóng thread pool
    public void shutdown() {
        if (!executor.isShutdown()) {
            executor.shutdown();
            System.out.println("TaskService has been shut down.");
        }
    }

    // Kiểm tra trạng thái thread pool
    public boolean isShutdown() {
        return executor.isShutdown();
    }
}
