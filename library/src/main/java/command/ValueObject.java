package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ValueObject {
    private boolean userAccess;
    private Object value;
    private String message;
    private String error;


    public ValueObject(boolean access, Object value, String message, String error) {
        this.userAccess = access;
        this.value = value;
        this.message = message;
        this.error = error;
    }

    public boolean isUserAccess() {
        return userAccess;
    }

    public void setUserAccess(boolean userAccess) {
        this.userAccess = userAccess;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String generateJSON() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
