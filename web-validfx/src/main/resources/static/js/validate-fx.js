// 配置、事件信息
if(typeof Validfx === "undefined"){
    var Validfx = {};
};
Validfx.debug = false;
Validfx.bindVoAttr = "bindVo";
Validfx.url = {};
Validfx.url.validRules = "/validators/rules"; // 获取校验规则的URL路径
Validfx.url.validModels = "/validators/models/"; // 获取校验信息的URL路径
Validfx.onFormValidFail = function (context) {};

// jquery 扩展
$.fn.extend({
    /**
     * 表单对象扩展方法 - 表单校验检查（触发 Validfx.onFormValidFail 事件）
     * @returns {boolean} 是否检查成功
     */
    checkForm : function () {
        var form = $(this);
        var validate = form.form('validate')
        if(validate == true)
            return true;
        if(Validfx.onFormValidFail){
            var invalidItems = form.find(".validatebox-invalid");
            Validfx.onFormValidFail({form : form, items : invalidItems});
        }

        return false;
    },
    /**
     * 表单对象扩展方法 - 构建指定表单的Json格式数据
     * @returns {json} 表单数据
     */
    jsonData:function() {
        return (function (element) {
            var form = $.getForm(element);
            if(form[0] == null){
                return null;
            }
            var data = {};
            var inputs = form.find("[name]");
            inputs.each(function(index,element){
                var name = $(element).attr("name");
                var val = $(element).val();
                var inputType = $(element).attr("type");
                if(inputType === "checkbox"){
                    if($(element).is(':checked')){
                        if(data[name] == null)
                            data[name] = [];
                        data[name].push(val);
                    }
                }else if(inputType === "radio"){
                    if($(element).is(':checked')) {
                        data[name] = val;
                    }
                }else{
                    data[name] = val;
                }
            });
            return data;
        })($(this));
    },
    /**
     * 表单对象扩展方法 - 提交表单
     * @param args
     */
    submitData : function (args) {
        var url = args.url;
        var form = null;
        var isValid = args.isValid;
        if(isValid != false)
            isValid = true;
        if(args.element)
            form = $.getForm(args.element);
        if(form === null){
            form = $.getForm($(this));
        }
        if(form[0] === null){
            return;
        }
        var api = new HttpClient();
        if(form.checkForm() || !isValid || Validfx.debug) {
            var data = $(form).jsonData();
            api.post({
                url: url, data: data, success: args.success
            });
        }
    }
});

$.extend($.fn.validatebox.defaults.rules, {
    equals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: '{1}'
    }});
$.extend({
    callJsonp : function (url,success) {
        $.ajax({
            url: url,
            type: "GET",
            // jsonpCallback: "callback",  //指定回调函数名称
            dataType: "jsonp", //指定服务器返回的数据类型
            success: success
        });
    },
    /**
     * 获取表单对象
     * @param element
     * @returns {*}
     */
    getForm : function (element) {
        var form = null;
        if(element != null){
            form = $(element);
        }
        if(form != null) {
            if (form.is("form")) {
                return form;
            }
            form = form.parents("form");
            return form;
        }
        form = $(event.target);
        if (form.is("form")) {
            return form;
        }
        form = form.parents("form");
        return form;
    }
});

