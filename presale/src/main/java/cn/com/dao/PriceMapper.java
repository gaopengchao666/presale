package cn.com.dao;

import java.util.List;
import java.util.Map;

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
    void insertPrices(List<Price> prices);

    /**
     * 分页查询项目价格
     */
    List<Price> queryPriceList(Map<String, Object> params);
}
