package cn.com.dao;

import java.util.List;

import cn.com.entity.Price;

/**
 * @author gaopengchao
 * 2019年6月5日
 */
public interface PriceMapper
{

    /**
     * 添加价格信息
     */
    void insertProces(List<Price> prices);
    
}
