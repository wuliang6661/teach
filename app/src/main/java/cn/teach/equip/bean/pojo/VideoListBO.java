package cn.teach.equip.bean.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/7/817:01
 * desc   :
 * version: 1.0
 */
public class VideoListBO {


    /**
     * pageList : [{"title":"标题3","desc":"简介3","addTime":"2020-07-03 00:26:23","url":"https://shjz.yingjin.pro/api/upload/video/3.mp4","imgUrl":"https://shjz.yingjin.pro/api/upload/article/1.png","likeNum":444,"code":"333"},{"title":"标题2","desc":"简介2","addTime":"2020-07-03 00:26:23","url":"https://shjz.yingjin.pro/api/upload/video/1.mp4","imgUrl":"https://shjz.yingjin.pro/api/upload/article/1.png","likeNum":444,"code":"222"},{"title":"标题1","desc":"简介1","addTime":"2020-07-03 00:26:23","url":"https://shjz.yingjin.pro/api/upload/video/2.mp4","imgUrl":"https://shjz.yingjin.pro/api/upload/article/1.png","likeNum":5,"code":"111"}]
     * page : 1
     * pageSize : 20
     * totalPage : 1
     * totalSize : 3
     * startPageNum : 1
     * endPageNum : 1
     * hasPreviousPage : false
     * hasNextPage : false
     */

    private int page;
    private int pageSize;
    private int totalPage;
    private int totalSize;
    private int startPageNum;
    private int endPageNum;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private List<PageListBean> pageList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getStartPageNum() {
        return startPageNum;
    }

    public void setStartPageNum(int startPageNum) {
        this.startPageNum = startPageNum;
    }

    public int getEndPageNum() {
        return endPageNum;
    }

    public void setEndPageNum(int endPageNum) {
        this.endPageNum = endPageNum;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public List<PageListBean> getPageList() {
        return pageList;
    }

    public void setPageList(List<PageListBean> pageList) {
        this.pageList = pageList;
    }

    public static class PageListBean implements Serializable {
        /**
         * title : 标题3
         * desc : 简介3
         * addTime : 2020-07-03 00:26:23
         * url : https://shjz.yingjin.pro/api/upload/video/3.mp4
         * imgUrl : https://shjz.yingjin.pro/api/upload/article/1.png
         * likeNum : 444
         * code : 333
         */

        private String title;
        private String desc;
        private String addTime;
        private String url;
        private String imgUrl;
        private int likeNum;
        private String code;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
