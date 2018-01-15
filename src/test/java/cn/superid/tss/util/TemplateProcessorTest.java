package cn.superid.tss.util;

import cn.superid.common.notification.dto.CommonMessage;
import cn.superid.common.notification.enums.MsgType;
import cn.superid.common.notification.enums.ResourceType;
import cn.superid.msg_producer.dto.MsgGeneratorDTO;
import cn.superid.msg_producer.process.ReceiverMsgProcessor;
import cn.superid.tss.msg.TssSource;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengrongGuan
 * @create 2018-01-12 下午5:34
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableBinding(TssSource.class)
public class


TemplateProcessorTest {
    @Autowired
    private ReceiverMsgProcessor receiverMsgProcessor;
    @Autowired
    TssSource tssSource;

    @Test
    public void testProcessTemplate() throws Exception {
//        MsgGeneratorDTO msgGeneratorDTO = new MsgGeneratorDTO();
//        msgGeneratorDTO.setBusinessOperation(BusinessOperation.TSS_REMARK_GROUP_ACTIVITY_PUBLISH);
//        msgGeneratorDTO.setSenderRoleId(1021603L);
//        msgGeneratorDTO.setAffairId(231403L);
//        msgGeneratorDTO.setResourceType(ResourceType.ANNOUNCEMENT);
//        msgGeneratorDTO.setResourceId(1000L);
//        JSONObject jsonObject = new JSONObject();
//        msgGeneratorDTO.setCustomParam(jsonObject);
//        CommonMessage handledMsg = receiverMsgProcessor.processMsgTemplate(msgGeneratorDTO);
//        System.out.println(handledMsg.getContent());
//        Assert.assertNotNull(handledMsg.getContent());
        List<Long> receiverIds = new ArrayList<>();
        receiverIds.add(906212L);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("remark","还行阿迪是会计法开始的风景");
        MsgGeneratorDTO msgGeneratorDTO = new MsgGeneratorDTO.Builder(
                130007L, 1131604L, receiverIds, MsgType.INVITATION,
                ResourceType.ANNOUNCEMENT, 1000L, "TSS_REMARK_GROUP_ACTIVITY_PUBLISH").customParam(jsonObject).build();
        CommonMessage handledMsg = receiverMsgProcessor.processMsgTemplate(msgGeneratorDTO);
        System.out.println(handledMsg.getContent());
        boolean sendMsgResult = tssSource.output().send(org.springframework.integration.support.MessageBuilder.withPayload(handledMsg).build(), 3000);   //设置超时为3s
        if (!sendMsgResult) {
            System.out.println("send message error");
        }
    }
}
