package cn.teach.equip.bean.pojo;

import java.util.List;

public class FenLeiBO {


    /**
     * levelId2 : 1
     * levelName2 : 教学仪器1
     * subList : [{"levelId3":10,"levelName3":"教学仪器1-1","productList":{"pageList":[{"title":"标题4","desc":"简介4","addTime":"2020-07-02 16:34:49","url":"https://shjz.yingjin.pro/api/upload/article/44","smallImgUrl":"https://shjz.yingjin.pro/api/upload/article/t2.png","bigImgUrl":"https://shjz.yingjin.pro/api/upload/article/t1.png","code":"44","bannerList":[],"content":null,"collect":false}],"page":1,"pageSize":20,"totalPage":1,"totalSize":1,"startPageNum":1,"endPageNum":1,"hasPreviousPage":false,"hasNextPage":false}},{"levelId3":11,"levelName3":"教学仪器1-2","productList":null}]
     */

    private int levelId2;
    private String levelName2;
    private List<SubListBean> subList;

    public int getLevelId2() {
        return levelId2;
    }

    public void setLevelId2(int levelId2) {
        this.levelId2 = levelId2;
    }

    public String getLevelName2() {
        return levelName2;
    }

    public void setLevelName2(String levelName2) {
        this.levelName2 = levelName2;
    }

    public List<SubListBean> getSubList() {
        return subList;
    }

    public void setSubList(List<SubListBean> subList) {
        this.subList = subList;
    }

    public static class SubListBean {
        /**
         * levelId3 : 10
         * levelName3 : 教学仪器1-1
         * productList : {"pageList":[{"title":"标题4","desc":"简介4","addTime":"2020-07-02 16:34:49","url":"https://shjz.yingjin.pro/api/upload/article/44","smallImgUrl":"https://shjz.yingjin.pro/api/upload/article/t2.png","bigImgUrl":"https://shjz.yingjin.pro/api/upload/article/t1.png","code":"44","bannerList":[],"content":null,"collect":false}],"page":1,"pageSize":20,"totalPage":1,"totalSize":1,"startPageNum":1,"endPageNum":1,"hasPreviousPage":false,"hasNextPage":false}
         */

        private int levelId3;
        private String levelName3;

        public int getLevelId3() {
            return levelId3;
        }

        public void setLevelId3(int levelId3) {
            this.levelId3 = levelId3;
        }

        public String getLevelName3() {
            return levelName3;
        }

        public void setLevelName3(String levelName3) {
            this.levelName3 = levelName3;
        }


    }
}
