package com.crazyostudio.to_do_list.Model;


public class Task_Sub_Model {
    String sub_task;
    boolean IsTrue;

    public Task_Sub_Model() {
    }

    public Task_Sub_Model(String Sub_task, boolean isTrue) {
        sub_task = Sub_task;
        IsTrue = isTrue;
    }

    public String getSub_task() {
        return sub_task;
    }

    public void setSub_task(String notes) {
        sub_task = notes;
    }

    public boolean isTrue() {
        return IsTrue;
    }

    public void setTrue(boolean aTrue) {
        IsTrue = aTrue;
    }
}
