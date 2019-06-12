window.ProjectList = (function($,module){
    _pagelist = 'pagelist';
    
    /**
     * 初始化
     */
    function init(){
        initPageInfo();
        bindEvent();
    }
    
    /**
     * 绑定事件
     */
    function bindEvent(){
        $(".icon-search").click(function(){
            var pageId = "pagelist";
            // 筛选数据获取
            var paramData = CommonUtils.serializeToObject($(".search"));
            // 分页参数 增加筛选条件
            jQuery.each(PageUtils._pageParamArr, function() {
                if (this['element'] == pageId) {
                    this['data'] = paramData;
                }
            });
            // 分页跳转第一页
            PageUtils.pageClick(1, pageId);
        });
    }
    
    /**
     * 加载页面
     */
    function initPageInfo(){
        //更新page信息
        var url = 'project/queryProjectList';
        CommonUtils.getAjaxData({url:url,type:'GET'},function(data){
            showProjectList(data);
            PageUtils.refreshPageInfo({element:_pagelist,url : url,callback : showProjectList},data['page']);
        });
    }
    
    /**
     * 显示项目列表
     */
    function showProjectList(data){
        //显示任务模板列表
        var html = template('projectTemp', data);
        $(".projectTbody").html("").html(html);
    }
    
    
    module.init = init;
    return module;
}($, window.ProjectList || {}));
$(function() {
    ProjectList.init();
});