package binnie.core.mod.config;

import net.minecraftforge.common.config.*;

import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(propertyClass = PropPercentage.PropertyPercentage.class)
public @interface PropPercentage {
	int upper() default 100;

	int lower() default 0;

	class PropertyPercentage extends PropertyBase<Integer, PropPercentage> {
		public PropertyPercentage(Field field, BinnieConfiguration file, ConfigProperty configProperty, PropPercentage annotedProperty) throws IllegalArgumentException, IllegalAccessException {
			super(field, file, configProperty, annotedProperty);
		}

		@Override
		protected Integer getConfigValue() {
			return property.getInt(defaultValue);
		}

		@Override
		protected void addComments() {
			addComment("Default value is " + defaultValue + "%.");
			addComment("Range is " + annotatedProperty.lower() + "-" + annotatedProperty.upper() + "%.");
		}

		@Override
		protected Property getProperty() {
			return file.get(getCategory(), getKey(), defaultValue);
		}
	}
}
