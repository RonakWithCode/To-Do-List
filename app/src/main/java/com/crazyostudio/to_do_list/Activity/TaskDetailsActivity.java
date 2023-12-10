package com.crazyostudio.to_do_list.Activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazyostudio.to_do_list.Adapter.ImageAttachmentAdapter;
import com.crazyostudio.to_do_list.Adapter.Task_Sub_Adapter;
import com.crazyostudio.to_do_list.DAO.CategoryDatabaseHelper;
import com.crazyostudio.to_do_list.DAO.TaskDatabaseHelper;
import com.crazyostudio.to_do_list.Model.CategoryModel;
import com.crazyostudio.to_do_list.Model.TaskModel;
import com.crazyostudio.to_do_list.Model.Task_Sub_Model;
import com.crazyostudio.to_do_list.R;
import com.crazyostudio.to_do_list.Receiver.TaskAlarmReceiver;
import com.crazyostudio.to_do_list.databinding.ActivityTaskDetailsBinding;
import com.crazyostudio.to_do_list.databinding.ChouseImageTypeBoxBinding;
import com.crazyostudio.to_do_list.databinding.DueDateCalendarBoxBinding;
import com.crazyostudio.to_do_list.databinding.TimeorreminderboxBinding;
import com.crazyostudio.to_do_list.interface_class.ImageAttachmentClick;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TaskDetailsActivity extends AppCompatActivity implements ImageAttachmentClick {
    private static final int ONEIMAGE = 1;
    private static final int DOCUMENTED = 2;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 500;
//    private static final int REQUEST_PERMISSIONS = 501;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 501;

    private ArrayList<String> imagePaths;


    ActivityTaskDetailsBinding binding;
    //    ArrayList<Boolean> subTaskCheckList = new ArrayList<>();
    int id;
    Calendar calendar;
    Date mDate;
    ArrayList<String> sub_task;
    ArrayList<Boolean> sub_task_check;
    Task_Sub_Adapter task_sub_adapter;
    ArrayList<Task_Sub_Model> task_sub_models;
    TaskDatabaseHelper taskDatabaseHelper;
    ArrayList<String> images;
    ImageAttachmentAdapter imageAdapters;



    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//      get Data from room database      \\
        taskDatabaseHelper = TaskDatabaseHelper.TasGetDB(this);
        imagePaths = new ArrayList<>();
        id = Integer.parseInt(getIntent().getStringExtra("index"));
        images = new ArrayList<>();
        SetupImageRecyclerView();
        binding.timeReminder.setOnClickListener(view-> ShowTimeOrReminder());
        binding.TaskDetailsAddSubTask.setOnClickListener(view -> {
            task_sub_models.add(new Task_Sub_Model(" ", false));
            task_sub_adapter.notifyDataSetChanged();
        });
        binding.dueDate.setOnClickListener(view -> ShowDueDate());
        getSubTask();

        if (!taskDatabaseHelper.taskModelDAO().getAllTaskModelById(id).getImageBytes().isEmpty()) {

            images.addAll(taskDatabaseHelper.taskModelDAO().getAllTaskModelById(id).getImageBytes());
        }


        binding.TaskTitle.setText(taskDatabaseHelper.taskModelDAO().getAllTaskModelById(id).getTask());
        binding.TaskCheck.setChecked(taskDatabaseHelper.taskModelDAO().getAllTaskModelById(id).getTaskCheck());
        binding.TaskNotes.setText(taskDatabaseHelper.taskModelDAO().getAllTaskModelById(id).getNotes());
        Date newDate = taskDatabaseHelper.taskModelDAO().getAllTaskModelById(id).getDate();
        mDate = newDate;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("dd/MM/yy");
        String month_name = month_date.format(newDate.getTime());
        binding.dueDateView.setText(month_name);
//        SetTaskAlarmReceiver();
        CategorySpinner(taskDatabaseHelper.taskModelDAO().getAllTaskModelById(id).getCategory());
        binding.Attachment.setOnClickListener(Attachment-> ShowChouseImageType());
    }

    private void ShowChouseImageType(){
        ChouseImageTypeBoxBinding imageTypeBoxBinding;
        imageTypeBoxBinding = ChouseImageTypeBoxBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(this);
        dialog.setContentView(imageTypeBoxBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.newcategoryboxbg);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.CategorySpinnerAnimation;
        imageTypeBoxBinding.cancel.setOnClickListener(view -> dialog.dismiss());
        imageTypeBoxBinding.ChouseOne.setOnClickListener(one-> {
            ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(800, 800)
                    .start(ONEIMAGE);
            dialog.dismiss();

        });

        imageTypeBoxBinding.ChouseMultiple.setOnClickListener(one->{

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted, so request it
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
            }
            else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it from the user
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_EXTERNAL_STORAGE);
                }else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(Intent.createChooser(intent, "Select Pictures"), DOCUMENTED);
                }
                // Permission is already granted; you can proceed with the intent

            }
            dialog.dismiss();
        });
        dialog.show();

    }

    private void ShowDueDate() {

        DueDateCalendarBoxBinding calendarBoxBinding;
        calendarBoxBinding = DueDateCalendarBoxBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(this);
        dialog.setContentView(calendarBoxBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.newcategoryboxbg);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.CategorySpinnerAnimation;
        calendarBoxBinding.cancel.setOnClickListener(view -> dialog.dismiss());
        calendarBoxBinding.calendarView.setDate(mDate.getTime());
        calendarBoxBinding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar c = Calendar.getInstance();
            c.set(year, (month+1), dayOfMonth, 0, 0);
            mDate = new GregorianCalendar(year, month, dayOfMonth, 0, 0).getTime();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat month_date;
            month_date = new SimpleDateFormat("dd/MM/yy");
            String month_name = month_date.format(mDate.getTime());
            binding.dueDateView.setText(month_name);
        });
        calendarBoxBinding.save.setOnClickListener(view -> dialog.dismiss());

        dialog.show();

    }

    private void SetupImageRecyclerView(){
        imageAdapters = new ImageAttachmentAdapter(images,this,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.ImageAttachment.setLayoutManager(layoutManager);
        binding.ImageAttachment.setAdapter(imageAdapters);

    }

    private void ShowTimeOrReminder(){
        TimeorreminderboxBinding timeorreminderboxBinding;
        timeorreminderboxBinding = TimeorreminderboxBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(this);
        dialog.setContentView(timeorreminderboxBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.newcategoryboxbg);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.CategorySpinnerAnimation;
        timeorreminderboxBinding.cancel.setOnClickListener(view -> {
            dialog.dismiss();
  });
        timeorreminderboxBinding.save.setOnClickListener(view->{
//            setAlarmReceiver();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.SCHEDULE_EXACT_ALARM},
                            195);
                    return;
                }
                else {
                    setAlarm(this,id,calendar.getTimeInMillis(),binding.TaskTitle.getText().toString());
                    Log.i("unQiunid", "ShowTimeOrReminder: "+id);
                }
            }
            else {
                setAlarm(this,id,calendar.getTimeInMillis(),binding.TaskTitle.getText().toString());
                Log.i("unQiunid", "ShowTimeOrReminder: "+id);

            }

            dialog.dismiss();

        });
        timeorreminderboxBinding.simpleTimePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            calendar = Calendar.getInstance();
            calendar.set(Calendar.AM_PM,Calendar.PM);
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
        });
        dialog.show();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void getSubTask() {
        task_sub_models = new ArrayList<>();
        task_sub_adapter = new Task_Sub_Adapter(task_sub_models, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.SubTaskRecycler.setLayoutManager(layoutManager);
        binding.SubTaskRecycler.setAdapter(task_sub_adapter);
        sub_task = new ArrayList<>();
        sub_task_check = new ArrayList<>();
//        if (index >= 0 && index < taskDatabaseHelper.taskModelDAO().getAllTaskModel().size()) {
        sub_task.addAll(taskDatabaseHelper.taskModelDAO().getAllTaskModelById(id).getSub_Task());
        sub_task_check.addAll(taskDatabaseHelper.taskModelDAO().getAllTaskModelById(id).getSub_check());
//        }
        for (int i = 0; i < sub_task.size(); i++) {
            task_sub_models.add(new Task_Sub_Model(sub_task.get(i), sub_task_check.get(i)));
        }
        task_sub_adapter.notifyDataSetChanged();
    }
    private void CategorySpinner(String category) {
        ArrayAdapter<String> CategorySpinnerAdapter;
        CategoryDatabaseHelper categoryDatabaseHelper = CategoryDatabaseHelper.getDB(this);
        binding.TaskDetailsCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        int CategoryIndex = 0;
        ArrayList<String> CategorySpinnerDate = new ArrayList<>();
        ArrayList<CategoryModel> categoryModels = (ArrayList<CategoryModel>) categoryDatabaseHelper.categoryModelDAO().getAllCategory();
        CategorySpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CategorySpinnerDate);
        for (int i = 0; i < categoryModels.size(); i++) {
            if (categoryModels.get(i).getCategoryName().equals(category)) {
                CategoryIndex = i;
            }
            CategorySpinnerDate.add(categoryModels.get(i).getCategoryName());
            CategorySpinnerAdapter.notifyDataSetChanged();
        }
        CategorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.TaskDetailsCategorySpinner.setAdapter(CategorySpinnerAdapter);

        binding.TaskDetailsCategorySpinner.setSelection(CategoryIndex);
    }
    @Override
    public void onBackPressed() {
        changingData();
        super.onBackPressed();
//        Toast.makeText(this, "onBackPressed", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        changingData();
        super.onDestroy();
    }

    private void changingData() {
//        AlarmUtils alarmUtils = new AlarmUtils();
//        ArrayList<Uri> uriList = arrayListImage;`
//        UriListWithContext uriListWithContext = new UriListWithContext(images, this);
//
//        byte[] byteArrayList = ByteArrayListConverter.fromUriListWithContext(uriListWithContext);
//        ArrayList<Uri> uriList = /* Your ArrayList of Uri */;
//        byte[] byteData = Converters.fromUriList(uriList);
//        MyEntity myEntity = new MyEntity();
//        myEntity.imageBytes = byteData;
//        myDatabase.myDao().insert(myEntity);

//        MyEntity myEntity = new MyEntity();
//        myEntity.byteArrayList = byteArrayList;
//        myDatabase.myDao().insert(myEntity);

// Retrieve data from the database
//        MyEntity retrievedEntity = myDatabase.myDao().getEntityById(id);
//        ArrayList<byte[]> retrievedByteArrayList = retrievedEntity.byteArrayList;
//        ArrayList<Uri> retrievedUriList = Converters.toUriList(retrievedByteArrayList);
//
//

        Log.i("images_SIZE", "changingData: "+images.size());
        for (int i = 0; i < images.size(); i++) {
            String imagePath = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                imagePath = saveImageToExternalStorage(Uri.parse(images.get(i)));
                imagePaths.add(imagePath);
            }
        }




            ArrayList<String> sub_task_String = new ArrayList<>();
            ArrayList<Boolean> sub_task_Check = new ArrayList<>();
            for (int i = 0; i < task_sub_models.size(); i++) {
                sub_task_String.add(task_sub_models.get(i).getSub_task());
                sub_task_Check.add(task_sub_models.get(i).isTrue());
            }
            TaskModel taskModel = new TaskModel();
            taskModel.setId(id);
            taskModel.setTask(binding.TaskTitle.getText().toString());
            taskModel.setNotes(binding.TaskNotes.getText().toString());
            taskModel.setCategory(binding.TaskDetailsCategorySpinner.getSelectedItem().toString());
            taskModel.setTaskCheck(binding.TaskCheck.isChecked());
            taskModel.setSub_Task(sub_task_String);
            taskModel.setSub_check(sub_task_Check);
            taskModel.setImageBytes(imagePaths);
