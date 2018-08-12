package cn.exrick.manager.dto;

import cn.exrick.manager.pojo.TbLog;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public class TbLogDto extends TbLog {

    /**
     * 设置请求参数
     *
     * @param paramMap
     */
    public void setMapToParams(Map<String, String[]> paramMap) {
        if (paramMap == null) {
            return;
        }
        Map<String, Object> params = new HashMap<>();
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {

            String key = param.getKey();
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            String obj = StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "你是看不见我的" : paramValue;
            params.put(key, obj);
        }
        this.setRequestParam(new Gson().toJson(params));
    }
}
