package com.xyz.bean;

import com.sun.istack.internal.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author sxl
 * @since 2021/4/14 17:34
 */
@Accessors(chain = true)
@Data
public class ClassCtx<T> {

    private Class<T> objClass;

    private List<FieldCtx> fields;

    @Nullable
    private String[] jsonPath;

}
