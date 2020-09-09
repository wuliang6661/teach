package cn.teach.equip.bean.pojo;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/8/615:03
 * desc   : 文章是否最新的
 * version: 1.0
 */
public class WenZhangVersionBO {


    /**
     * latestVersion1 : 234567890
     * latestVersion2 :
     * latestVersion3 :
     */

    private String latestVersion1;
    private String latestVersion2;
    private String latestVersion3;
    private int readStatus1;
    private int readStatus2;
    private int readStatus3;

    public String getLatestVersion1() {
        return latestVersion1;
    }

    public void setLatestVersion1(String latestVersion1) {
        this.latestVersion1 = latestVersion1;
    }

    public String getLatestVersion2() {
        return latestVersion2;
    }

    public void setLatestVersion2(String latestVersion2) {
        this.latestVersion2 = latestVersion2;
    }

    public String getLatestVersion3() {
        return latestVersion3;
    }

    public void setLatestVersion3(String latestVersion3) {
        this.latestVersion3 = latestVersion3;
    }

    public int getReadStatus1() {
        return readStatus1;
    }

    public void setReadStatus1(int readStatus1) {
        this.readStatus1 = readStatus1;
    }

    public int getReadStatus2() {
        return readStatus2;
    }

    public void setReadStatus2(int readStatus2) {
        this.readStatus2 = readStatus2;
    }

    public int getReadStatus3() {
        return readStatus3;
    }

    public void setReadStatus3(int readStatus3) {
        this.readStatus3 = readStatus3;
    }
}
