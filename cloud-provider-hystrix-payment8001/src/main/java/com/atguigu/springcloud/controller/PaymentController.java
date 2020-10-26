package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.ws.Service;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    public PaymentService paymentService;

   @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result=paymentService.paymentInfo_Ok(id);
        log.info(result+"");
        return result;
    }
    @GetMapping("/payment/hystrix/Error/{id}")
    public String paymentInfo_Error(@PathVariable("id") Integer id){
        String result=paymentService.paymentInfo_Error(id);
        log.info(result+"");
        return result;
    }

    @GetMapping("payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result=paymentService.paymentCircuitBreaker(id);
        log.info(result+"");
        return result;
    }

}
