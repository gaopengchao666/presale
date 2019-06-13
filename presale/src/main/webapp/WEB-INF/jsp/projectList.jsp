<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html lang="zh-cn">
<base href="<%=basePath%>">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title>项目列表</title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery.js"></script>
<script src="js/template-web.js"></script>
<script src="js/pintuer.js"></script>
<script src="js/common.js"></script>
<script src="js/projectList.js"></script>
</head>
<body>
    <!-- 项目模块 -->
    <div class="panel admin-panel" style="display:block">
        <div class="panel-head">
            <strong class="icon-reorder"> 内容列表</strong>
        </div>
        <div class="padding border-bottom">
            <ul class="search" style="padding-left: 10px;">
                <li>批次号：<input type="text" placeholder="请输入批次号" name="batch" class="input"
                    style="width: 250px; line-height: 17px; display: inline-block" /> &nbsp;&nbsp; 项目：<input
                    type="text" placeholder="请输入项目" name="projectName" class="input"
                    style="width: 250px; line-height: 17px; display: inline-block" />&nbsp;&nbsp; 装修：<select
                    name="decoration" class="input" style="width: 60px; line-height: 17px; display: inline-block">
                        <option value="">选择</option>
                        <option value="毛坯">毛坯</option>
                        <option value="精装">精装</option>
                </select>
                </li>
                <li>地址：<input type="text" placeholder="请输入搜索地址" name="address" class="input"
                    style="width: 250px; line-height: 17px; display: inline-block" /> 
                    <a href="javascript:void(0)" class="button border-main icon-search"> 搜索</a>
                    <a href="javascript:void(0)" class="button border-main icon-clear"> 清除</a>
                </li>
            </ul>
        </div>
        <table class="table table-hover text-center">
            <tr>
                <th width=20%>项目名称</th>
                <th width=20%>批次号</th>
                <th width=20%>可售楼栋</th>
                <th width=20%>总高</th>
                <th width=10%>地址</th>
                <th width=10%>装修情况</th>
            </tr>
            <tbody class="projectTbody">
            </tbody>
            <tr>
                <td colspan="8">
                    <div class="pagelist" id="projectPage"></div>
                </td>
            </tr>
        </table>
    </div>
    <!-- 项目价格模块 -->
    <div class="panel admin-panel price" style="display:none">
        <div class="panel-head">
            <strong class="icon-reorder"> 内容列表</strong>
        </div>
        <div class="padding border-bottom">
            <ul class="search" style="padding-left: 10px;">
                <li>序号：<input type="text" name="sort" class="input"
                    style="width: 250px; line-height: 17px; display: inline-block" /> &nbsp;&nbsp; 房间号：<input
                    type="text" placeholder="请输入房间号" name="roomNo" class="input"
                    style="width: 250px; line-height: 17px; display: inline-block" />
                </li>
                <li>
                    <a href="javascript:void(0)" class="button border-main icon-search"> 搜索</a>
                    <a href="javascript:void(0)" class="button border-main icon-clear"> 清除</a>
                    <a class="button button-little bg-green" onclick="ProjectList.viewProject()"><span class="icon-power-off"></span>返回项目</a>
                </li>
            </ul>
        </div>
        <table class="table table-hover text-center">
            <tr>
                <th>序号</th>
                <th>房间号</th>
                <th>面积</th>
                <th>单价(元/m²)</th>
                <th>总价(元)</th>
            </tr>
            <tbody class="priceTbody">
            </tbody>
            <tr>
                <td colspan="8">
                    <div class="pagelist" id="pricePage"></div>
                </td>
            </tr>
        </table>
    </div>
<script id="projectTemp" type="text/html">
{{each list obj index}}
    <tr class='listTableText list_list'>
    <td title='{{obj.projectName}}'><a class='cursor' onclick="ProjectList.viewProjectPrices({{obj.id}})">{{obj.projectName}}</a></td>
    <td title='{{obj.batch}}'>{{obj.batch}}</td>
    <td title='{{obj.saleable}}'>{{obj.saleable}}</td>
    <td title='{{obj.floors}}'>{{obj.floors}}</td>
    <td title='{{obj.address}}'>{{obj.address}}</td>
    <td title='{{obj.decoration}}'>{{obj.decoration}}</td>
    </tr>
{{/each}}
</script>
<script id="priceTemp" type="text/html">
{{each list obj index}}
    <tr class='listTableText list_list'>
    <td>{{obj.sort}}</td>
    <td>{{obj.roomNo}}</td>
    <td>{{obj.area}}</td>
    <td>{{obj.unitPrice}}</td>
    <td>{{obj.totalPrice}}</td>
    </tr>
{{/each}}
</script>
</body>
</html>