<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title>商品房查询</title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery.js"></script>
</head>
<body style="background-color: #f2f9fd;">
    <div class="header bg-main">
        <div class="logo margin-big-left fadein-top">
            <h1>
                <img src="images/y.jpg" class="radius-circle rotate-hover" height="50" alt="" />商品房查询
            </h1>
        </div>
    </div>
    <div class="leftnav">
        <div class="leftnav-title">
            <strong><span class="icon-list"></span>菜单列表</strong>
        </div>
        <h2>
            <span class="icon-user"></span>统计查询
        </h2>
        <ul style="display: block">
            <li><a href="project/queryProjects" target="right"><span class="icon-caret-right"></span>近期房源</a></li>
            <li><a href="presale/queryPresales" target="right"><span class="icon-caret-right"></span>预售证</a></li>
        </ul>
    </div>
    <script type="text/javascript">
        $(function() {
            $(".leftnav h2").click(function() {
                $(this).next().slideToggle(200);
                $(this).toggleClass("on");
            })
            $(".leftnav ul li a").click(function() {
                $("#a_leader_txt").text($(this).text());
                $(".leftnav ul li a").removeClass("on");
                $(this).addClass("on");
            })
        });
    </script>
    <ul class="bread">
        <li><a href="{:U('Index/info')}" target="right" class="icon-home"> 首页</a></li>
        <li><a href="##" id="a_leader_txt">近期房源</a></li>
    </ul>
    <div class="admin">
        <iframe scrolling="auto" rameborder="0" src="project/queryProjects" name="right" width="100%" height="100%"></iframe>
    </div>
</body>
</html>