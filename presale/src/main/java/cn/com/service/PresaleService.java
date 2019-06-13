package cn.com.service;

import java.util.List;
import java.util.Map;

import cn.com.entity.Presale;

/**
 * @author gaopengchao
 * 2019年3月27日
 */
public interface PresaleService
{
    public List<Presale> queryPresales(Map<String, Object> params);
    
    public List<Presale> getPresales(String url);

    /**
     * @param sales
     */
    public void insertPresales(List<Presale> sales);
}