//        for (Uri imageUri : images) {
////            byte[] imageBytes = convertUriToByteArray(imageUri);
//            byte[] imageBytes = convertUriToByteArray(imageUri, getContentResolver());
//            if (imageBytes != null) {
//                taskModel.imageBytes = imageBytes;
//            }

//        }
//        taskModel.setImageData(arrayListImage);
            if (mDate == null) {
                mDate = new Date();
                Toast.makeText(this, "Fall to Change Date Now we set data as" + mDate, Toast.LENGTH_SHORT).show();
            }
            taskModel.setDate(mDate);
            taskDatabaseHelper.taskModelDAO().UpdateTaskModel(taskModel);
            finish();
        }
    public static void setAlarm(Context context, int alarmId, long triggerTimeInMillis, String alarmName) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, TaskAlarmReceiver.class);
        intent.putExtra("alarmId", alarmId);
        intent.putExtra("alarmName", alarmName);

//        int  flags;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            flags = PendingIntent.FLAG_IMMUTABLE;
//        } else {
//            flags = PendingIntent.FLAG_UPDATE_CURRENT;
//        }
        int flags;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags = PendingIntent.FLAG_IMMUTABLE;
        } else {
            flags = PendingIntent.FLAG_UPDATE_CURRENT;
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, flags);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTimeInMillis, pendingIntent);

