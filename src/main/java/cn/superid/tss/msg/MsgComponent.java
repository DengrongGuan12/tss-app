package cn.superid.tss.msg;

import cn.superid.common.notification.dto.CommonMessage;
import cn.superid.common.notification.enums.MsgType;
import cn.superid.common.notification.enums.ResourceType;
import cn.superid.msg_producer.dto.MsgGeneratorDTO;
import cn.superid.msg_producer.process.ReceiverMsgProcessor;
import cn.superid.tss.constant.MsgTemplateType;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author DengrongGuan
 * @create 2018-01-16 下午2:08
 **/
@Component
@EnableBinding(TssSource.class)
public class MsgComponent {
    private static final Logger logger = LoggerFactory.getLogger(MsgComponent.class);
    @Autowired
    private ReceiverMsgProcessor receiverMsgProcessor;
    @Autowired
    private TssSource tssSource;

    public CommonMessage genCommonMsg(long affairId, long roleId, List<Long> receiverIds, MsgType msgType, ResourceType resourceType, long resourceId, MsgTemplateType msgTemplateType, JSONObject jsonObject){
        MsgGeneratorDTO msgGeneratorDTO = new MsgGeneratorDTO.Builder(
                affairId, roleId, receiverIds, msgType,
                resourceType, resourceId, msgTemplateType.getValue()).customParam(jsonObject).build();
        CommonMessage handledMsg = receiverMsgProcessor.processMsgTemplate(msgGeneratorDTO);
        return handledMsg;
    }

    public void sendMsg(CommonMessage commonMessage){
        boolean sendMsgResult = tssSource.output().send(org.springframework.integration.support.MessageBuilder.withPayload(commonMessage).build(), 3000);   //设置超时为3s
        if (!sendMsgResult) {
            logger.error("发送消息失败："+commonMessage.getContent());
        }
    }
}
