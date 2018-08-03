package cn.bluegod.mycinema.result;

/**
 * @description: 逻辑层返回视图层的结果类
 * @author: Mr.Fu
 * @create: 2018-07-26 09:51
 * @Version V1.0
 */
public class BGResult {
    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static BGResult build(Integer status, String msg, Object data) {
        return new BGResult(status, msg, data);
    }

    public static BGResult ok(Object data) {
        return new BGResult(data);
    }

    public static BGResult ok() {
        return new BGResult(null);
    }

    public BGResult() {

    }

    public static BGResult build(Integer status, String msg) {
        return new BGResult(status, msg, null);
    }

    public BGResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public BGResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
