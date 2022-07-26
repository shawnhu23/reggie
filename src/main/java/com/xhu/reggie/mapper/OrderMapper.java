package com.xhu.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhu.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
