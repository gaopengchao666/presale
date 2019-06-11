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


    module.fillForm = fillForm;
    module.serializeToObject = serializeToObject;
    module.getAjaxData = getAjaxData;
    return module;
}(jQuery, window.CommonUtils || {}));

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
        
        // 分页汇总记录数 页数 每页条数
        var summaryHtml = "<div class='page_sum'>共<span class='page_number'>" + end + "</span>页&emsp;每页 "
                + page.pageSize + " 条&emsp;共<span class='info_sum'> " + page.totalRecord + " </span>条记录</div>"

        // 分页跳转html
        var jumpPageHtml = "<div class='page_jump'><span>跳转到&emsp;第&nbsp;</span>"
                + "<input type='text' value='"+current+"' onkeypress='if(event.keyCode==13){PageUtils.jumpPage(this)}'/><span>&nbsp;页&emsp;</span>" +
                 "<button onclick='PageUtils.jumpPage(this)'>Go</button></div>";

        // 分页页码HTML外层
        var pageNumberHtml = "<div class='page-pull-right async-page'>";

        
        var pageDom = "";
        if (current != 1 && end != 0) {
            pageDom += "<button onclick='PageUtils.pageClick(1,this)'>首页</button>";
            pageDom += "<button onclick='PageUtils.pageClick(" + (current - 1) + ",this)'><<</button>";
        } else {
            pageDom += "<button>首页</button>";
            pageDom += "<button><<</button>";
        }

        jQuery.each(pageNoArr,function(i, index) {
            if (index == 0) {
                pageDom += "<label style='margin-right:3px;font-size: 10px; width: 20px; text-align: center;'>•••</label>";
            } else if (index != current) {
                pageDom += "<button onclick='PageUtils.pageClick(" + index + ",this)'>" + index
                        + "</button>";
            } else {
                pageDom += "<button class='select_page'>" + index + "</button>";
            }
        });

        if (current < end && end != 0) {
            pageDom += "<button onclick='PageUtils.pageClick(" + (current + 1) + ",this)'>>></button>";
            pageDom += "<button onclick='PageUtils.pageClick(" + end + ",this)'>尾页</button>";
        } else {
            pageDom += "<button>>></button>";
            pageDom += "<button>尾页</button>";
        }
        //分页HTML = 页数汇总 + 分页跳转 + 分页内容html
        var pageHtml = summaryHtml + jumpPageHtml + pageNumberHtml + pageDom + "</div>";
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
