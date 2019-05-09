package cn.com.service;

import java.util.List;

import cn.com.entity.PreSale;

/**
 * @author gaopengchao
 * 2019年3月27日
 */
public interface PreSaleService
{
    public List<PreSale> queryPreSales();
    
    public List<PreSale> getPreSales(String url);

    /**
     * @param sales
     */
    public void insertPreSales(List<PreSale> sales);
}
