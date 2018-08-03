package cn.bluegod.mycinema.pojo;

public class Filmhall {
    private Long id;

    private String c_id;

    private Integer seatnumber;

    private String seatinfo;


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

    public Integer getSeatnumber() {
        return seatnumber;
    }

    public void setSeatnumber(Integer seatnumber) {
        this.seatnumber = seatnumber;
    }

    public String getSeatinfo() {
        return seatinfo;
    }

    public void setSeatinfo(String seatinfo) {
        this.seatinfo = seatinfo == null ? null : seatinfo.trim();
    }

}