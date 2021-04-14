package com.xyz.converter;

import com.xyz.bean.ClassCtx;
import com.xyz.bean.FieldCtx;
import com.xyz.extracter.Extracter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sxl
 * @since 2021/4/14 17:15
 */
public class Json2Ojb {

    public static <T> List<T> convert2Objs(List<String> jsonText, ClassCtx<T> clsCtx) {
        return jsonText.stream().map(json -> {
            try {
                return convert2Obj(json, clsCtx);
            } catch (Exception e) {
                throw new RuntimeException("json转对象失败", e);
            }
        }).collect(Collectors.toList());
    }

    public static <T> T convert2Obj(String jsonText, ClassCtx<T> clsCtx) throws InstantiationException,
            IllegalAccessException {

        T instance = clsCtx.getObjClass().newInstance();
        List<FieldCtx> fields = clsCtx.getFields();
        fields.forEach(fieldCtx -> {
            String[] jsonPath = fieldCtx.getJsonPath();
            if (ArrayUtils.isEmpty(jsonPath)) {
                return;
            }
            String value = Extracter.extractByPaths(jsonText, jsonPath);
            if (StringUtils.isNotBlank(value)) {
                try {
                    fieldCtx.getField().set(instance, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return instance;
    }

}
