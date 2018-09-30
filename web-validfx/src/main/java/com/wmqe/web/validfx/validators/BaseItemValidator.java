package com.wmqe.web.validfx.validators;

import com.wmqe.web.validfx.models.ParameterMap;
import com.wmqe.web.validfx.utils.StringUtil;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;

public abstract class BaseItemValidator<A extends Annotation> extends  BaseValidator<A, String> {

    public BaseItemValidator() {
//        try{
//            Class <A> constraintAnnotation = (Class <A>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//            validatorName = constraintAnnotation.getSimpleName();
//            validatorName = StringUtil.camelCase(validatorName);
////            Object coann =  constraintAnnotation.newInstance();
////            Method messageMethod = constraintAnnotation.getMethod("message");
////            Object message = messageMethod.invoke(coann,null);
////            if(message != null){
////                setMessage(message.toString());
////            }
//        }catch (Exception ex){
//            //System.out.println(ex);
//            validatorName = getClass().getSimpleName();
//            validatorName = StringUtil.camelCase(validatorName);
//        }
//
//        onLoad();
    }

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

//    private Map<String,Object> params = new HashMap<>();
//
//    /**
//     * 设置参数
//     * @param name
//     * @param value
//     */
//    public void setParameter(String name, Object value){
//        params.put(name,value);
//    }
//
//    public Map<String,Object> getParameters(){
//        return  params;
//    }
//
//    public Object getParameter(String name){
//        if(params.containsKey(name))
//            return params.get(name);
//
//        return null;
//    }
//    public int getParameterInt(String name, int defaultValue){
//        if(params.containsKey(name)) {
//            Object val = params.get(name);
//            if(val == null)
//                return defaultValue;
//            return Integer.parseInt(val.toString());
//        }
//
//        return defaultValue;
//    }
//    /**
//     *
//     * @param name
//     * @return
//     */
//    public String getParameterString(String name){
//        if(params.containsKey(name)) {
//            Object val = params.get(name);
//            if(val == null)
//                return null;
//            return val.toString();
//        }
//
//        return null;
//    }

//    @Override
//    public void initialize(A constraintAnnotation){
//        onInitialize(constraintAnnotation);
//    }
//
//    @Override
//    public abstract boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext);
//
//    public String getValidatorName(){
//        return validatorName;
//    }
}
