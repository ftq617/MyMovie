package cn.bluegod.mycinema.pojo;

import java.util.Date;

public class Session {
    private Long id;

    private String c_id;

    private String m_id;

    private String fh_id;

    private Date starttime;

    private Integer timelength;

    private Integer price;

    private String selectseat;

    private Integer remain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getcId() {
        return c_id;
    }

    public void setcId(String c_id) {
        this.c_id = c_id;
    }

    public String getmId() {
        return m_id;
    }

    public void setmId(String m_id) {
        this.m_id = m_id;
    }

    public String getFhId() {
        return fh_id;
    }

    public void setFhId(String fh_id) {
        this.fh_id = fh_id;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Integer getTimelength() {
        return timelength;
    }

    public void setTimelength(Integer timelength) {
        this.timelength = timelength;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSelectseat() {
        return selectseat;
    }

    public void setSelectseat(String selectseat) {
        this.selectseat = selectseat ;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }
}