package cn.bmob.model;

import cn.bmob.v3.BmobObject;

/**
 *  反馈
 * */
public class Feedback extends BmobObject {
    public final static int FEEDBACK_ERROR = 1; /**错误
    public final static int FEEDBACK_ADVICE = 2; /**建议
    public final static int FEEDBACK_OTHER = 3;/**吐槽
    /**反馈内容*/
    private String content;
    /**反馈联系方式*/
    private String phoneOrqq;
    /**是否可被客服采访*/
    private boolean interview;
    /**反馈类型*/
    private int type;

    public String getPhoneOrqq() {
        return phoneOrqq;
    }

    public void setPhoneOrqq(String phoneOrqq) {
        this.phoneOrqq = phoneOrqq;
    }

    public boolean isInterview() {
        return interview;
    }

    public void setInterview(boolean interview) {
        this.interview = interview;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

