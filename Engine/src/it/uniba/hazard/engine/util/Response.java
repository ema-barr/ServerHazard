package src.com.serverhazard.turn;

import java.util.HashMap;

/**
 * Created by maccn on 28/12/2016.
 */
public class Response {

    private int code;
    private HashMap<String, String> params;

    public Response () {
        params = new HashMap<String, String>();
    }

    public Response (int c) {
        code = c;
        params = new HashMap<String, String>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public void addParam (String k, String v) {
        params.put(k, v);
    }

    public String getParamValue (String k) {
        return params.get(k);
    }
}
