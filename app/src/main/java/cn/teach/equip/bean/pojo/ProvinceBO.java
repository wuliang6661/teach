package cn.teach.equip.bean.pojo;

import java.util.List;

public class ProvinceBO {


    /**
     * provinceName : 江苏
     * provinceId : 3
     * firstLetter : J
     * cityList : [{"cityName":"苏州","cityId":4,"firstLetter":"S"}]
     */

    private String provinceName;
    private int provinceId;
    private String firstLetter;
    private List<CityListBean> cityList;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public List<CityListBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityListBean> cityList) {
        this.cityList = cityList;
    }

    public static class CityListBean {
        /**
         * cityName : 苏州
         * cityId : 4
         * firstLetter : S
         */

        private String cityName;
        private int cityId;
        private String firstLetter;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getFirstLetter() {
            return firstLetter;
        }

        public void setFirstLetter(String firstLetter) {
            this.firstLetter = firstLetter;
        }
    }
}