package com.ymj.campuscanvas.utils;

import com.ymj.campuscanvas.annotation.Sortable;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SortValidator {
    // 缓存实体类元数据：类 -> 允许排序的字段映射
    private static final Map<Class<?>, Set<String>> sortableFieldsCache = new ConcurrentHashMap<>();

    public static String validateSortBy(Class<?> entityClass, String inputSortBy) {
        if(inputSortBy == null || inputSortBy.isEmpty()) {
            throw new IllegalArgumentException("Sort by field cannot be null or empty");
        }

        // 获取当前实体允许排序的字段集合
        Set<String> allowedFields = sortableFieldsCache.computeIfAbsent(entityClass, clazz -> {
            Set<String> fields = new LinkedHashSet<>();
            for (Field field : clazz.getDeclaredFields()) {
                Sortable sortable = field.getAnnotation(Sortable.class);
                if (sortable != null) {
                    // 优先使用注解指定的列名，否则自动转下划线命名
                    String column = sortable.column().isEmpty()
                            ? camelToUnderscore(field.getName())
                            : sortable.column();
                    fields.add(column);
                }
            }
            return fields;
        });

        // 校验排序字段
        String underscoreInputSortBy = camelToUnderscore(inputSortBy);
        if (allowedFields.contains(underscoreInputSortBy)) {
            return underscoreInputSortBy;
        }else{
            // 抛出异常
            throw new IllegalArgumentException("Invalid sort by field: " + inputSortBy + ", allowed fields: " + allowedFields);
        }
    }

    // 驼峰转下划线工具
    private static String camelToUnderscore(String camel) {
        return camel.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    public static String validateSortOrder(String inputSortOrder) {
        if (inputSortOrder == null || inputSortOrder.isEmpty()) {
            throw new IllegalArgumentException("Sort order cannot be null or empty");
        }
        String lowerCaseSortOrder = inputSortOrder.toLowerCase();
        if (!lowerCaseSortOrder.equals("asc") && !lowerCaseSortOrder.equals("desc")) {
            throw new IllegalArgumentException("Invalid sort order: " + inputSortOrder);
        }
        return lowerCaseSortOrder;
    }

    public static String validateSort(Class<?> entityClass, String inputSortBy, String inputSortOrder) {
        if(inputSortBy != null && inputSortOrder != null) {
            String validSortBy = validateSortBy(entityClass, inputSortBy);
            String validSortOrder = validateSortOrder(inputSortOrder);
            return validSortBy + " " + validSortOrder;
        } else {
            return null; // 默认排序
        }
    }
}
