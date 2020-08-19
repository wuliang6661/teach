package cn.teach.equip.bean.pojo;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/8/1913:34
 * desc   :
 * version: 1.0
 */
public class TagBO {


    /**
     * name : 中小学
     * tagId : 1
     * tags : [{"name":"中学","tagId":2},{"name":"小学","tagId":3}]
     */

    private String name;
    private int tagId;
    private List<TagsBean> tags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TagsBean {
        /**
         * name : 中学
         * tagId : 2
         */

        private String name;
        private int tagId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTagId() {
            return tagId;
        }

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }
    }
}
