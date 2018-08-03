package cn.bluegod.mycinema.pojo;

public class Cinema {
    private Long id;

    private String c_name;

    private String address;

    private String image;

    private String describes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return c_name;
    }

    public void setName(String c_name) {
        this.c_name = c_name ;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address ;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescribe() {
        return describes;
    }

    public void setDescribe(String describes) {
        this.describes = describes ;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", c_name='" + c_name + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", describes='" + describes + '\'' +
                '}';
    }
}