package binnie.core.mod.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import net.minecraftforge.common.config.Property;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(propertyClass = PropInteger.PropertyInteger.class)
public @interface PropInteger {
    class PropertyInteger extends PropertyBase<Integer, PropInteger> {
        public PropertyInteger(
                Field field, BinnieConfiguration file, ConfigProperty configProperty, PropInteger annotedProperty)
                throws IllegalArgumentException, IllegalAccessException {
            super(field, file, configProperty, annotedProperty);
        }

        @Override
        protected Property getProperty() {
            return file.get(getCategory(), getKey(), defaultValue);
        }

        @Override
        protected Integer getConfigValue() {
            return property.getInt(defaultValue);
        }

        @Override
        protected void addComments() {
            addComment("Default value is " + defaultValue + ".");
        }
    }
}
