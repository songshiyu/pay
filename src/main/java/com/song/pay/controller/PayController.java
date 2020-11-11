package com.song.pay.controller;

import com.lly835.bestpay.model.PayResponse;
import com.song.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
public class PayController {

    @Autowired
    private IPayService payService;

    @GetMapping("/create")
    public ModelAndView create() {
        Map<String, String> map = new HashMap<>();
        PayResponse payResponse = payService.create("6805711-2342341213", new BigDecimal("0.01"));
        map.put("codeUrl", payResponse.getCodeUrl());
        return new ModelAndView("create", map);
    }
}
