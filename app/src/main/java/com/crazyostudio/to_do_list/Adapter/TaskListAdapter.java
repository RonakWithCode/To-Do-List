package com.crazyostudio.to_do_list.Adapter;

//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import com.crazyostudio.to_do_list.Activity.TaskDetailsActivity;
//import com.crazyostudio.to_do_list.Model.TaskModel;
//import com.crazyostudio.to_do_list.Model.onCheck;
//import com.crazyostudio.to_do_list.R;
//import com.crazyostudio.to_do_list.databinding.TaskLayoutBinding;
//
//import java.util.List;
//
public class TaskListAdapter {

}
//    private final Context context;
//    onCheck onCheck;
//    private int mResource;
//
//    public TaskListAdapter(Context Context, int resource, List<TaskModel> tasks,onCheck OnCheck) {
//        super(Context, resource, tasks);
//        context = Context;
//        this.onCheck = OnCheck;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        TaskLayoutBinding binding;
//
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(context);
//            binding = TaskLayoutBinding.inflate(inflater, parent, false);
//            convertView = binding.getRoot();
//            convertView.setTag(binding);
//        } else {
//            binding = (TaskLayoutBinding) convertView.getTag();
//        }
//
//        TaskModel task = getItem(position);
//        if (task != null) {
////            binding.textViewTask.setText(task.getTaskName());
//            binding.pin.setImageResource(R.drawable.ic_baseline_push_pin_24_blue);
//            binding.TaskCheckbox.setChecked(task.getTaskCheck());
//            binding.TaskCheckbox.setText(task.getTask());
//            binding.TaskCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> onCheck.onBtnListClickCheckBox(task,isChecked));
//            binding.delete.setOnClickListener(view -> onCheck.onBtnListClickDelete(task));
//            binding.pin.setOnClickListener(view -> {
//                binding.pin.setImageResource(R.drawable.ic_baseline_push_pin_24);
//                onCheck.onBtnListClickPinRemove(task,false);
//                notifyDataSetChanged();
//            });
//
//            binding.TaskLayout.setOnClickListener(view->{
//                Intent intent = new Intent(context, TaskDetailsActivity.class);
//                intent.putExtra("index",task.getId()+"");
////                System.out.println("index"+task.getId());
//                intent.putExtra("Task",task.getTask());
//                intent.putExtra("TaskCheck",task.getTaskCheck());
//                intent.putExtra("TaskNotes",task.getNotes());
//                intent.putExtra("Sub_Task",task.getSub_Task());
//                intent.putExtra("Sub_TaskCheck",task.getSub_check());
//                intent.putExtra("Task_Category",task.getCategory());
//                context.startActivity(intent);
//            });
//        }
//
//        return convertView;
//    }
//}