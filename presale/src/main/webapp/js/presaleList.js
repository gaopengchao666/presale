window.PresaleList = (function($,module){
    
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
        var url = 'presale/queryPresaleList';
        CommonUtils.getAjaxData({url:url,type:'GET'},function(data){
            showPresaleList(data);
            PageUtils.refreshPageInfo({element:'presalePage',url : url,callback : showPresaleList},data['page']);
        });
    }
    
    /**
     * 显示预售证列表
     */
    function showPresaleList(data){
        //显示任务模板列表
        var html = template('presaleTemp', data);
        $(".presaleTbody").html("").html(html);
    }
    
    module.init = init;
    return module;
}($, window.PresaleList || {}));
$(function() {
    PresaleList.init();
});