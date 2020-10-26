package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_Ok(Integer id){
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_Ok,id"+id;
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
    })
    public String paymentInfo_Error(Integer id){
        int timeNumber=5;
//        int age=10/0;
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_Error,id"+id+"，耗时"+timeNumber+"秒钟";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池："+Thread.currentThread().getName()+" 8001系统繁忙或者运行报错，请稍后再试,id"+id+"，o(╥﹏╥)o";
    }


    //===========服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallbakc",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//失败率达到多少跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id<0){
            throw new RuntimeException("***id不能为负数");
        }
        String serialNumber=IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号:"+serialNumber;
    }
    public String paymentCircuitBreaker_fallbakc(@PathVariable("id") Integer id){
        return "id不能为负数，请稍后再试，o(╥﹏╥)o id="+id;
    }
}
