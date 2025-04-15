package api.model.response;

import api.model.request.Support;
import api.model.request.User;

public class UserResponse {

    private User data;
    private Support support;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }


}
