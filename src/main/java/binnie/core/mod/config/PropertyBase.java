package binnie.core.mod.config;

import net.minecraftforge.common.config.*;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

abstract class PropertyBase<ValueType, AnnotationType extends Annotation> {
	protected Configuration file;
	protected Property property;
	protected ValueType defaultValue;
	protected AnnotationType annotatedProperty;

	private ConfigProperty configProperty;
	private List<String> comments;
	private Field field;

	protected PropertyBase(Field field, BinnieConfiguration file, ConfigProperty configProperty, AnnotationType annotedProperty) throws IllegalArgumentException, IllegalAccessException {
		comments = new ArrayList<>();
		this.field = field;
		this.file = file;
		this.configProperty = configProperty;
		this.annotatedProperty = annotedProperty;
		defaultValue = getDefaultValue(field);
		property = getProperty();

		for (String comment : configProperty.comment()) {
			addComment(comment);
		}
		addComments();
		property.comment = getComment();
		field.set(null, getConfigValue());
	}

	protected abstract Property getProperty();

	protected abstract ValueType getConfigValue();

	protected abstract void addComments();

	protected String getCategory() {
		return configProperty.category().equals("") ?
			annotatedProperty.annotationType().getAnnotation(ConfigProperty.Type.class).category() :
			configProperty.category();
	}

	protected String getKey() {
		return configProperty.key();
	}

	protected ValueType getDefaultValue(Field field) throws IllegalArgumentException, IllegalAccessException {
		return (ValueType) field.get(null);
	}

	protected void addComment(String comment) {
		comments.add(comment);
	}

	protected String getComment() {
		String comment = "";
		for (String com : comments) {
			comment = comment + com + " ";
		}
		return comment;
	}
}
