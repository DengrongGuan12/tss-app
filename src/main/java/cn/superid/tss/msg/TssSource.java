package cn.superid.tss.msg;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TssSource {
    String OUTPUT = "msg-proto";
    @Output(OUTPUT)
    MessageChannel output();
}
