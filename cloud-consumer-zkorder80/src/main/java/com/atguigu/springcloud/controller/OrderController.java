package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderController {

   private static final String INVOME_URL="http://cloud-provider-payment";

   @Resource
   private RestTemplate restTemplate;

   @GetMapping("/consumer/payment/zk")
   public String  zk(){
      String result=restTemplate.getForObject(INVOME_URL +"/payment/zk",String.class);
      return result;
   }
}
