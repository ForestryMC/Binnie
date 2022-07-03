package binnie.core.mod.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import net.minecraftforge.common.config.Property;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(propertyClass = PropPercentage.PropertyPercentage.class)
public @interface PropPercentage {
    int upper() default 100;

    int lower() default 0;

    class PropertyPercentage extends PropertyBase<Integer, PropPercentage> {
        public PropertyPercentage(
                Field field, BinnieConfiguration file, ConfigProperty configProperty, PropPercentage annotedProperty)
                throws IllegalArgumentException, IllegalAccessException {
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
