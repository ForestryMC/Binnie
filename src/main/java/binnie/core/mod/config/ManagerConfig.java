package binnie.core.mod.config;

import binnie.core.AbstractMod;
import binnie.core.ManagerBase;
import net.minecraftforge.common.config.Configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class ManagerConfig extends ManagerBase {
    private Map<Class<?>, Configuration> configurations;
    private Map<AbstractMod, List<BinnieItemData>> itemIDs;

    public ManagerConfig() {
        this.configurations = new LinkedHashMap<Class<?>, Configuration>();
        this.itemIDs = new HashMap<AbstractMod, List<BinnieItemData>>();
    }

    public void registerConfiguration(final Class<?> cls, final AbstractMod mod) {
        if (cls.isAnnotationPresent(ConfigFile.class)) {
            this.loadConfiguration(cls, mod);
        }
    }

    public void loadConfiguration(final Class<?> cls, final AbstractMod mod) {
        try {
            final String filename = cls.getAnnotation(ConfigFile.class).filename();
            final BinnieConfiguration config = new BinnieConfiguration(filename, mod);
            config.load();
            for (final Field field : cls.getFields()) {
                if (field.isAnnotationPresent(ConfigProperty.class)) {
                    final ConfigProperty propertyAnnot = field.getAnnotation(ConfigProperty.class);
                    for (final Annotation annotation : field.getAnnotations()) {
                        if (annotation.annotationType().isAnnotationPresent(ConfigProperty.Type.class)) {
                            final Class<?> propertyClass = annotation.annotationType().getAnnotation(ConfigProperty.Type.class).propertyClass();
                            final PropertyBase property = (PropertyBase) propertyClass.getConstructor(Field.class, BinnieConfiguration.class, ConfigProperty.class, annotation.annotationType()).newInstance(field, config, propertyAnnot, annotation.annotationType().cast(annotation));
                        }
                    }
                }
            }
            config.save();
            this.configurations.put(cls, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItemID(final Integer configValue, final String configKey, final BinnieConfiguration configFile) {
        if (!this.itemIDs.containsKey(configFile.mod)) {
            this.itemIDs.put(configFile.mod, new ArrayList<BinnieItemData>());
        }
        this.itemIDs.get(configFile.mod).add(new BinnieItemData(configValue + 256, configFile, configKey));
    }
}