$(function () {
    // 1. 加载校验规则
    $.callJsonp(Validfx.url.validRules,function (rules) {
        var regexRules = {};
        for(var i in rules){
            var rule = rules[i];
            regexRules[rule.ruleName] = {};
            (function () {
                // 如果是正则的校验，构建正则校验器
                if(rule.regex){
                    var thisRule = regexRules[rule.ruleName];
                    thisRule.regex = new RegExp(rule.regex);
                    thisRule.message = rule.message;
                    thisRule.validator = function (value, params) {
                        if(params && params.length > 0){
                            thisRule.message = params[params.length-1];
                        }
                        return thisRule.regex.test(value);
                    };
                }else if(rule.ruleName === "length"){ // 如果是字符串长度校验
                    var thisRule = regexRules[rule.ruleName];
                    thisRule.message = "输入的字符长度不能超过{1}";
                    thisRule.validator = function(val, params){
                        var len = $.trim(val).length;
                        if(params[0] > 0 && len < params[0]){
                            thisRule.message = "输入的字符长度不能少于{0}";
                        }else{
                            thisRule.message = "输入的字符长度不能超过{1}";
                        }
                        return len >= params[0] && len<= params[1];
                    };
                }else if(rule.ruleName === "size"){ // 如果是数组长度校验
                    var thisRule = regexRules[rule.ruleName];
                    thisRule.message = rule.message;
                    thisRule.validator = function(val, params){
                        var len = $("[name=" + val + "]:checked").length

                        if(params[0] > 0 && len < params[0]){
                            if(thisRule.message === null)
                                thisRule.message = "输入的字符长度不能少于{0}";
                            else
                                thisRule.message = thisRule.message.replace("{min}",params[0]);
                        }else{
                            if(thisRule.message === null)
                                thisRule.message = "输入的字符长度不能超过{1}";
                            else
                                thisRule.message = thisRule.message.replace("{max}",params[1]);
                        }
                        return len >= params[0] && len<= params[1];
                    }
                }
            })();
        }
        if(Validfx.debug)
            console.log(regexRules,"extendValidateRules");
        // 扩展校验
        $.extend($.fn.validatebox.defaults.rules,regexRules);
    });

    // 2.加载当前表单对象的校验信息
    var bindInfo = "";
    var formIdx = 0;
    $(this).find("form").each(function(index,element){
        var bindVo = $(element).attr(Validfx.bindVoAttr);
        if(bindVo){
            var formId = $(element).attr("id");
            if(formId == null){
                formId = "_form_" + formIdx;
                formIdx++;
                $(element).attr("id",formId);
            }
            bindInfo += formId +":"+bindVo+","
        }
    });
    if(bindInfo.length > 0){
        $.callJsonp(Validfx.url.validModels + bindInfo,function (json) {
            __bindVoModel(json);
        });
    }
});

/**
 * 构建EasyUI 的 ValidType
 * @param rules
 * @returns {*}
 * @private
 */
var __buildValidType = function (rules) {
    var retRules = [];
    for(var i in rules){
        var rule = rules[i];
        var ruleInfo = rule.name;
        if(rule.parameters){
            var ps = "";
            for(var p in rule.parameters){
                var val = rule.parameters[p];
                if(typeof val === "number"){
                    ps += val+",";
                }else if(typeof val === "string"){
                    ps += "\"" + val +"\",";
                }else{
                    ps += "\"" + val +"\",";
                }
            }
            if(ps.length > 1){
                retRules.push(ruleInfo + "[" + ps.substr(0,ps.length-1) + "]");
                continue;
            }
        }
        retRules.push(ruleInfo);
    }
    if(retRules.length == 1)
        return retRules[0];

    return retRules;
};
var __bindVoModel = function (json) {
    if(Validfx.debug)
        console.log(json,"bindVoModel");

    for(var i in json){
        var f = json[i];
        var form = $("#"+f.form);
        if(form.length > 0){
            for(var j in f.items){
                var item = f.items[j];
                var inputs = form.find("input[name='" + item.propertyName + "']");
                if(inputs.length == 0)
                    break;
                var input = inputs.length == 1 ? $(inputs[0]) : null;
                if(input == null){
                    // 说明有多个
                    input = $("<input type='hidden' name='group_" + item.propertyName + "' value='" + item.propertyName + "' />");
                    $(inputs[0]).before(input);
                }
                if(input){
                    if(item.rules && item.rules.length > 0) {
                        var validType = __buildValidType(item.rules);
                        input.validatebox({
                            required: item.required,
                            validType: validType
                        });
                    }else if(item.required == true){
                        input.validatebox({
                            required: true
                        });
                    }
                    $.parser.parse(input);
                }
            }
        }
    }
};

