package cpp.common;

public class OutInfo {
    public int code = 1;
    public String status = "success";
    public int id; // 用于返回Id
    public String message = null; // 返回错误信息，留作备用

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static OutInfo success() {
        return new OutInfo();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static OutInfo failure() {
        OutInfo out = new OutInfo();
        out.status = "failure";
        out.code = 0;
        return out;
    }

    public static OutInfo failure(String status ) {
        OutInfo out = new OutInfo();
        out.status = status;
        out.code = 0;
        return out;
    }

    public static OutInfo yes() {
        OutInfo out = new OutInfo();
        out.status = "yes";
        out.code = 1;
        return out;
    }
    public static OutInfo no() {
        OutInfo out = new OutInfo();
        out.status = "no";
        out.code = -1;
        return out;
    }

    public static OutInfo notLogin() {
        OutInfo out = new OutInfo();
        out.status = "您还没有登录";
        out.code = 0;
        return out;
    }
}
