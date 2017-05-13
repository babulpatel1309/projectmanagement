package Model;

/**
 * Created by Babul Patel on 15/04/2017.
 */

public class ProjectModel {
    int id;
    String name;
    String email;
    String status;
    String no_member;
    String desc;
    String name_member;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNo_member() {
        return no_member;
    }

    public void setNo_member(String no_member) {
        this.no_member = no_member;
    }

    public String getName_member() {
        return name_member;
    }

    public void setName_member(String name_member) {
        this.name_member = name_member;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
