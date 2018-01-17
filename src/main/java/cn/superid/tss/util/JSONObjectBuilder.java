package cn.superid.tss.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author DengrongGuan
 * @create 2018-01-17 下午2:24
 **/
public class JSONObjectBuilder {
    private JSONObject jsonObject = new JSONObject();
    public JSONObjectBuilder put(String key, Object value){
        jsonObject.put(key, value);
        return this;
    }
    public JSONObjectBuilder(){}
    public JSONObjectBuilder(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