//        Service.startForeground();
    }
    private void showPermissionDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission Denied");
        builder.setMessage("This app needs storage permission to function properly.");

        builder.setPositiveButton("Open Settings", (dialog, which) -> {
            // Open the app's settings
            openAppSettings();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // Handle the user's choice, e.g., show an error message
        });

        builder.show();
    }
    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DOCUMENTED && resultCode == RESULT_OK) {
            if (data != null) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri fileUri = data.getClipData().getItemAt(i).getUri();
                        images.add(String.valueOf(fileUri));
                        imageAdapters.notifyDataSetChanged();
                    }
                }
                else if (data.getData() != null) {
                    // Single file selected
                    Uri fileUri = data.getData();
                    images.add(String.valueOf(fileUri));
                    imageAdapters.notifyDataSetChanged();

                }
            }
        }
        else if (requestCode==ONEIMAGE&& resultCode == RESULT_OK && data!=null){
            images.add(String.valueOf(data.getData()));
            imageAdapters.notifyDataSetChanged();

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 195) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, continue setting the alarm
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                setAlarm(this,id,calendar.getTimeInMillis(),binding.TaskTitle.getText().toString());
            } else {
                // Permission denied, handle accordingly (e.g., show a message)
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                this.startActivity(intent);

            }
        }
        else if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with the intent
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Pictures"), DOCUMENTED);
            } else {
                // Permission denied, handle it accordingly (e.g., show a message to the user)
                Toast.makeText(this, "GIVE PERMISSION", Toast.LENGTH_SHORT).show();

            }
        }

        else if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can proceed with file operations
            } else {
                // Permission denied, handle accordingly (e.g., show a message to the user)
                showPermissionDeniedDialog();

            }
        }
    }









