package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

   @Resource
   private PaymentHystrixService paymentHystrixService;

   @Value("${server.port}")
   private String Serverport;

   @GetMapping("/consumer/payment/hystrix/ok/{id}")
   //@HystrixCommand
   public String paymentInfo_Ok(@PathVariable("id") Integer id){
      return paymentHystrixService.paymentInfo_ok(id);
   }


   @GetMapping("/consumer/payment/hystrix/timeout/{id}")
  /* @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
           @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")  //3秒钟以内就是正常的业务逻辑
   })*/
  //@HystrixCommand
   public String paymentInfo_TimOut(@PathVariable("id") Integer id){
      return paymentHystrixService.paymentInfo_TimeOut(id);
   }

   public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id){
      return "我是消费者80，对付支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,(┬＿┬)";
   }

   public String payment_Global_FallbackMethod(){
      return "default handler:我是消费者80，对付支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,(┬＿┬)";
   }

}
