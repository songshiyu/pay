package com.song.pay.controller;

import com.lly835.bestpay.model.PayResponse;
import com.song.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author songshiyu
 * @date 2020/1/12 19:54
 **/
@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private IPayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount){
        Map map = new HashMap();
        PayResponse response = payService.create(orderId, amount);
        map.put("codeUrl",response.getCodeUrl());
        return new ModelAndView("create",map);
    }

    @PostMapping("/notify")
    @ResponseBody
    public void asyncNotify(@RequestBody String notifyData){
        payService.asyncNotify(notifyData);
    }

}
