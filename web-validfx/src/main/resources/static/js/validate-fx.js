if(typeof Validfx === "undefined"){
    var Validfx = {};
    Validfx.debug = false;
    Validfx.bindVoAttr = "bindVo";
}
$.extend($.fn.validatebox.defaults.rules, {
    equals: {
        validator: function(value,param){
            // if(param.length > 1){
            //     this.message = param[1];
            //     $(this).attr("invalidMessage",param[1]);
            //     $.parser.parse($(this));
            // }
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
    $.callJsonp("/validators/rules",function (rules) {
        var regexRules = {};
        for(var i in rules){
            var rule = rules[i];
            regexRules[rule.ruleName] = {};
            (function () {
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
                }else if(rule.ruleName === "length"){
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
                }
            })();
        }
        if(Validfx.debug)
            console.log(regexRules,"extendValidateRules");

        $.extend($.fn.validatebox.defaults.rules,regexRules);
    });

    var bindInfo = "";
    var formIdx = 0;
    $(this).find("form").each(function(index,element){
        //$.parser.parse($(element));
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
        $.callJsonp("/validators/models/" + bindInfo,function (json) {
            bindVoModel(json); // $.bindForm("ff:user,ff2:user");
        });
    }
});
var getValidType = function (rules) {
    var retRules = [];
    for(var i in rules){
        var rule = rules[i];
        var ruleInfo = rule.name;
        if(rule.parameters){
            var ps = "";
            for(var p in rule.parameters){
                //if("message" == p)
                //    continue;
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
    //if(rules.length > 1)
    //    return "[" + retRules.substr(0,retRules.length-1) + "]";

    //return retRules.substr(0,retRules.length-1);
};
var bindVoModel = function (json) {
    if(Validfx.debug)
        console.log(json,"bindVoModel");

    for(var i in json){
        var f = json[i];
        var form = $("#"+f.form);
        if(form[0]){
            for(var j in f.items){
                var item = f.items[j];
                var input = form.find("input[name='" + item.propertyName + "']");
                if(input[0]){
                    if(item.rules && item.rules.length > 0) {
                        var validType = getValidType(item.rules);
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

$.fn.extend({
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
                //console.log(element,index);
            });
            return data;
        })($(this));
    },
    submitData : function (option) {
        var url = option.url;
        var form = null;
        var isValid = option.isValid;
        if(isValid != false)
            isValid = true;
        if(option.element)
            form = $.getForm(option.element);
        if(form === null){
            form = $.getForm($(this));
        }
        if(form[0] === null){
            return;
        }
        var api = new HttpClient();
        if(form.form('validate') || !isValid || Validfx.debug) {
            var data = $(form).jsonData();
            api.post({
                url: url, data: data, success: option.success
            });
        }
    }
});