package org.pyx.pyx.javax.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author pyx
 * @date 2019/2/20
 */
@Target({METHOD,FIELD,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckCaseValidator1.class)
@Documented
public @interface CheckCase {

    String message() default "{org.pyx.pyx.constraints.checkcase}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    CaseMode value();

}
