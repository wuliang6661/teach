package cn.teach.equip.bean.pojo;

import java.io.Serializable;

public class UnitBO implements Serializable {


    /**
     * unitName : 滨江幼儿园
     * unitId : 1
     * unitFirstLetter : B
     * provinceName : 浙江
     * cityName : 杭州
     */

    private String unitName;
    private int unitId;
    private String unitFirstLetter;
    private String provinceName;
    private String cityName;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitFirstLetter() {
        return unitFirstLetter;
    }

    public void setUnitFirstLetter(String unitFirstLetter) {
        this.unitFirstLetter = unitFirstLetter;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
