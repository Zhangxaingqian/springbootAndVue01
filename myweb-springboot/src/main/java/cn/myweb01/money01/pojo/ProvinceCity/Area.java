package cn.myweb01.money01.pojo.ProvinceCity;

import java.io.Serializable;

public class Area implements Serializable {
    private String code;
    private String name;
    private String citycode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }
}
