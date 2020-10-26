package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Gloabl_FallBackMethod" )
public class OrderController {
    @Resource
    PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfo_OK(id);
    }

    @GetMapping("/consumer/payment/hystrix/Error/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutHandler",commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
//    })
    @HystrixCommand
    public String paymentInfo_Error(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfo_Error( id);
    }

    public String paymentInfoTimeOutHandler(@PathVariable("id") Integer id){
        return "线程池："+Thread.currentThread().getName()+" 80系统繁忙或者运行报错，请稍后再试,o(╥﹏╥)o";
    }

    public String payment_Gloabl_FallBackMethod(){
        return "Global异常处理信息，请稍后再试，o(╥﹏╥)o";
    }
}
