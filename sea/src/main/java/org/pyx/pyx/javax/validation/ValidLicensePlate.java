package org.pyx.pyx.javax.validation;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author pyx
 * @date 2019/2/20
 */
@NotNull
@Size(min =2,max=14)
@CheckCase(CaseMode.UPPER)
@Target({FIELD,METHOD,ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidLicensePlate {
    String message() default "{com.mycompany.constraints.validlicenseplate}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
