package cn.com.entity;

import lombok.Data;

/**
 * @author gaopengchao
 * 2019年3月27日
 */
@Data
public class PreSale
{
    private int id;//自增主键
    private String certificate;//预售证号
    private String projectName;//项目名称
    private String location;//地理位置
    private String developer;//开发商
    private String saleable;//可售楼栋
    private String createTime;//发证时间
}
