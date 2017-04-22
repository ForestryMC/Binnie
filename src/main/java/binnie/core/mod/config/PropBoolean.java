package binnie.core.mod.config;

import net.minecraftforge.common.config.Property;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(propertyClass = PropBoolean.PropertyBoolean.class)
public @interface PropBoolean {
	class PropertyBoolean extends PropertyBase<Boolean, PropBoolean> {
		public PropertyBoolean(Field field, BinnieConfiguration file, ConfigProperty configProperty, PropBoolean annotedProperty) throws IllegalArgumentException, IllegalAccessException {
			super(field, file, configProperty, annotedProperty);
		}

		@Override
		protected Property getProperty() {
			return file.get(getCategory(), getKey(), defaultValue);
		}

		@Override
		protected Boolean getConfigValue() {
			return property.getBoolean(defaultValue);
		}

		@Override
		protected void addComments() {
			addComment("Default value is " + defaultValue + ".");
		}
	}
}
