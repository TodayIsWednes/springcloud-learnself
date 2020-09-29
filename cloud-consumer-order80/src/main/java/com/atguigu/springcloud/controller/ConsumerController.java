package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
public class ConsumerController {
  // public static final String PAYMENT_URL="http://localhost:8001";
  public static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";
   @Resource
   private RestTemplate restTemplate;
   @Resource
   private LoadBalancer loadBalancer;

   @Resource
   private DiscoveryClient discoveryClient;
   @GetMapping("/consumer/payment/create")
   public CommonResult<Payment> create(Payment payment){
      return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
   }

   @GetMapping("/consumer/payment/get/{id}")
   public CommonResult<Payment> getPaymentById(@PathVariable("id")Long id){
      return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
   }

   @GetMapping("/consumer/payment/get2/{id}")
   public CommonResult<Payment> getPaymentById2(@PathVariable("id")Long id){
      ResponseEntity<CommonResult> result=restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
      if(result.getStatusCode().is2xxSuccessful()){
         return result.getBody();
      }else{
         return new CommonResult<>(444,"调用失败！");
      }
   }

   @GetMapping("/consumer/payment/create2")
   public CommonResult<Payment> create2(Payment payment){
      ResponseEntity<CommonResult> result=restTemplate.postForEntity(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
      if(result.getStatusCode().is2xxSuccessful()){
         return result.getBody();
      }else{
         return new CommonResult<>(444,"调用失败");
      }

   }

   @GetMapping(value="/comsumer/payment/lb")
   public String getPaymentLB(){
      List<ServiceInstance> instances=discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
      if(instances==null||instances.size()<=0){
         return null;
      }
      ServiceInstance serviceInstance=loadBalancer.instances(instances);
      URI uri=serviceInstance.getUri();
      return restTemplate.getForObject(uri+"/payment/lb",String.class);
   }

//================>zipkin+sleuth
   @GetMapping(value="/consumer/payment/zipkin")
   public String paymentZipkin(){
      String result=restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin",String.class);
      return result;
   }

}
