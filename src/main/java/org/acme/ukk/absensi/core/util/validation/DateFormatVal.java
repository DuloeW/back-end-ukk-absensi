package org.acme.ukk.absensi.core.util.validation;


import java.lang.annotation.*;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = DateFormatValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormatVal {
    String message() default "Wrong Date format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
