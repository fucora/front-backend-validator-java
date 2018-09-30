package com.wmqe.web.validfx.validators;

import com.wmqe.web.validfx.models.ParameterMap;
import com.wmqe.web.validfx.utils.StringUtil;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public abstract class BaseListValidator<A extends Annotation> extends BaseValidator<A, List> {

    public BaseListValidator() {
//        try{
//            Class <A> constraintAnnotation = (Class <A>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//            validatorName = constraintAnnotation.getSimpleName();
//            validatorName = StringUtil.camelCase(validatorName);
//
//        }catch (Exception ex){
//            validatorName = getClass().getSimpleName();
//            validatorName = StringUtil.camelCase(validatorName);
//        }
//
//        onLoad();
    }

//    /**
//     * s
//     * @param messageTemplate
//     * @return
//     */
//    public String buildMessage(String messageTemplate){
//
//        if(StringUtil.isEmpty(messageTemplate))
//            return messageTemplate;
//
//        Map<String,Object> parameters = getParameters();
////        parameters.forEach((k, v)->{
////
////        });
//        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
//            Object val = entry.getValue();
//            if(val == null)
//                val = "";
//            messageTemplate = messageTemplate.replace("${"+entry.getKey()+"}",val.toString());
//        }
//        return messageTemplate;
//    }
//
//    public String getMessageTemplate(ConstraintValidatorContext context)
//    {
//        if(context == null)
//            return null;
//
//        if(context.getClass().equals(ConstraintValidatorContextImpl.class)){
//            String message = ((ConstraintValidatorContextImpl) context).getConstraintDescriptor().getMessageTemplate();
//
//            return  message;
//        }
//
//        return  null;
//    }
//
//    public void buildConstraintValidatorContext(ConstraintValidatorContext context){
//        String message = getMessageTemplate(context);
//        context.disableDefaultConstraintViolation();
//        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
//    }
//
//    public abstract void onLoad();
//
//    private String validatorName = null;
//    private String message = null;
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public abstract void onInitialize(A constraintAnnotation);
//
//
//    @Override
//    public void initialize(A constraintAnnotation){
//        onInitialize(constraintAnnotation);
//    }
//
//    @Override
//    public abstract boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext);
//
//    public String getValidatorName(){
//        return validatorName;
//    }
}
