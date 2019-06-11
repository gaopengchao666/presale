window.ProjectList = (function($,module){
    _materialPage = 'materialPage';
    
    /**
     * 初始化
     */
    function init(){
        initPageInfo();
    }
    
    /**
     * 加载页面
     */
    function initPageInfo(){
        //更新page信息
        var url = 'project/queryProjectList';
        CommonUtils.getAjaxData({url:url,type:'GET'},function(data){
            showProjectList(data);
            PageUtils.refreshPageInfo({element:_materialPage,url : url,callback : showProjectList},data['page']);
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