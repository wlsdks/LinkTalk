package com.tony.linktalk.util.custom;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)           // Class, Interface, Enum
@Retention(RetentionPolicy.RUNTIME) // Runtime 시에도 유지
@Component                          // Spring Bean으로 등록
public @interface PersistenceAdapter {
    String value() default "";
}
