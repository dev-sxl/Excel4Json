package com.xyz.exporter;

import com.github.crab2died.ExcelUtils;
import com.xyz.annotation.JsonPath;
import com.xyz.bean.ClassCtx;
import com.xyz.bean.FieldCtx;
import com.xyz.extracter.Extracter;
import com.xyz.converter.Json2Ojb;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sxl
 * @since 2021/4/14 17:15
 */
public class Exporter {

    /**
     * @param jsonText   json文本
     * @param tClass     实体类class
     * @param exportPath 导出路径
     * @param <T>
     */
    public <T> void toExcel(String jsonText, Class<T> tClass, String exportPath) {

        ClassCtx<T> clsCtx = classClassCtx(tClass);

        String[] jsonPathList = clsCtx.getJsonPath();
        String value = jsonText;
        if (ArrayUtils.isNotEmpty(jsonPathList)) {
            value = Extracter.extractByPaths(value, jsonPathList);
        }

        List<String> jsonValues = Extracter.extractListByPath(value, "$[*]", String.class);
        List<T> objs = Json2Ojb.convert2Objs(jsonValues, clsCtx);

        if (CollectionUtils.isEmpty(objs)) {
            System.out.println("需导出数据为空!!!");
            return;
        }

        try {
            ExcelUtils.getInstance().exportObjects2Excel(objs, tClass, true, exportPath);
        } catch (Exception e) {
            throw new RuntimeException("执行导出结果失败", e);
        }
    }

    /**
     * @param importPath 导入文件路径
     * @param tClass     导出信息实体class
     * @param exportPath 导出文件路径
     * @param <T>        实体类型
     */
    public <T> void toExcelFromFilePath(String importPath, Class<T> tClass, String exportPath) {
        String fileText;
        try {
            fileText = FileUtils.readFileToString(new File(importPath));
        } catch (IOException e) {
            throw new RuntimeException("读入文件错误", e);
        }
        toExcel(fileText, tClass, exportPath);
    }

    private <T> ClassCtx<T> classClassCtx(Class<T> tClass) {
        // 获取属性
        Field[] fields = tClass.getDeclaredFields();
        List<FieldCtx> fieldCtxList = Arrays.stream(fields).map(this::fieldCtx).collect(Collectors.toList());
        ClassCtx<T> pathCtx = new ClassCtx<T>().setObjClass(tClass).setFields(fieldCtxList);
        JsonPath clsJsonPath = tClass.getAnnotation(JsonPath.class);
        if (clsJsonPath == null) {
            return pathCtx;
        }
        return pathCtx.setJsonPath(clsJsonPath.path());
    }

    public FieldCtx fieldCtx(Field field) {
        String[] jsonPath = fieldJsonPath(field);
        return new FieldCtx().setField(field).setJsonPath(jsonPath);
    }

    private String[] fieldJsonPath(Field field) {
        field.setAccessible(true);
        JsonPath jsonPath = field.getAnnotation(JsonPath.class);
        if (jsonPath == null) {
            return null;
        }
        return jsonPath.path();
    }

}
