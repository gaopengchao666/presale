window.CommonUtils = (function(jQuery, module) {
    /**
     * 根据数据按照name规则填充form中的标准HTML输入框(包括input checkbox textarea,select),
     * <p>
     * 注意form中的checkbox需要预先填写被勾选后的值 <code><input type="checkbox"
     * name="sopTeamInfo.isOwner" value="1"></code>
     * "1"
     * 就是预期需要与sopTeamInfo.isOwner比较的值,如果sopTeamInfo.isOwner的值是"1",则checkbox被勾选
     * 
     * @param form
     *            form的id或jquery选择器获取到的form元素
     * @param data
     *            需要填充到form中的数据 json对象
     * @param profix
     *            前缀,输入框的名称的"."之前的部分,如果没有则不填
     */
    function fillForm(form, data, profix) {
        var _jQueryform = typeof form == "string" ? jQuery("#" + form) : form;
        var _profix;
        // 如果用户填入前缀,则使用前缀构造名称,如果未填,名称前缀为""
        if (profix) {
            _profix = profix + ".";
        } else {
            _profix = "";
        }

        for ( var index in data) {
            var _jQueryinput = _jQueryform.find(":input[name='" + _profix + index + "']");
            if (_jQueryinput.attr("type") == "checkbox") {
                _jQueryinput.attr("checked", _jQueryinput.val() == data[index]);
            } else {
                _jQueryinput.val(data[index]);
            }
        }
    }
    // 判空处理，如果值为null返回""
    function isNull(data) {
        if (data == null) {
            return ""
        } else {
            return data
        }
    }

    /**
     * 序列化dom 中input/select 为对象 获取下拉框的 html
     */
    function serializeToObject(form, profix) {
        var _jQueryform = typeof form == "string" ? jQuery("#" + form) : form;
        // 如果用户填入前缀,则使用前缀构造名称,如果未填,名称前缀为""
        var _profix;
        if (profix) {
            _profix = profix + ".";
        } else {
            _profix = "";
        }
        var o = {};
        if (profix) {
            jQuery.each(_jQueryform.find(":input[name^='" + _profix + "']"), function() {
                if (this.name.indexOf(_profix) > -1) {
                    var name = this.name.split(_profix)[1];
                    o[name] = this.value || '';
                }
            });
        } else {
            jQuery.each(_jQueryform.find(":input"), function() {
                var name = this.name.indexOf(".") > -1 ? this.name.split(".")[1] : this.name;
                if (name.length > 0) {
                    o[name] = this.value || '';
                }
            });
        }

        return o;
    }

    /**
     * ajax请求后台数据
     */
    function getAjaxData(param, s_callback) {
        // 查询数据
        jQuery.ajax({
            type : param.type,
            url : param.url,
            data : param.data,
            contentType : 'application/json; charset=utf-8',
            async : param.async || false,
            cache : false,
            success : function(data) {
                if (s_callback) {
                    s_callback(data);
                }
            },
            error : function(data, data1, data2) {
                if (data.responseText.indexOf("该事项已被处理") > -1) {
                    alert(data.responseText);
                    // 更新待办事项状态
                    self.parent.menuFrame.updateWorkBeach();
                } else if (jQuery("#loader").length > 0) {
                    alert("后台报错");
                    jQuery("#loader").addClass("hidden");
                } else {
                    alert("后台报错");
                }
            }
        });
    }
    
    /**
     * 初始化函数
     */
    function init(){
        bindEvent();
    }
    
    /**
     * 绑定事件
     */
    function bindEvent(){
        $(".icon-search").click(function(){
            var pageId = $(this).parents(".admin-panel").find(".pagelist").attr("id");
            var element = $(this).parents(".admin-panel").find(".search");
            // 筛选数据获取
            var paramData = CommonUtils.serializeToObject(element);
            // 分页参数 增加筛选条件
            jQuery.each(PageUtils._pageParamArr, function() {
                if (this['element'] == pageId) {
                    this['data'] = paramData;
                }
            });
            // 分页跳转第一页
            PageUtils.pageClick(1, pageId);
        });
        $(".icon-clear").click(function(){
            var pageId = $(this).parents(".admin-panel").find(".pagelist").attr("id");
            var element = $(this).parents(".admin-panel").find(".search");
            //清除筛选项
            jQuery(element).find(":input").val("");
            jQuery(element).find("select").prop('selectedIndex', 0);
            // 分页参数 增加筛选条件
            jQuery.each(PageUtils._pageParamArr, function() {
                if (this['element'] == pageId) {
                    this['data'] = "";
                }
            });
            // 分页跳转第一页
            PageUtils.pageClick(1, pageId);
        });
    }

    module.fillForm = fillForm;
    module.serializeToObject = serializeToObject;
    module.getAjaxData = getAjaxData;
    module.init = init;
    return module;
}(jQuery, window.CommonUtils || {}));
$(function() {
    CommonUtils.init();
});

