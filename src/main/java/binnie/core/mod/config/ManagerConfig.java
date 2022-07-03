package binnie.core.mod.config;

import binnie.core.AbstractMod;
import binnie.core.ManagerBase;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraftforge.common.config.Configuration;

public class ManagerConfig extends ManagerBase {
    private Map<Class<?>, Configuration> configurations;
    private Map<AbstractMod, List<BinnieItemData>> itemIDs;

    public ManagerConfig() {
        configurations = new LinkedHashMap<>();
        itemIDs = new HashMap<>();
    }

    public void registerConfiguration(Class<?> cls, AbstractMod mod) {
        if (cls.isAnnotationPresent(ConfigFile.class)) {
            loadConfiguration(cls, mod);
        }
    }

    public void loadConfiguration(Class<?> cls, AbstractMod mod) {
        try {
            String filename = cls.getAnnotation(ConfigFile.class).filename();
            BinnieConfiguration config = new BinnieConfiguration(filename, mod);
            config.load();
            for (Field field : cls.getFields()) {
                if (field.isAnnotationPresent(ConfigProperty.class)) {
                    ConfigProperty propertyAnnot = field.getAnnotation(ConfigProperty.class);
                    for (Annotation annotation : field.getAnnotations()) {
                        if (annotation.annotationType().isAnnotationPresent(ConfigProperty.Type.class)) {
                            Class<?> propertyClass = annotation
                                    .annotationType()
                                    .getAnnotation(ConfigProperty.Type.class)
                                    .propertyClass();
                            // TODO what for?
                            PropertyBase property = (PropertyBase) propertyClass
                                    .getConstructor(
                                            Field.class,
                                            BinnieConfiguration.class,
                                            ConfigProperty.class,
                                            annotation.annotationType())
                                    .newInstance(
                                            field,
                                            config,
                                            propertyAnnot,
                                            annotation.annotationType().cast(annotation));
                        }
                    }
                }
            }
            config.save();
            configurations.put(cls, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItemID(Integer configValue, String configKey, BinnieConfiguration configFile) {
        if (!itemIDs.containsKey(configFile.mod)) {
            itemIDs.put(configFile.mod, new ArrayList<>());
        }
        itemIDs.get(configFile.mod).add(new BinnieItemData(configValue + 256, configFile, configKey));
    }
}
