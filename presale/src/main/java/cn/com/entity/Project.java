package cn.com.entity;

import java.util.List;

import lombok.Data;

/**
 * 项目信息
 * @author gaopengchao
 * 2019年5月10日
 */
@Data
public class Project
{
    private int id;//项目id
    private String batch;//所属批次
    private String projectName;//项目名称
    private String saleable;//申报楼栋
    private String floors;//总层数 32,32
    private String address;//地址
    private String decoration;//装修情况
    private String priceUrl;//项目价格url
    private List<Price> prices;//项目中所有的价格
}
