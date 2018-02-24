package bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by anqilin on 18/1/12.
 */

public class Feedback extends BmobObject {
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
