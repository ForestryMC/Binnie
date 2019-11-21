package binnie.core.config;

import net.minecraftforge.common.config.Property;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(propertyClass = PropDouble.PropertyDouble.class)
public @interface PropDouble {
	class PropertyDouble extends PropertyBase<Double, PropDouble> {
		public PropertyDouble(final Field field, final BinnieConfiguration file, final ConfigProperty configProperty, final PropDouble annotedProperty) throws IllegalArgumentException, IllegalAccessException {
			super(field, file, configProperty, annotedProperty);
		}

		@Override
		protected Property getProperty() {
			return this.getFile().get(this.getCategory(), this.getKey(), this.getDefaultValue());
		}

		@Override
		protected Double getConfigValue() {
			return super.getProperty().getDouble(this.getDefaultValue());
		}

		@Override
		protected void addComments() {
			this.addComment("Default value is " + this.getDefaultValue() + '.');
		}
	}
}
