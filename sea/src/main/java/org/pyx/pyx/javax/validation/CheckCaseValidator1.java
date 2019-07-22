package org.pyx.pyx.javax.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author pyx
 * @date 2019/2/20
 */
public class CheckCaseValidator1 implements ConstraintValidator<CheckCase,String> {
    private CaseMode caseMode;
    @Override
    public void initialize(CheckCase constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
    }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if (s == null) {
            return true;
        }
        if (caseMode == CaseMode.UPPER){
            return s.equals(s.toUpperCase());
        } else{
            return s.equals(s.toLowerCase());
        }
    }
}
