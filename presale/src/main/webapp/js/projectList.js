window.ProjectList = (function($,module){
    
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
    }
    
    /**
     * 加载页面
     */
    function initPageInfo(){
        //更新page信息
        var url = 'project/queryProjectList';
        CommonUtils.getAjaxData({url:url,type:'GET'},function(data){
            showProjectList(data);
            PageUtils.refreshPageInfo({element:'projectPage',url : url,callback : showProjectList},data['page']);
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
    
    /**
     * 查看项目页面
     */
    function viewProject(){
        //隐藏价格
        $(".admin-panel:eq(1)").hide();
        //显示项目
        $(".admin-panel:eq(0)").show();
    }
    
    /**
     * 查看项目所有价格
     */
    function viewProjectPrices(projectId){
        initPricePage(projectId);
        //隐藏项目
        $(".admin-panel:eq(0)").hide();
        //显示价格
        $(".admin-panel:eq(1)").show();
    }
    
    /**
     * 加载页面
     */
    function initPricePage(projectId){
        //更新page信息
        var url = 'price/queryPriceList?projectId=' + projectId;
        CommonUtils.getAjaxData({url:url,type:'GET'},function(data){
            showPriceList(data);
            PageUtils.refreshPageInfo({element:'pricePage',url : url,callback : showPriceList},data['page']);
        });
    }
    
    /**
     * 显示价格列表
     */
    function showPriceList(data){
        //显示任务模板列表
        var html = template('priceTemp', data);
        $(".priceTbody").html("").html(html);
    }
    
    module.init = init;
    module.viewProject = viewProject;
    module.viewProjectPrices = viewProjectPrices;
    return module;
}($, window.ProjectList || {}));
$(function() {
    ProjectList.init();
});