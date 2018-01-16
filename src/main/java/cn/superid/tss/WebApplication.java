package cn.superid.tss;

import cn.superid.common.rest.client.EnableCommonClient;
import cn.superid.id_client.core.EnableIdGenerator;
import cn.superid.msg_producer.annotation.EnableMsgProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * service 状态： 1 mock 2 已经向本体提接口但本体尚未提供 3 本体完成接口但尚未实际使用
 * @author DengrongGuan
 * @create 2017-12-07 上午10:57
 **/
@SpringBootApplication
@EnableCommonClient
@EnableHystrix
@EnableHystrixDashboard
@EnableDiscoveryClient
@EnableIdGenerator
@EnableAspectJAutoProxy
@EnableMsgProducer
public class WebApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        LOGGER.info("tss service is running!");
    }
}
