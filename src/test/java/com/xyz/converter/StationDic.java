package com.xyz.converter;

import com.github.crab2died.annotation.ExcelField;
import com.xyz.annotation.JsonPath;
import lombok.Data;

/**
 * 类上的jsonPath过滤出导出数据的json text,可以不加
 */
@JsonPath(path = "$[*]._source")
@Data
public class StationDic {

    /**
     * 油站名称
     */
    @JsonPath(path = {"$.sale_channels[?(@.supplier_type=='2')]", "$[0].station_name"})
    @ExcelField(title = "油站名称")
    private String stationName;
    /**
     * 省份
     */
    @JsonPath(path = "$.province_name")
    @ExcelField(title = "省份")
    private String province;
    @JsonPath(path = "$.city_name")
    @ExcelField(title = "城市")
    private String city;
    @JsonPath(path = "$.district_name")
    @ExcelField(title = "地区")
    private String district;
    @JsonPath(path = "$.address")
    @ExcelField(title = "地址")
    private String address;
    @JsonPath(path = "$.location")
    @ExcelField(title = "经纬度")
    private String latLng;
    /**
     * 最近的找油站点id
     */
    @JsonPath(path = {"$.nearest_stations[?(@.station_type=='1')]", "$[0].station_id"})
    @ExcelField(title = "距离最近的找油站点id")
    private String zyStationId;
    /**
     * 最近的找油站点名称
     */
    @JsonPath(path = {"$.nearest_stations[?(@.station_type=='1')]", "$[0].station_name"})
    @ExcelField(title = "距离最近的找油站点名称")
    private String zyStationName;
    @JsonPath(path = {"$.nearest_stations[?(@.station_type=='1')]", "$[0].distance"})
    @ExcelField(title = "距离最近的找油站点距离")
    private String distance;
}
