package com.masri.TaskMangamentSystem.comparator;

import com.masri.TaskMangamentSystem.entity.Project;

import java.util.Comparator;

public class ProjectComparator implements Comparator<Project> {

    @Override
    public int compare(Project project1, Project project2) {
        return project1.getTitle().compareTo(project2.getTitle());
    }
}
