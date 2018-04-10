package one.show.common;

/*
 * Create time : 2010-01-25
 */

import java.util.List;

/**
 * 封装服务节点对象
 * 
 * 修改记录：<br>
 * 1.2010-06-07 djg 扩展FMS的CDN.<br>
 * 2.2012-09-21 zjq 扩展PUSH的CDN.<br>
 * 
 * @author jiangongduan
 * @version 1.0
 * @since 1.0
 * 
 */
public class ServerNodeBean {

    private List<IDCBean> norIDCList;// 普通IDC列表

    private List<IDCBean> fmsIDCList;// fms IDC列表

    private List<IDCBean> pushIDCList;// 直播间推流 IDC列表

    private int norAllowCount;// 服务节点允许的总数

    private int fmsAllowCount;

    private int pushAllowCount;

    private float norTinyNum;// 波动值,默认为1

    private float fmsTinyNum;

    private float pushTinyNum;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<IDCBean> getNorIDCList() {
        return norIDCList;
    }

    public void setNorIDCList(List<IDCBean> norIDCList) {
        this.norIDCList = norIDCList;
    }

    public List<IDCBean> getFmsIDCList() {
        return fmsIDCList;
    }

    public void setFmsIDCList(List<IDCBean> fmsIDCList) {
        this.fmsIDCList = fmsIDCList;
    }

    public int getNorAllowCount() {
        return norAllowCount;
    }

    public void setNorAllowCount(int norAllowCount) {
        this.norAllowCount = norAllowCount;
    }

    public int getFmsAllowCount() {
        return fmsAllowCount;
    }

    public void setFmsAllowCount(int fmsAllowCount) {
        this.fmsAllowCount = fmsAllowCount;
    }

    public float getNorTinyNum() {
        return norTinyNum;
    }

    public void setNorTinyNum(float norTinyNum) {
        this.norTinyNum = norTinyNum;
    }

    public float getFmsTinyNum() {
        return fmsTinyNum;
    }

    public void setFmsTinyNum(float fmsTinyNum) {
        this.fmsTinyNum = fmsTinyNum;
    }

    public List<IDCBean> getPushIDCList() {
        return pushIDCList;
    }

    public void setPushIDCList(List<IDCBean> pushIDCList) {
        this.pushIDCList = pushIDCList;
    }

    public int getPushAllowCount() {
        return pushAllowCount;
    }

    public void setPushAllowCount(int pushAllowCount) {
        this.pushAllowCount = pushAllowCount;
    }

    public float getPushTinyNum() {
        return pushTinyNum;
    }

    public void setPushTinyNum(float pushTinyNum) {
        this.pushTinyNum = pushTinyNum;
    }
}

