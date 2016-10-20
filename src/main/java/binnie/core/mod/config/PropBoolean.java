// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.mod.config;

import net.minecraftforge.common.config.Property;
import java.lang.reflect.Field;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(propertyClass = PropBoolean.PropertyBoolean.class)
public @interface PropBoolean {
	public static class PropertyBoolean extends PropertyBase<Boolean, PropBoolean>
	{
		public PropertyBoolean(final Field field, final BinnieConfiguration file, final ConfigProperty configProperty, final PropBoolean annotedProperty) throws IllegalArgumentException, IllegalAccessException {
			super(field, file, configProperty, annotedProperty);
		}

		@Override
		protected Property getProperty() {
			return this.file.get(this.getCategory(), this.getKey(), this.defaultValue);
		}

		@Override
		protected Boolean getConfigValue() {
			return this.property.getBoolean(this.defaultValue);
		}

		@Override
		protected void addComments() {
			this.addComment("Default value is " + this.defaultValue + ".");
		}
	}
}
