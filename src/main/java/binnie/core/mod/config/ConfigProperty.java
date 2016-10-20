// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.mod.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ConfigProperty {
	String key();

	String category() default "";

	String[] comment() default {};

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.ANNOTATION_TYPE })
	public @interface Type {
		Class<? extends PropertyBase> propertyClass();

		String category() default "general";
	}
}
