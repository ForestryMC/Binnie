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
			return this.getFile().get(this.getCategory(), this.getKey(), this.getDefaultValue());
		}

		@Override
		protected Integer getConfigValue() {
			return super.getProperty().getInt(this.getDefaultValue());
		}

		@Override
		protected void addComments() {
			this.addComment("Default value is " + this.getDefaultValue() + ".");
		}
	}
}
