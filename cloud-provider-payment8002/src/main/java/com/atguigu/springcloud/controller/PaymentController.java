package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {
   @Resource
   PaymentService paymentService;

   @Value("${server.port}")
   String server_port;
   @Resource
   private DiscoveryClient discoveryClient;


   @PostMapping(value="/payment/create")
   public CommonResult create(@RequestBody Payment payment){
      int result=paymentService.create(payment);
      //log.info("*********插入结果*********："+result);

      if(result>0){
         return new CommonResult(200,"插入成功:"+server_port,result);
      }else{
         return new CommonResult(404,"插入失败",result);
      }
   }

   @GetMapping(value="/payment/get/{id}")
   public CommonResult getPaymentById(@PathVariable("id") Long id){
      Payment payment=paymentService.getPaymentById(id);
      //log.info("*********查询结果*********："+payment);
      if(payment!=null){
         return new CommonResult(200,"查询成功:"+server_port,payment);
      }else{
         return new CommonResult(404,"查询失败",payment);
      }
   }

   @GetMapping(value="/payment/discovery")
   public Object discovery(){
      return discoveryClient;
   }

   @GetMapping(value = "/payment/lb")
   public String getPaymentLB(){
      return server_port;
   }
}
