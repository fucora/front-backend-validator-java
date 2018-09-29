package com.wmqe.web.validfx.utils;

import com.alibaba.fastjson.JSONObject;
import com.wmqe.web.validfx.annotations.NotEmpty;
import com.wmqe.web.validfx.models.ValidatorInfo;
import com.wmqe.web.validfx.models.ValidatorRule;
import com.wmqe.web.validfx.validators.BaseValidator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ValidatorUtils {
    public static Object getRules() {
        List<Class<BaseValidator>> validatorTypes = Scanner.getValidatorTypes();
        List<JSONObject> ruleList = new ArrayList<>();
        for (int i = 0; i < validatorTypes.size(); i++) {
            Class<BaseValidator> validatorType = validatorTypes.get(i);
            validatorType.getGenericSuperclass();
            boolean isAbs = Modifier.isAbstract(validatorType.getModifiers()) ;
            if(isAbs)continue;
            BaseValidator validator = null;
            try {
                validator = validatorType.newInstance();
            } catch (InstantiationException e) {
                continue;
            } catch (IllegalAccessException e) {
                continue;
            }

            JSONObject rule = new JSONObject();
            rule.put("ruleName",validator.getValidatorName());
            rule.put("message",validator.getMessage());
            validator.getParameters().forEach((k, v)->{
                rule.put(k.toString(), v);
            });

            ruleList.add(rule);
        }
        return ruleList;
    }


    private static Class<? extends ConstraintValidator<?,?>>[] getValidatedBy(Class<? extends Annotation> annotationType){
        Constraint constraint = annotationType.getAnnotation(Constraint.class);
        if(constraint == null)
            return null;

        Class<? extends ConstraintValidator<?,?>>[] validatedBy = constraint.validatedBy();
        if(validatedBy.length == 0)
            return null;

        return validatedBy;
    }

    private static ValidatorInfo getValidatorInfo(Field field){
        Annotation[] annotations = field.getAnnotations();

        if(annotations.length == 0)
            return null;

        boolean required = false;
        List<ValidatorRule> ruleList = new ArrayList<>();

        for (int i = 0; i < annotations.length; i++) {
            Annotation annotation = annotations[i];
            Class annotationType = annotation.annotationType();
            if(annotationType.isAssignableFrom(NotEmpty.class)){
                required = true;
                continue;
            }
            Class<? extends ConstraintValidator<?,?>>[] validatedBy = getValidatedBy(annotationType);
            if(validatedBy != null){
                String annotationName = annotationType.getSimpleName();

                ValidatorRule rule = new ValidatorRule();
                rule.setName(StringUtil.camelCase(annotationName));

                Method[] methods = annotationType.getDeclaredMethods();

                for (int j = 0; j < methods.length; j++) {
                    Method method = methods[j];
                    String methodName = method.getName();
                    if("groups".equals(methodName)){
                        continue;
                    }else if("payload".equals(methodName)){
                        continue;
                    }
                    try {
                        Object val = method.invoke(annotation,null);
                        if("message".equals(methodName)){
                            rule.setMessage(StringUtil.convString(val));
                        }else {
                            rule.setParameter(methodName, val);
                        }
                    } catch (IllegalAccessException e) {
                        continue;
                    } catch (InvocationTargetException e) {
                        continue;
                    }
                }
                rule.setParameter("message", rule.getMessage());
                //rule
                ruleList.add(rule);
            }
        }
        ValidatorInfo validatorInfo = new ValidatorInfo();
        validatorInfo.setPropertyName(field.getName());
        validatorInfo.setRequired(required);

        if(ruleList.size() == 0) {
            if(required) {
                return validatorInfo;
            }
            return null;
        }

        ValidatorRule[] rules = new ValidatorRule[ruleList.size()];
        ruleList.toArray(rules);
        validatorInfo.setRules(rules);

        return validatorInfo;
    }

    public static Object getValidatorInfos(String bindInfos){
        String[] groups = bindInfos.split(",");
        List<JSONObject> modelList = new ArrayList<>();
        for (int i = 0; i < groups.length; i++) {
            String group = groups[i];
            String[] infos = group.split(":");
            JSONObject info = new JSONObject();
            info.put("form",infos[0]);
            info.put("items", ValidatorUtils.getValidatorInfosBy(infos[1]));
            modelList.add(info);
        }
        return modelList;
    }

    private static List<ValidatorInfo> getValidatorInfosBy(String name){

        Class<?> cls = Scanner.getValidationModels(name);
        if(cls == null)
            return null;

        Field[] fields = cls.getDeclaredFields();
        List<ValidatorInfo> validatorInfoList = new ArrayList<>();
        for (int i = 0; i < fields.length ; i++) {
            Field field = fields[i];
            ValidatorInfo vi = getValidatorInfo(field);
            if(vi == null){
                continue;
            }
            validatorInfoList.add(vi);
        }

        return validatorInfoList;
    }

}
