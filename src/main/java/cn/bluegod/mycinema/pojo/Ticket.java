package cn.bluegod.mycinema.pojo;

public class Ticket {
    private Long id;

    private String u_id;

    private String m_id;

    private String fh_id;

    private String c_id;

    private String filed_id;

    private Integer state;

    private String seat;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getFh_id() {
        return fh_id;
    }

    public void setFh_id(String fh_id) {
        this.fh_id = fh_id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getFiled_id() {
        return filed_id;
    }

    public void setFiled_id(String filed_id) {
        this.filed_id = filed_id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}