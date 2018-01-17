package cn.superid.tss.msg.handler;

import cn.superid.msg_producer.dto.MsgGeneratorDTO;
import cn.superid.msg_producer.handler.ParamHandler;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementHandler implements ParamHandler {
    @Override
    public String handle(MsgGeneratorDTO generatorDTO, String key) {
        JSONObject jsonObject = generatorDTO.getCustomParam();
        return (String) jsonObject.get(key);
    }
}
