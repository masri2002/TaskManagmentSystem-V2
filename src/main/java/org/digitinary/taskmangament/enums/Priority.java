package org.digitinary.taskmangament.enums;

/**
 * Enumeration representing task priorities.
 * @author ahmad almasri
 */
public enum Priority {
    HIGH(3),
    MEDIUM(2),
    LOW(1);

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
