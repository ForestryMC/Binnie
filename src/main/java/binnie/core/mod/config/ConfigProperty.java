package binnie.core.mod.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ConfigProperty {
    String key();

    String category() default "";

    String[] comment() default {};

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.ANNOTATION_TYPE})
    @interface Type {
        Class<? extends PropertyBase> propertyClass();

        String category() default "general";
    }
}
