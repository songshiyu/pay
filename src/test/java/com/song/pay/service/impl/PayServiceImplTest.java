package com.song.pay.service.impl;

import com.song.pay.PayApplicationTests;
import com.song.pay.service.IPayService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;


/**
 * @author songshiyu
 * @date 2020/1/11 21:30
 **/
public class PayServiceImplTest extends PayApplicationTests {

    @Autowired
    private IPayService payService;

    @Test
    public void create(){
        //BigDecimal.valueOf(0.01) ==> 等同于 new BigDecimal("0.01")
        //千万不能用new BigDecimal(0.01) 精度会出问题
        payService.create("123456789song", BigDecimal.valueOf(0.01));
    }
}