package cn.com.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.dao.PriceMapper;
import cn.com.entity.Price;
import cn.com.service.PriceService;

/**
 * @author gaopengchao
 * 2019年5月10日
 */
@Service
public class PriceServiceImpl implements PriceService
{
    @Resource
    private PriceMapper priceMapper;
    
    /**
     * 分页查询项目价格
     */
    @Override
    public List<Price> queryPriceList(Map<String, Object> params)
    {
        return priceMapper.queryPriceList(params);
    }

}
