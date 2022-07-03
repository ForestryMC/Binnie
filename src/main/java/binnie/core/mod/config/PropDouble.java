package binnie.core.mod.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import net.minecraftforge.common.config.Property;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(propertyClass = PropDouble.PropertyDouble.class)
public @interface PropDouble {
    class PropertyDouble extends PropertyBase<Double, PropDouble> {
        public PropertyDouble(
                Field field, BinnieConfiguration file, ConfigProperty configProperty, PropDouble annotedProperty)
                throws IllegalArgumentException, IllegalAccessException {
            super(field, file, configProperty, annotedProperty);
        }

        @Override
        protected Property getProperty() {
            return file.get(getCategory(), getKey(), defaultValue);
        }

        @Override
        protected Double getConfigValue() {
            return property.getDouble(defaultValue);
        }

        @Override
        protected void addComments() {
            addComment("Default value is " + defaultValue + ".");
        }
    }
}
