package cn.com.dao;

import java.util.List;

import cn.com.entity.PreSale;

/**
 * @author gaopengchao
 * 2019年3月27日
 */
public interface PreSaleMapper
{
    /**
     * 查询预售证
     * @return
     */
    List<PreSale> queryPreSales();
    /**
     * 添加预售证
     * @param presales
     * @return
     */
    int insertPreSales(List<PreSale> presales);
    
    /**
     * 查询最大预售证号
     */
    String queryMaxCertificate();
}
