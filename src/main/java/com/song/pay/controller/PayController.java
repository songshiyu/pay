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
 * @description:
 * @create: 2020/11/11 09:52:07
 **/
@Controller
@Slf4j
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IPayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId, @RequestParam("amount") BigDecimal amount) {
        Map<String, String> map = new HashMap<>();
        PayResponse payResponse = payService.create(orderId, amount);
        map.put("codeUrl", payResponse.getCodeUrl());
        return new ModelAndView("create", map);
    }

    @PostMapping("/notify")
    public void asyncNotify(@RequestBody String notifyData) {
        log.info("notifyData={}", notifyData);
        payService.asyncNotify(notifyData);

    }
}
