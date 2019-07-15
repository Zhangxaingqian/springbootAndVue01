package cn.myweb01.money01.pojo.ProvinceCity;

import java.io.Serializable;

public class City implements Serializable {
    private String code;
    private String name;
    private String provincecode;

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

    public String getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode;
    }
}
