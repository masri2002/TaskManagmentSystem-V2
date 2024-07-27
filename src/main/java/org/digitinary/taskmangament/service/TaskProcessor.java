package org.digitinary.taskmangament.service;

import jakarta.transaction.Transactional;
import org.digitinary.taskmangament.entity.Task;
import org.digitinary.taskmangament.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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
    private final Object lock = new Object();
    private final SecureRandom random = new SecureRandom();
    private final Logger logger = LoggerFactory.getLogger(TaskProcessor.class);

    private final TaskService taskService;

    @Autowired
    public TaskProcessor(TaskService taskService) {
        this.taskService = taskService;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void submitTask(Task task) {
        executorService.submit(() -> processTask(task));
    }

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
        } catch (Exception e) {
            logger.error("Error processing task: {}", task, e);
        }
    }

    @PreDestroy
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
    public void processAllTasks(List<Task> tasks){
        for (Task task : tasks){
            if(task.getStatus().equals(Status.COMPLETED)){
                continue;
            }else{
                submitTask(task);
            }
        }
    }
}
