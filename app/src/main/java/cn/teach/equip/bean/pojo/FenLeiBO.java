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
        private ProductListBean productList;

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

        public ProductListBean getProductList() {
            return productList;
        }

        public void setProductList(ProductListBean productList) {
            this.productList = productList;
        }

        public static class ProductListBean {
            /**
             * pageList : [{"title":"标题4","desc":"简介4","addTime":"2020-07-02 16:34:49","url":"https://shjz.yingjin.pro/api/upload/article/44","smallImgUrl":"https://shjz.yingjin.pro/api/upload/article/t2.png","bigImgUrl":"https://shjz.yingjin.pro/api/upload/article/t1.png","code":"44","bannerList":[],"content":null,"collect":false}]
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
                 * title : 标题4
                 * desc : 简介4
                 * addTime : 2020-07-02 16:34:49
                 * url : https://shjz.yingjin.pro/api/upload/article/44
                 * smallImgUrl : https://shjz.yingjin.pro/api/upload/article/t2.png
                 * bigImgUrl : https://shjz.yingjin.pro/api/upload/article/t1.png
                 * code : 44
                 * bannerList : []
                 * content : null
                 * collect : false
                 */

                private String title;
                private String desc;
                private String addTime;
                private String url;
                private String smallImgUrl;
                private String bigImgUrl;
                private String code;
                private String content;
                private boolean collect;
                private List<?> bannerList;

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

                public String getSmallImgUrl() {
                    return smallImgUrl;
                }

                public void setSmallImgUrl(String smallImgUrl) {
                    this.smallImgUrl = smallImgUrl;
                }

                public String getBigImgUrl() {
                    return bigImgUrl;
                }

                public void setBigImgUrl(String bigImgUrl) {
                    this.bigImgUrl = bigImgUrl;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public boolean isCollect() {
                    return collect;
                }

                public void setCollect(boolean collect) {
                    this.collect = collect;
                }

                public List<?> getBannerList() {
                    return bannerList;
                }

                public void setBannerList(List<?> bannerList) {
                    this.bannerList = bannerList;
                }
            }
        }
    }
}