window.PageUtils = (function(jQuery, module) {
    var _pageParamArr = [], // 分页参数数组,可以有多个分页情况
    _currentPage = {}; // 当前页

    /**
     * 刷新分页页脚信息 用于异步
     * 
     * @param obj
     *            选择元素id 或者 分页信息参数对象
     * @param page
     *            分页对象
     */
    function refreshPageInfo(obj, page) {
        if (!obj || !page) {
            return;
        }

        var element = null;
        if (typeof obj == "object") {
            _pageParamArr.push(obj);
            element = obj['element'];
        } else {
            element = obj;
        }
        var _jQueryelement = typeof element == "string" ? jQuery("#" + element) : element;
        var begin = 1;
        var end = page['totalPage'];
        _jQueryelement.html("");
        if (end == 0) {
            return;
        }
        
        //当前页
        var current = page['pageNo'];
        _currentPage[element] = current;
        //分页列表
        var pageNoDisp = page['pageNoDisp'];
        var pageNoArr = pageNoDisp.split("|") || [];

        // 分页页码HTML外层
        var pageNumberHtml = "<div>";

        
        var pageDom = "";
        if (current != 1 && end != 0) {
            pageDom += "<a class='button' onclick='PageUtils.pageClick(1,this)'>首页</a>";
            pageDom += "<a class='button' onclick='PageUtils.pageClick(" + (current - 1) + ",this)'><<</a>";
        } else {
            pageDom += "<a class='button'>首页</a>";
            pageDom += "<a class='button'><<</a>";
        }

        jQuery.each(pageNoArr,function(i, index) {
            if (index == 0) {
                pageDom += "<label style='margin-right:3px;font-size: 10px; width: 20px; text-align: center;'>•••</label>";
            } else if (index != current) {
                pageDom += "<a class='button' onclick='PageUtils.pageClick(" + index + ",this)'>" + index
                        + "</a>";
            } else {
                pageDom += "<span class='current'>" + index + "</span>";
            }
        });

        if (current < end && end != 0) {
            pageDom += "<a class='button' onclick='PageUtils.pageClick(" + (current + 1) + ",this)'>>></a>";
            pageDom += "<a class='button' onclick='PageUtils.pageClick(" + end + ",this)'>尾页</a>";
        } else {
            pageDom += "<a class='button'>>></a>";
            pageDom += "<a class='button'>尾页</a>";
        }
        
        //分页HTML = 页数汇总 + 分页跳转 + 分页内容html
        var pageHtml = pageNumberHtml + pageDom + "</div>";
        _jQueryelement.append(pageHtml);
    }

    /**
     * 分页点击事件处理
     */
    function pageClick(pNo, object) {
        // 分页元素id
        var elementId = typeof object == 'string' ? object : jQuery(object).parent().parent().attr("id");
        var param = {};
        jQuery.each(_pageParamArr, function() {
            if (this['element'] == elementId) {
                param = this;
            }
        });

        var params = jQuery.extend(param.data || {}, {
            'pageNo' : pNo
        });
        // 获取数据和 分页对象
        jQuery("#loader").removeClass("hidden");
        CommonUtils.getAjaxData({
            url : param['url'],
            type : 'GET',
            data : params,
            async : 'true'
        }, function(data) {
            if (!data) {
                return;
            }
            // 刷新分页信息
            refreshPageInfo(param['element'], data['page']);

            // 刷新列表信息 回调函数
            jQuery.each(_pageParamArr, function() {
                var callback = param['callback'];
                if (callback) {
                    callback(data);
                }
                jQuery("#loader").addClass("hidden");
            });
        });
    }
    
    /**
     * 自定义页面跳转
     */
    function jumpPage(obj){
        //如果事件源是按钮 则 取input
        var jumpPageInput = obj.tagName == 'BUTTON' ? jQuery(obj).prevAll("input") : jQuery(obj);
        var pageNoStr = jumpPageInput.val();
        //如果文本框是数字则跳转 否则清空
        var pageNo = parseInt(pageNoStr);
        if (pageNo && pageNo > 0){
            pageClick(parseInt(pageNoStr),obj);
        }
        else{
            jumpPageInput.val("");
        }
    }
    
    module.refreshPageInfo = refreshPageInfo;
    module.pageClick = pageClick;
    module.jumpPage = jumpPage;
    module._currentPage = _currentPage;
    module._pageParamArr = _pageParamArr;
    return module;
}(jQuery, window.PageUtils || {}));
