package cn.hzr0523.controller;

import cn.hzr0523.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 *
 * @author yan.kang@hand-china.com
 * @date 2019/10/24 11:07
 */
@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @RequestMapping("/hello")
    public String hello(){
        consumerService.receive();
        return "null";
    }
}
