var extendValidateRules = function (rules) {
    //console.log(rules,"extendValidateRules");

    var regexRules = {};
    for(var i in rules){
        var rule = rules[i];
        regexRules[rule.ruleName] = {};

        (function () {
            if(rule.regex){
                var thisRule = regexRules[rule.ruleName];
                thisRule.regex = new RegExp(rule.regex);
                thisRule.message = rule.message;
                thisRule.validator = function (value) {
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

