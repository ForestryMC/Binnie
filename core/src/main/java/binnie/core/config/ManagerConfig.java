package binnie.core.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;

import binnie.core.AbstractMod;
import binnie.core.ManagerBase;

public class ManagerConfig extends ManagerBase {
	private Map<Class<?>, Configuration> configurations;

	public ManagerConfig() {
		this.configurations = new LinkedHashMap<>();
	}

	public void registerConfiguration(final Class<?> cls, final AbstractMod mod) {
		if (cls.isAnnotationPresent(ConfigFile.class)) {
			this.loadConfiguration(cls, mod);
		}
	}

	public void loadConfiguration(final Class<?> cls, final AbstractMod mod) {
		final String filename = cls.getAnnotation(ConfigFile.class).filename();
		final BinnieConfiguration config = new BinnieConfiguration(filename, mod);
		config.load();
		for (final Field field : cls.getFields()) {
			if (field.isAnnotationPresent(ConfigProperty.class)) {
				final ConfigProperty propertyAnnot = field.getAnnotation(ConfigProperty.class);
				for (final Annotation annotation : field.getAnnotations()) {
					if (annotation.annotationType().isAnnotationPresent(ConfigProperty.Type.class)) {
						final Class<?> propertyClass = annotation.annotationType().getAnnotation(ConfigProperty.Type.class).propertyClass();
						try {
							final PropertyBase<?, ?> property = (PropertyBase<?, ?>) propertyClass.getConstructor(Field.class, BinnieConfiguration.class, ConfigProperty.class, annotation.annotationType()).newInstance(field, config, propertyAnnot, annotation.annotationType().cast(annotation));
						} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		config.save();
		this.configurations.put(cls, config);
	}
}
