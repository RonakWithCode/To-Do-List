package com.crazyostudio.to_do_list.Adapter;

import com.crazyostudio.to_do_list.Model.TaskModel;

import java.util.Comparator;

public class PinnedComparator implements Comparator<TaskModel> {

    @Override
    public int compare(TaskModel item1, TaskModel item2) {
        if (item1.isPin() && !item2.isPin()) {
            return -1; // item1 should come before item2
        } else if (!item1.isPin() && item2.isPin()) {
            return 1;  // item2 should come before item1
        } else {
            return 0;  // No change in order
        }
    }
}
