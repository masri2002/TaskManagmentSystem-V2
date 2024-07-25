package com.masri.TaskMangamentSystem.comparator;

import com.masri.TaskMangamentSystem.entity.User;

import java.util.Comparator;

/**
 * Comparator to order users by name.
 */
public class UserComparator implements Comparator<User> {

    @Override
    public int compare(User user1, User user2) {
        return user1.getName().compareTo(user2.getName());
    }
}