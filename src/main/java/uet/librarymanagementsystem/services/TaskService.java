package uet.librarymanagementsystem.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskService {
    private final int THREAD_POOL_SIZE;
    private final ExecutorService executor;

    public TaskService() {
        // Số luồng tự động bằng số lõi CPU, có thể tùy chỉnh thêm
        this.THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        System.out.println("Thread pool size: " + THREAD_POOL_SIZE);
    }

    // Phương thức chạy một tác vụ
    public void runTask(Runnable task) {
        executor.submit(task);
    }

    // Đóng thread pool khi không sử dụng nữa
    public void shutdown() {
        executor.shutdown();
    }
}
