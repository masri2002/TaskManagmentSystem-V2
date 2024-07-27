package com.masri.TaskMangamentSystem.service;

import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.entity.Status;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import jakarta.annotation.PreDestroy;

/**
 * Service class that manages task processing asynchronously using an ExecutorService.
 * <p>
 * This class uses a fixed thread pool to process tasks concurrently, updating their status and simulating processing time.
 * </p>
 * @author ahmad almasri
 */
@Service
public class TaskProcessor {

    private final ExecutorService executorService;
    private final Object lock = new Object(); // Lock object for synchronization
    private final SecureRandom random = new SecureRandom(); // Secure random number generator for simulating processing
    private final Logger logger = LoggerFactory.getLogger(TaskProcessor.class);

    private final TaskService taskService;

    /**
     * Constructs a TaskProcessor with a fixed thread pool of 10 threads.
     *
     * @param taskService The TaskService used to update task statuses in the database.
     */
    @Autowired
    public TaskProcessor(TaskService taskService) {
        this.taskService = taskService;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    /**
     * Submits a task to be processed asynchronously.
     *
     * @param task The task to process.
     */
    public void submitTask(Task task) {
        executorService.submit(() -> processTask(task));
    }

    /**
     * Processes a task synchronously. Updates task status from PENDING to IN_PROGRESS,
     * and from IN_PROGRESS to COMPLETED after a simulated processing time.
     * Updates the task in the database after each status change.
     *
     * @param task The task to process.
     *
     */
        private void processTask(Task task) {
            try {
                synchronized (lock) {
                    logger.info("Processing task: {}", task);
                    if (task.getStatus() == Status.PENDING) {
                        Thread.sleep(2000); // Simulate processing delay
                        task.setStatus(Status.IN_PROGRESS);
                        task.setCreationDate(task.getCreationDate().plusDays(random.nextInt(2)));
                        taskService.updateTask(task);
                        logger.info("Task status updated to IN_PROGRESS: {}", task);
                    } else if (task.getStatus() == Status.IN_PROGRESS) {
                        Thread.sleep(10000); // Simulate longer processing delay
                        task.setStatus(Status.COMPLETED);
                        task.setCreationDate(task.getDueDate().minusDays(random.nextInt(4)));
                        taskService.updateTask(task);
                        logger.info("Task status updated to COMPLETED: {}", task);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Task processing interrupted for task: {}", task);
            }
        }

    /**
     * Processes a list of tasks asynchronously.
     * Skips tasks that are already marked as COMPLETED.
     *
     * @param tasks The list of tasks to process.
     */
    public void processAllTasks(List<Task> tasks) {
        for (Task task : tasks) {
            if (task.getStatus().equals(Status.COMPLETED)) {
                continue; // Skip already completed tasks
            }
            submitTask(task);
        }
    }

    /**
     * Shuts down the TaskProcessor gracefully.
     * Waits for up to 60 seconds for tasks to complete before forcing shutdown.
     * If the process takes more than 60 seconds, it will shut down.
     */
    @PreDestroy // Method invoked during bean destruction
    public void shutdown() {
        System.out.println("Shutting down TaskProcessor...");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.warn("ExecutorService did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
