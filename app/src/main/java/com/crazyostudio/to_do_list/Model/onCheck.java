package com.crazyostudio.to_do_list.Model;

public interface onCheck {

    void onBtnClickPin(TaskModel taskModel,boolean change);
    void onBtnClickDelete(TaskModel taskModel);
    void onBtnClickCheckBox(TaskModel taskModel,boolean IsCheck);

}
