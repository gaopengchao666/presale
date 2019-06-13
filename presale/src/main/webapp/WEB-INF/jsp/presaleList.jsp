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
<title>预售证列表</title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery.js"></script>
<script src="js/template-web.js"></script>
<script src="js/pintuer.js"></script>
<script src="js/common.js"></script>
<script src="js/presaleList.js"></script>
</head>
<body>
    <!-- 预售证模块 -->
    <div class="panel admin-panel">
        <div class="panel-head">
            <strong class="icon-reorder"> 内容列表</strong>
        </div>
        <div class="padding border-bottom">
            <ul class="search" style="padding-left: 10px;">
                <li>预售证号：<input type="text" placeholder="请输入预售证号" name="certificate" class="input"
                    style="width: 150px; line-height: 17px; display: inline-block" /> &nbsp;&nbsp; 项目名称：<input
                    type="text" placeholder="请输入项目名称" name="projectName" class="input"
                    style="width: 150px; line-height: 17px; display: inline-block" />
                </li>
                <li> 
                    <a href="javascript:void(0)" class="button border-main icon-search"> 搜索</a>
                    <a href="javascript:void(0)" class="button border-main icon-clear"> 清除</a>
                </li>
            </ul>
        </div>
        <table class="table table-hover text-center">
            <tr>
                <th width=10%>预售证号</th>
                <th width=20%>项目名称</th>
                <th width=20%>位置</th>
                <th width=20%>开发商</th>
                <th width=20%>可售楼栋</th>
                <th width=10%>发证日期</th>
            </tr>
            <tbody class="presaleTbody">
            </tbody>
            <tr>
                <td colspan="8">
                    <div class="pagelist" id="presalePage"></div>
                </td>
            </tr>
        </table>
    </div>
<script id="presaleTemp" type="text/html">
{{each list obj index}}
    <tr class='listTableText list_list'>
    <td>{{obj.certificate}}</td>
    <td>{{obj.projectName}}</td>
    <td>{{obj.location}}</td>
    <td>{{obj.developer}}</td>
    <td>{{obj.saleable}}</td>
    <td>{{obj.createTime}}</td>
    </tr>
{{/each}}
</script>
</body>
</html>