package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DengrongGuan
 * @create 2017-12-12 下午2:41
 **/
@RestController
@RequestMapping("/api/user")
public class UserController {

    @ApiOperation(value = "个人中心获取", response = Signature.class, notes = "verb, contentType必传")
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public SimpleResponse test1(@RequestParam String name){
        return SimpleResponse.ok("hello world!");
    }

}
