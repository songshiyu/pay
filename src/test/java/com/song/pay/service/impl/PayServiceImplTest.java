package com.song.pay.service.impl;

import com.lly835.bestpay.model.PayResponse;
import com.song.pay.PayApplicationTests;
import com.song.pay.service.IPayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;


/**
 * @description:
 * @create: 2020/11/11 09:34:44
 **/
public class PayServiceImplTest extends PayApplicationTests {

    @Autowired
    private IPayService payService;

    @Test
    public void create() {
        PayResponse payResponse = payService.create("6805711-2342341213", new BigDecimal("0.01"));
        System.out.println(payResponse);
    }
}
