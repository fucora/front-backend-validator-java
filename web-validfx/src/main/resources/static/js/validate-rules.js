var extendValidateRules = function (rules) {
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
    console.log(regexRules,"extendValidateRules");
    $.extend($.fn.validatebox.defaults.rules,regexRules);
// //扩展easyui表单的验证
//     $.extend($.fn.validatebox.defaults.rules, {
//         //验证汉字
//         CHS: {
//             validator: function (value) {
//                 return /^[\u0391-\uFFE5]+$/.test(value);
//             },
//             message: 'The input Chinese characters only.'
//         },
//         //移动手机号码验证
//         Mobile: {//value值为文本框中的值
//             validator: function (value) {
//                 var reg = /^1[3|4|5|8|9]\d{9}$/;
//                 return reg.test(value);
//             },
//             message: 'Please enter your mobile phone number correct.'
//         },
//         //国内邮编验证
//         ZipCode: {
//             validator: function (value) {
//                 var reg = /^[0-9]\d{5}$/;
//                 return reg.test(value);
//             },
//             message: 'The zip code must be 6 digits and 0 began.'
//         },
//         //数字
//         Number: {
//             validator: function (value) {
//                 var reg =/^[0-9]*$/;
//                 return reg.test(value);
//             },
//             message: 'Please input number.'
//         },
//     });
};

var getValidType = function (rules) {
    var retRules = "";
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
                retRules += "'" + ruleInfo + "[" + ps.substr(0,ps.length-1) + "]',"
                continue;
            }
        }
        retRules += "'" + ruleInfo + "',";
    }
    if(rules.length > 1)
        return "[" + retRules.substr(0,retRules.length-1) + "]";

    return retRules.substr(0,retRules.length-1);
};
var bindVoModel = function (json) {
    console.log(json,"bindVoModel");

    for(var i in json){
        var f = json[i];
        var form = $("#"+f.form);
        if(form[0]){
            for(var j in f.items){
                var item = f.items[j];
                var input = form.find("input[name='" + item.propertyName + "']");
                if(input[0]){
                    var data_options = "";
                    if(item.required)
                        data_options = "required:" + item.required + ",";
                    //input.attr("required",item.required);
                    if(item.rules && item.rules.length > 0) {
                        var validType = getValidType(item.rules);
                        //input.attr("validType", validType);
                        data_options += "validType:" + validType;
                    }
                    input.attr("data-options",data_options);
                }
            }
        }
    }
};

$.fn.extend({
    jsonData:function() {
        return (function (element) {
            var form = $(element);
            if(!form.is("form")){
                form = form.parents("form");
            }
            if(form[0] === undefined){
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
    }
});