package cn.superid.tss.util;


import cn.superid.common.notification.dto.CommonMessage;
import cn.superid.common.notification.enums.ResourceType;
import cn.superid.msg_producer.dto.MsgGeneratorDTO;
import cn.superid.msg_producer.enums.BusinessOperation;
import com.alibaba.fastjson.JSONObject;

/**
 * @author DengrongGuan
 * @create 2018-01-12 下午2:14
 **/
public class MQUtil {
//    public static boolean send(){
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
//    }
}
