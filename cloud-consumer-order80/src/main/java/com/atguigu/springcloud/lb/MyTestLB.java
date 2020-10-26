package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyTestLB implements TestMyRound {

    private AtomicInteger atomicInteger =new AtomicInteger(0);

    public final int nextChoice(){
        int current;
        int next;
        do {
            current = atomicInteger.get();
            next = current>=Integer.MAX_VALUE ? 0 :current+1;
        }while (!atomicInteger.compareAndSet(current,next));
        System.out.println("**********next:"+next);
        return next;
    }

    public ServiceInstance instance(List<ServiceInstance> instances){
        int next= nextChoice();
        int index=next % instances.size();
        return instances.get(index);
    }
}
