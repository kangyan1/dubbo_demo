package cn.hzr0523.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 *
 * @author yan.kang@hand-china.com
 * @date 2019/10/24 15:44
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String get(){
        return "test sucess";
    }
}
