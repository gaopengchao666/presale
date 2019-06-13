package cn.com.service;

import java.util.List;
import java.util.Map;

import cn.com.entity.Price;

/**
 * 房屋价格服务接口
 * @author gaopengchao
 * 2019年5月10日
 */
public interface PriceService
{

    /**
     * 分页查询价格
     */
    List<Price> queryPriceList(Map<String, Object> params);

}
