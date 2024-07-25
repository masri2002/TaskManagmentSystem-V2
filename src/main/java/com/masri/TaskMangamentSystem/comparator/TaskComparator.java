package com.masri.TaskMangamentSystem.comparator;

import com.masri.TaskMangamentSystem.entity.Task;

import java.util.Comparator;

/**
 * Comparator implementation for comparing Task objects based on priority and date.
 * Tasks are sorted by priority in descending order (higher priority first), and by due date ascending.
 * Equality is checked based on title and description.
 */
public class TaskComparator implements Comparator<Task> {

    /**
     * Compares two Task objects based on priority and due date.
     *
     * @param o1 The first Task object to compare.
     * @param o2 The second Task object to compare.
     * @return A negative integer, zero, or a positive integer as the first argument is less than, equal to,
     * or greater than the second, based on priority and due date comparison.
     */
    @Override
    public int compare(Task o1, Task o2) {
        // First compare by priority
        int priorityComparison = Integer.compare(o1.getPriority().getPriority(), o2.getPriority().getPriority());

        if (priorityComparison == 0) {
            // If priorities are equal, compare by due date ascending
            int dueDateComparison = o1.getDueDate().compareTo(o2.getDueDate());

            if (dueDateComparison == 0) {
                // If due dates are also equal, check equality based on title and description
                int titleComparison = o1.getTitle().compareTo(o2.getTitle());
                if (titleComparison == 0) {
                    return o1.getDescription().compareTo(o2.getDescription());
                }
                return titleComparison;
            }

            return dueDateComparison;
        }

        // Return in descending order of priority (higher priority first)
        return (-1 * priorityComparison);
    }
}
