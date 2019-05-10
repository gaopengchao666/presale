package cn.com.entity;

import lombok.Data;

/**
 * 房屋价格
 * @author gaopengchao
 * 2019年5月10日
 */
@Data
public class Price
{
    private int id;//主键
    private int projectId;//项目id
    private int sort;//序号
    private String roomNo;//房间号
    private float area;//面积
    private float unitPrice;//单价
    private float totalPrice;//总价
}
