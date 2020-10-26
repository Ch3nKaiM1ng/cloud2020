package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "-------PaymentFallbackService paymentInfo_OK fall back ,o(╥﹏╥)o";
    }

    @Override
    public String paymentInfo_Error(Integer id) {
        return "-------PaymentFallbackService paymentInfo_Error fall back ,o(╥﹏╥)o";
    }
}
