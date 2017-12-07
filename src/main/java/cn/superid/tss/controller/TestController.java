package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DengrongGuan
 * @create 2017-11-28 上午10:39
 **/

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public SimpleResponse test1(@RequestParam String name){
        return SimpleResponse.ok("hello world!");
    }
//    @RequestMapping(value = "/insert", method = RequestMethod.GET)
//    public SimpleResponse insert(@RequestParam String value, @RequestParam long id){
//        SimpleResponse simpleResponse = new SimpleResponse();
//        TestEntity testEntity = new TestEntity();
//        testEntity.setId(id);
//        testEntity.setValue(value);
//        testEntity.save();
////        simpleResponse.setData("Hello! "+name);
//        return simpleResponse;
//    }
//    @RequestMapping(value = "/select", method = RequestMethod.GET)
//    public SimpleResponse select(@RequestParam long id){
//        SimpleResponse simpleResponse = new SimpleResponse();
//        TestEntity testEntity = DStatement.build(TestEntity.class).id(id).selectOne();
//        simpleResponse.setData(testEntity);
//        return simpleResponse;
//    }
//
//    @RequestMapping(value = "/update", method = RequestMethod.GET)
//    public SimpleResponse update(@RequestParam long id, @RequestParam String value){
//        SimpleResponse simpleResponse = new SimpleResponse();
//        TestEntity testEntity = new TestEntity();
//        testEntity.setId(id);
//        testEntity.setValue(value);
//        testEntity.update();
//        return simpleResponse;
//    }
//
//    @RequestMapping(value = "/delete", method = RequestMethod.GET)
//    public SimpleResponse delete(@RequestParam long id){
//        SimpleResponse simpleResponse = new SimpleResponse();
//        DStatement.build(TestEntity.class).id(id).remove();
//        return simpleResponse;
//    }

}
