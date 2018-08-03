package cn.bluegod.mycinema.pojo;

import java.util.Date;

public class Comment {
    private Long id;

    private Long u_id;

    private Long m_id;

    private String content;

    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getuId() {
        return u_id;
    }

    public void setuId(Long uId) {
        this.u_id = uId;
    }

    public Long getmId() {
        return m_id;
    }

    public void setmId(Long mId) {
        this.m_id = mId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content ;
    }
}