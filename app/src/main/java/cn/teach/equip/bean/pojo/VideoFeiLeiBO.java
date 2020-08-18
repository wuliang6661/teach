package cn.teach.equip.bean.pojo;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/8/1810:28
 * desc   :  视频分类
 * version: 1.0
 */
public class VideoFeiLeiBO {


    /**
     * name : 测试1
     * videoTypeId : 9
     * subList : [{"name":"测试1-2","videoTypeId":12,"subList":[]},{"name":"测试1-1","videoTypeId":11,"subList":[]}]
     */

    private String name;
    private int videoTypeId;
    private List<SubListBean> subList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVideoTypeId() {
        return videoTypeId;
    }

    public void setVideoTypeId(int videoTypeId) {
        this.videoTypeId = videoTypeId;
    }

    public List<SubListBean> getSubList() {
        return subList;
    }

    public void setSubList(List<SubListBean> subList) {
        this.subList = subList;
    }

    public static class SubListBean {
        /**
         * name : 测试1-2
         * videoTypeId : 12
         * subList : []
         */

        private String name;
        private int videoTypeId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getVideoTypeId() {
            return videoTypeId;
        }

        public void setVideoTypeId(int videoTypeId) {
            this.videoTypeId = videoTypeId;
        }
    }
}
