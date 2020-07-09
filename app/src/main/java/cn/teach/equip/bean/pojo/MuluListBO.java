package cn.teach.equip.bean.pojo;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/7/915:55
 * desc   :
 * version: 1.0
 */
public class MuluListBO {


    /**
     * pageList : [{"title":"文件2","desc":"简介2","addTime":"2020-07-08 14:41:30","url":"https://shjz.yingjin.pro/api/upload/document/2.txt"}]
     * page : 1
     * pageSize : 20
     * totalPage : 1
     * totalSize : 1
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

    public static class PageListBean {
        /**
         * title : 文件2
         * desc : 简介2
         * addTime : 2020-07-08 14:41:30
         * url : https://shjz.yingjin.pro/api/upload/document/2.txt
         */

        private String title;
        private String desc;
        private String addTime;
        private String url;
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

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
    }
}
