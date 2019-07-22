package org.pyx.pyx.javax.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author pyx
 * @date 2019/2/20
 */
public class CheckCaseValidator implements ConstraintValidator<CheckCase,String> {

    private CaseMode caseMode;
    @Override
    public void initialize(CheckCase constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
    }

    /**
     * 使用constraintValidatorContext来自定义错误信息
     * @param s
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        boolean isValid;
        if (caseMode == CaseMode.UPPER) {
            isValid = s.equals(s.toUpperCase());
        } else {
            isValid = s.equals(s.toLowerCase());
        }
        if(!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate( "{org.pyx.pyx.javax.validation.CheckCase.message}");
        }
        return isValid;
    }

    /*@Override
    public boolean isValid(Group group, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = false;


        if(!isValid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate( "{my.custom.template}" )
                .addNode( "myProperty" ).addConstraintViolation();
        }
        return isValid;
    }*/
}
