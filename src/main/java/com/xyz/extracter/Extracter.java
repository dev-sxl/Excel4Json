package com.xyz.extracter;

import com.xyz.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author sxl
 * @since 2021/4/14 17:37
 */
public class Extracter {

    public static String extractByPaths(String jsonText, String[] jsonPathList) {
        String str = jsonText;
        for (String jsonPath : jsonPathList) {
            if (StringUtils.isBlank(str)) {
                return str;
            }

            Object result = null;
            try {
                result = com.jayway.jsonpath.JsonPath.read(str, jsonPath);
            } catch (Exception e) {
                //    导出数据中会为空,配置错误
            }
            if (result != null) {
                str = result.toString();
            }
        }
        return str;
    }

    public static <T> List<T> extractListByPath(String jsonText, String jsonPath, Class<T> tClass) {
        return JsonUtils.extraListByPath(jsonText, jsonPath, tClass);
    }


}
