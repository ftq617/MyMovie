package cn.bluegod.mycinema.pojo;

import java.util.Date;

public class User {
    private Long id;

    private String phone;

    private String name;

    private String password;

    private String head_image;

    private String c_id;

    private int state;

    private Long money;

    private Date starttime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadImage() {
        return head_image;
    }

    public void setHeadImage(String headImage) {
        this.head_image = headImage;
    }

    public String getcId() {
        return c_id;
    }

    public void setcId(String c_id) {
        this.c_id = c_id;
    }

    public int getStatus() {
        return state;
    }

    public void setStatus(int state) {
        this.state = state;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Date getStartTime() {
        return starttime;
    }

    public void setStartTime(Date startTime) {
        this.starttime = startTime;
    }
}