//    private String saveImageToExternalStorage(Uri fileUri) {
//        try {
//            if (fileUri == null) {
//                // Handle the case where the URI is null
//                return null;
//            }
//
//            File directory = new File(Environment.getExternalStoragePublicDirectory(
//                    Environment.DIRECTORY_PICTURES), "Attachment");
//
//            if (!directory.exists() && !directory.mkdirs()) {
//                // Handle the case where directory creation fails
//                return null;
//            }
//            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString());
//
//            String fileName = "attachment" + System.currentTimeMillis() + fileExtension;
//            File outputFile = new File(directory, fileName);
//
//            InputStream inputStream = getContentResolver().openInputStream(fileUri);
//            FileOutputStream outputStream = new FileOutputStream(outputFile);
//
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = inputStream.read(buffer)) > 0) {
//                outputStream.write(buffer, 0, length);
//            }
//
//            inputStream.close();
//            outputStream.close();
//
//            return outputFile.getAbsolutePath();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            // Handle the File Not Found error here
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle other IO-related errors here
//        }
//        return null; // Return null to indicate an error
//    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private String saveImageToExternalStorage(Uri fileUri) {
        try {
            if (fileUri == null) {
                // Handle the case where the URI is null
                return null;
            }
            String fileExtension =getFileExtensionFromUri(fileUri);
            Log.i("fileExtension+++))", "saveImageToExternalStorage: "+fileExtension);
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "attachment" + System.currentTimeMillis() + "."+fileExtension);
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Attachment");

            Uri imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            if (imageUri == null) {
                // Handle the case where the image couldn't be inserted
                return null;
            }

            OutputStream outputStream = getContentResolver().openOutputStream(imageUri);

            if (outputStream == null) {
                // Handle the case where the output stream is null
                return null;
            }

            InputStream inputStream = getContentResolver().openInputStream(fileUri);

            if (inputStream == null) {
                // Handle the case where the input stream is null
                return null;
            }

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return imageUri.toString();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle other IO-related errors here
        }
        return null; // Return null to indicate an error
    }

    private String getFileExtensionFromUri(Uri fileUri) {
        if (fileUri == null) {
            // Handle the case where the URI is null
            return null;
        }

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        String fileExtension = null;

        // Check if the URI is a content URI
        if (ContentResolver.SCHEME_CONTENT.equals(fileUri.getScheme())) {
            fileExtension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(fileUri));
        }

        // If the extension is still null, try to extract it from the file path
        if (fileExtension == null) {
            String filePath = fileUri.getPath();
            if (filePath != null) {
                fileExtension = MimeTypeMap.getFileExtensionFromUrl(filePath);
            }
        }

        return fileExtension;
    }
    @Override
    public void remove(int pos) {
        images.remove(pos);
        imageAdapters.notifyItemRemoved(pos);

    }



}