package cn.com.dao;

import java.util.List;
import java.util.Map;

import cn.com.entity.Presale;

/**
 * @author gaopengchao
 * 2019年3月27日
 */
public interface PresaleMapper
{
    /**
     * 查询预售证
     * @param params 
     * @return
     */
    List<Presale> queryPresales(Map<String, Object> params);
    /**
     * 添加预售证
     * @param presales
     * @return
     */
    int insertPresales(List<Presale> presales);
    
    /**
     * 查询最大预售证号
     */
    String queryMaxCertificate();
}
