package cn.bluegod.mycinema.pojo;

import java.util.Date;

public class Movie {
    private Long id;

    private String m_name;

    private String director;

    private String play;

    private String type;

    private String image;

    private Integer timelength;

    private String describes;

    private Integer state;

    private Date starttime;

    private Date endtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String m_name) {
        this.m_name = m_name ;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director ;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play ;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image ;
    }

    public Integer getTimelength() {
        return timelength;
    }

    public void setTimelength(Integer timelength) {
        this.timelength = timelength;
    }

    public String getDescribe() {
        return describes;
    }

    public void setDescribe(String describes) {
        this.describes = describes ;
    }

    public Integer getStatus() {
        return state;
    }

    public void setStatus(Integer state) {
        this.state = state;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", m_name='" + m_name + '\'' +
                ", director='" + director + '\'' +
                ", play='" + play + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", timelength=" + timelength +
                ", describes='" + describes + '\'' +
                ", state=" + state +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                '}';
    }
}