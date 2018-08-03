package cn.bluegod.mycinema.pojo;

import java.util.Date;

public class Orders {
    private Long id;

    private String filedId;

    private String uId;

    private Integer ticketNumber;

    private Integer money;

    private Date startTime;

    private Date updateTime;

    private Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFiledId() {
        return filedId;
    }

    public void setFiledId(String filedId) {
        this.filedId = filedId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Integer getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return state;
    }

    public void setStatus(Integer state) {
        this.state = state;
    }
}