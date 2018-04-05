package binnie.core.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

abstract class PropertyBase<ValueType, AnnotationType extends Annotation> {
	private final Configuration file;
	private final Property property;
	private final ValueType defaultValue;
	private final AnnotationType annotatedProperty;
	private final ConfigProperty configProperty;
	private final List<String> comments;
	private final Field field;

	protected PropertyBase(final Field field, final BinnieConfiguration file, final ConfigProperty configProperty, final AnnotationType annotedProperty) throws IllegalArgumentException, IllegalAccessException {
		this.comments = new ArrayList<>();
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

	protected Property getProperty() {
		return property;
	}

	protected abstract ValueType getConfigValue();

	protected abstract void addComments();

	protected String getCategory() {
		return this.configProperty.category().isEmpty() ? this.annotatedProperty.annotationType().getAnnotation(ConfigProperty.Type.class).category() : this.configProperty.category();
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
		StringBuilder comment = new StringBuilder();
		for (final String com : this.comments) {
			comment.append(com).append(' ');
		}
		return comment.toString();
	}

	public Configuration getFile() {
		return file;
	}

	public ValueType getDefaultValue() {
		return defaultValue;
	}

	public AnnotationType getAnnotatedProperty() {
		return annotatedProperty;
	}
}
