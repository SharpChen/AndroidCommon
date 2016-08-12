package cn.opensrc.comnlib.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author:       sharp
 * Created on:   8/6/16 4:01 PM
 * Description:  JSON数据操作
 * Revisions:
 */
public final class JsonUtils {
    private JsonUtils() {
    }

    /**
     * @param jsonString JSONObject String
     * @return JSONObject
     */
    public static JSONObject buildJObjFromString(String jsonString) {

        JSONObject backJObj = new JSONObject();

        if (jsonString == null || "".equals(jsonString) || jsonString.trim().length() == 0)
            return backJObj;

        try {

            backJObj = new JSONObject(jsonString);

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return backJObj;

    }

    /**
     * @param jsonString JSONArray String
     * @return JSONArray
     */
    public static JSONArray buildJArrayFromString(String jsonString) {

        JSONArray backJArray = new JSONArray();

        if (jsonString == null || "".equals(jsonString) || jsonString.trim().length() == 0)
            return backJArray;

        try {

            backJArray = new JSONArray(jsonString);

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return backJArray;

    }

    /**
     * @param jsonObject JSONObject
     * @param key        the key in JSONObject
     * @return value
     */
    public static String getValueFromJObj(JSONObject jsonObject, String key) {

        String backStr = "";

        try {

            if (jsonObject != null && jsonObject.has(key) && jsonObject.get(key) != null)
                backStr = jsonObject.get(key).toString();

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return backStr;

    }

    /**
     * @param jsonArray JSONArray
     * @param index     the value index in JSONArray
     * @return value
     */
    public static String getValueFromJArray(JSONArray jsonArray, int index) {

        String backStr = "";

        try {

            if (jsonArray != null && jsonArray.get(index) != null && jsonArray.length() > index)
                backStr = jsonArray.get(index).toString();

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return backStr;

    }

    /**
     * @param jsonObject JSONObject
     * @param key        the key in JSONObject
     * @return JSONObject
     */
    public static JSONObject getJObjFromJObj(JSONObject jsonObject, String key) {

        JSONObject backJObj = new JSONObject();

        try {

            if (jsonObject != null && jsonObject.has(key) && jsonObject.getJSONObject(key) != null)
                backJObj = jsonObject.getJSONObject(key);

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return backJObj;

    }

    /**
     * @param jsonArray JSONArray
     * @param index     the index in JSONArray
     * @return JSONObject
     */
    public static JSONObject getJObjFromJArray(JSONArray jsonArray, int index) {

        JSONObject backJObj = new JSONObject();

        try {

            if (jsonArray != null && jsonArray.length() > index && jsonArray.getJSONObject(index) != null)
                backJObj = jsonArray.getJSONObject(index);

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return backJObj;

    }

    /**
     * @param jsonObject JSONObject
     * @param key        the key in JSONObject
     * @return JSONArray
     */
    public static JSONArray getJArrayFromJObj(JSONObject jsonObject, String key) {

        JSONArray backJArray = new JSONArray();

        try {

            if (jsonObject != null && jsonObject.has(key) && jsonObject.getJSONArray(key) != null)
                backJArray = jsonObject.getJSONArray(key);

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return backJArray;

    }

    /**
     * @param jsonObject JSONObject
     * @param key        the key to put
     * @param value      the value to put
     */
    public static void putValue2JObj(JSONObject jsonObject, String key, Object value) {

        try {

            if (jsonObject != null && key != null && value != null)
                jsonObject.put(key, value);

        } catch (JSONException e) {

            e.printStackTrace();

        }

    }

    /**
     * @param jsonArray JSONArray
     * @param value     the value to put
     */
    public static void putValue2JArray(JSONArray jsonArray, Object value) {

        if (jsonArray != null && value != null)
            jsonArray.put(value);

    }

}
