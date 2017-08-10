package binnie.core.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

import net.minecraftforge.common.config.Property;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(propertyClass = PropInteger.PropertyInteger.class)
public @interface PropInteger {
	class PropertyInteger extends PropertyBase<Integer, PropInteger> {
		public PropertyInteger(final Field field, final BinnieConfiguration file, final ConfigProperty configProperty, final PropInteger annotedProperty) throws IllegalArgumentException, IllegalAccessException {
			super(field, file, configProperty, annotedProperty);
		}

		@Override
		protected Property getProperty() {
			return this.file.get(this.getCategory(), this.getKey(), this.defaultValue);
		}

		@Override
		protected Integer getConfigValue() {
			return this.property.getInt(this.defaultValue);
		}

		@Override
		protected void addComments() {
			this.addComment("Default value is " + this.defaultValue + ".");
		}
	}
}
