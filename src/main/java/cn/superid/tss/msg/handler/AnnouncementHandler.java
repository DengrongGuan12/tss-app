package cn.superid.tss.msg.handler;

import cn.superid.msg_producer.dto.MsgGeneratorDTO;
import cn.superid.msg_producer.handler.ParamHandler;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementHandler implements ParamHandler {
    @Override
    public String handle(MsgGeneratorDTO generatorDTO, String key) {
        return "custom test";
    }
}
