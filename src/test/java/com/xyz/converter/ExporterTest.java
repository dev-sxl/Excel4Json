package com.xyz.converter;

import com.xyz.exporter.Exporter;

import java.io.File;

/**
 * @author sxl
 * @since 2021/4/14 17:43
 */
class ExporterTest {


    public static void main(String[] args) {
        String importPath = System.getProperty("user.dir") + File.separator + "doc" + File.separator + "czb.txt";
        System.out.println("method: main, param: outPath= " + importPath);
        String exportPath = "/Users/mark/Documents/test/车主邦站点1.xlsx";
        new Exporter().toExcelFromFilePath(importPath, StationDic.class, exportPath);
    }

}