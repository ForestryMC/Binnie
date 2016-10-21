package binnie.core.mod.config;

import net.minecraftforge.common.config.Property;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(propertyClass = PropBoolean.PropertyBoolean.class)
public @interface PropBoolean {
    class PropertyBoolean extends PropertyBase<Boolean, PropBoolean> {
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
