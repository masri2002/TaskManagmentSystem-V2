package com.masri.TaskMangamentSystem.entity;

/**
 * Enumeration representing task priorities.
 * @author ahmad almasri
 */
public enum Priority {
    HIGH(3),    // High priority
    MEDIUM(2),  // Medium priority
    LOW(1);     // Low priority

    private final int priority;

    /**
     * Constructor for Priority enum.
     * @param priority The integer value representing the priority level.
     */
    Priority(int priority) {
        this.priority = priority;
    }

    /**
     * Retrieves the integer value of the priority.
     * @return The integer value representing the priority level.
     */
    public int getPriority() {
        return priority;
    }
  
}
