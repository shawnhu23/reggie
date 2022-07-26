package com.xhu.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhu.reggie.entity.Orders;

public interface OrderService extends IService<Orders> {

    /**
     * place order
     * @param orders
     */
    public void submit(Orders orders);
}
