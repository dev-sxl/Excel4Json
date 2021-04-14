package com.xyz.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;

/**
 * @author sxl
 * @since 2021/4/14 14:00
 */
@Accessors(chain = true)
@Data
public class FieldCtx {

    private Field field;

    private String[] jsonPath;
}
