package com.codepath.simpletodo;

import com.codepath.simpletodo.data.TodoItem;

import java.util.Comparator;

/**
 * Created by mcvjetinovic on 11/17/15.
 */
public class DueDateComparator implements Comparator<TodoItem> {
    @Override
    public int compare(TodoItem lhs, TodoItem rhs) {
        System.out.println("COmpare data:" + lhs.dueDate +" rhs:" +rhs.dueDate);
        if (lhs.dueDate.before(rhs.dueDate)) {
            return 1;
        }
        else if (lhs.dueDate.after(rhs.dueDate)) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
