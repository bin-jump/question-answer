package com.zhang.ddd.infrastructure.common.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValidator.class})
public @interface EnumValid {

    String message() default "Enum Validation failed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> target() default Class.class;

}