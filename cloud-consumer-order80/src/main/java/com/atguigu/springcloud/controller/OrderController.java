package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import com.atguigu.springcloud.lb.TestMyRound;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private TestMyRound testMyRound;

    @Autowired
    private DiscoveryClient discoveryClient;

    public static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";
    @PostMapping("/consumer/payment/create")
    public CommonResult<Payment> create(@RequestBody  Payment payment){

//        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
        return restTemplate.postForEntity(PAYMENT_URL+"/payment/create",payment,CommonResult.class).getBody();
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getForObject(@PathVariable("id") Long id){

//        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
        return restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class).getBody();
    }
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getForEntity(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity=restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult(500,"服务器错误");
        }
    }


//    @GetMapping(value="/consumer/payment/lb")
//    public String getLB(){
//        List<ServiceInstance> instances=discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
//        if(instances==null || instances.size()<=0){
//            return null;
//        }
//        ServiceInstance serviceInstance=loadBalancer.instances(instances);
//        URI uri=serviceInstance.getUri();
//        return restTemplate.getForObject(uri+"/payment/lb",String.class);
//    }

    @GetMapping(value="/consumer/payment/lb")
    public String getLB(){
       List<ServiceInstance> serviceInstances=discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
       ServiceInstance serviceInstance=testMyRound.instance(serviceInstances);
       URI uri=serviceInstance.getUri();
       return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }

    @GetMapping(value="/consumer/payment/zipkin")
    public String zipkin(){
       return restTemplate.getForObject(PAYMENT_URL+"/payment/zipkin",String.class);
    }
//    @GetMapping("/consumer/payment/discovery")
//    public DiscoveryClient discovery(){
//        return restTemplate.getForObject(PAYMENT_URL+"/payment/discovery/", DiscoveryClient.class);
//    }
}
