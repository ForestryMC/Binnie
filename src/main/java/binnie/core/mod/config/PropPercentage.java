// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.mod.config;

import net.minecraftforge.common.config.Property;
import java.lang.reflect.Field;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(propertyClass = PropPercentage.PropertyPercentage.class)
public @interface PropPercentage {
	int upper() default 100;

	int lower() default 0;

	public static class PropertyPercentage extends PropertyBase<Integer, PropPercentage>
	{
		public PropertyPercentage(final Field field, final BinnieConfiguration file, final ConfigProperty configProperty, final PropPercentage annotedProperty) throws IllegalArgumentException, IllegalAccessException {
			super(field, file, configProperty, annotedProperty);
		}

		@Override
		protected Integer getConfigValue() {
			return this.property.getInt(this.defaultValue);
		}

		@Override
		protected void addComments() {
			this.addComment("Default value is " + this.defaultValue + "%.");
			this.addComment("Range is " + this.annotatedProperty.lower() + "-" + this.annotatedProperty.upper() + "%.");
		}

		@Override
		protected Property getProperty() {
			return this.file.get(this.getCategory(), this.getKey(), this.defaultValue);
		}
	}
}
