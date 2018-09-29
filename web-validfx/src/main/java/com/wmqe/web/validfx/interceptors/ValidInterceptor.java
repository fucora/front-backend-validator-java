package com.wmqe.web.validfx.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.wmqe.web.validfx.annotations.ValidationModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;

/**
 * 表单校验拦截类
 * @author
 * @date
 */
@Component
@Aspect
public class ValidInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ValidInterceptor.class);
    
    @Autowired
    private Validator validator;

    @Pointcut("execution(* com.wmqe..*.controllers..*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String reqeustMethod = request.getMethod();
        
        if(reqeustMethod.equalsIgnoreCase("POST") || request.getMethod().equalsIgnoreCase("PUT")) {
            Signature signature = pjp.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            Method method = methodSignature.getMethod();
            Parameter[] parameters = method.getParameters();
            
            int validIndex = -1;
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if(parameter.getType().isAnnotationPresent(ValidationModel.class)) {
                    validIndex = i;
                }
            }
            
            if(validIndex >= 0) {
                Object[] args = pjp.getArgs();
                Object validArg = args[validIndex];
                Set<ConstraintViolation<Object>> cvSet = validator.validate(validArg, Default.class);
                if(cvSet != null && cvSet.size() > 0) {
                    JSONObject errorResult = new JSONObject();
                    for(ConstraintViolation<Object> cv : cvSet) {
                        errorResult.put(cv.getPropertyPath().toString(), cv.getMessage());
                    }
                    
                    return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
                }
            }
        }
        
        return pjp.proceed();
    }

}