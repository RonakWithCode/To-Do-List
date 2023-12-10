package com.crazyostudio.to_do_list.Converters;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
public class ArrayListConverters {
    @TypeConverter
    public static ArrayList<Boolean> fromBooleanString(String value) {
        if (value == null || value.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<Boolean> list = new ArrayList<>();
        String[] elements = value.split(",");
        for (String element : elements) {
            list.add(Boolean.parseBoolean(element));
        }
        return list;
    }

    @TypeConverter
    public static String toBooleanString(ArrayList<Boolean> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Boolean element : list) {
            stringBuilder.append(element).append(",");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    @TypeConverter
    public static ArrayList<String> fromStringList(String value) {
        if (value == null || value.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<String> list = new ArrayList<>();
        String[] elements = value.split(",");
        Collections.addAll(list, elements);
        return list;
    }

    @TypeConverter
    public static String toStringList(ArrayList<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String element : list) {
            stringBuilder.append(element).append(",");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}