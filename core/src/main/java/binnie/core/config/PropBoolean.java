package binnie.core.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

import net.minecraftforge.common.config.Property;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(propertyClass = PropBoolean.PropertyBoolean.class)
public @interface PropBoolean {
	class PropertyBoolean extends PropertyBase<Boolean, PropBoolean> {
		public PropertyBoolean(final Field field, final BinnieConfiguration file, final ConfigProperty configProperty, final PropBoolean annotedProperty) throws IllegalArgumentException, IllegalAccessException {
			super(field, file, configProperty, annotedProperty);
		}

		@Override
		protected Property getProperty() {
			return this.getFile().get(this.getCategory(), this.getKey(), this.getDefaultValue());
		}

		@Override
		protected Boolean getConfigValue() {
			return super.getProperty().getBoolean(this.getDefaultValue());
		}

		@Override
		protected void addComments() {
			this.addComment("Default value is " + this.getDefaultValue() + ".");
		}
	}
}
