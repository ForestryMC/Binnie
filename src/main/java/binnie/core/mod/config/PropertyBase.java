package binnie.core.mod.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

abstract class PropertyBase<ValueType, AnnotationType extends Annotation> {
    Configuration file;
    Property property;
    ValueType defaultValue;
    private ConfigProperty configProperty;
    AnnotationType annotatedProperty;
    private List<String> comments;
    private Field field;

    protected PropertyBase(final Field field, final BinnieConfiguration file, final ConfigProperty configProperty, final AnnotationType annotedProperty) throws IllegalArgumentException, IllegalAccessException {
        this.comments = new ArrayList<String>();
        this.field = field;
        this.file = file;
        this.configProperty = configProperty;
        this.annotatedProperty = annotedProperty;
        this.defaultValue = this.getDefaultValue(field);
        this.property = this.getProperty();
        for (final String comment : configProperty.comment()) {
            this.addComment(comment);
        }
        this.addComments();
        this.property.setComment(this.getComment());
        field.set(null, this.getConfigValue());
    }

    protected abstract Property getProperty();

    protected abstract ValueType getConfigValue();

    protected abstract void addComments();

    protected String getCategory() {
        return this.configProperty.category().equals("") ? this.annotatedProperty.annotationType().getAnnotation(ConfigProperty.Type.class).category() : this.configProperty.category();
    }

    protected String getKey() {
        return this.configProperty.key();
    }

    protected ValueType getDefaultValue(final Field field) throws IllegalArgumentException, IllegalAccessException {
        return (ValueType) field.get(null);
    }

    protected void addComment(final String comment) {
        this.comments.add(comment);
    }

    protected String getComment() {
        String comment = "";
        for (final String com : this.comments) {
            comment = comment + com + " ";
        }
        return comment;
    }
}
