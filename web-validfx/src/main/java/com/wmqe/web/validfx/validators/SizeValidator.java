package com.wmqe.web.validfx.validators;
import com.wmqe.web.validfx.annotations.Size;
import com.wmqe.web.validfx.utils.StringUtil;

import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * 列表长度校验类
 */
public class SizeValidator extends BaseListValidator<Size> {
    private final String PARAM_NAME_MIN = "min";
    private final String PARAM_NAME_MAX = "max";
    @Override
    public void onLoad() {

    }

    @Override
    public void onInitialize(Size parameters) {
        int min = parameters.min();
        int max = parameters.max();
        setParameter(PARAM_NAME_MIN, min);
        setParameter(PARAM_NAME_MAX, max);
    }
    
    @Override
    public boolean isValid(List list, ConstraintValidatorContext context) {
        int min = getParameterInt(PARAM_NAME_MIN, 0);
        int max = getParameterInt(PARAM_NAME_MAX, Integer.MAX_VALUE);
        context.disableDefaultConstraintViolation();

        int size = 0;
        if(list != null)
            size = list.size();

        String message = getMessageTemplate(context);

        if(max == Integer.MAX_VALUE && min > 0 && size < min) {
            if(StringUtil.isEmpty(message)) {
                message = "列表数量至少为{min}个";
            }
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        } else if(min == 0 && max < Integer.MAX_VALUE && size > min) {
            if(StringUtil.isEmpty(message)) {
                message = "列表数量不能超过{max}个";
            }
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        } else if(min > 0 && max < Integer.MAX_VALUE && (size > max || size < min)) {
            if(StringUtil.isEmpty(message)) {
                message ="列表必须在{min}至{max}的范围";
            }
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }

}
