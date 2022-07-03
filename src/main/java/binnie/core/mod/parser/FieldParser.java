package binnie.core.mod.parser;

import binnie.core.AbstractMod;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public abstract class FieldParser {
    public static Collection<FieldParser> parsers = new ArrayList<>();

    public abstract boolean isHandled(Field field, AbstractMod mod);

    public void preInit(Field field, AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
        // ignored
    }

    public void init(Field field, AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
        // ignored
    }

    public void postInit(Field field, AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
        // ignored
    }

    public static void preInitParse(Field field, AbstractMod mod)
            throws IllegalArgumentException, IllegalAccessException {
        for (FieldParser parser : FieldParser.parsers) {
            if (parser.isHandled(field, mod)) {
                parser.preInit(field, mod);
            }
        }
    }

    public static void initParse(Field field, AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
        for (FieldParser parser : FieldParser.parsers) {
            if (parser.isHandled(field, mod)) {
                parser.init(field, mod);
            }
        }
    }

    public static void postInitParse(Field field, AbstractMod mod)
            throws IllegalArgumentException, IllegalAccessException {
        for (FieldParser parser : FieldParser.parsers) {
            if (parser.isHandled(field, mod)) {
                parser.postInit(field, mod);
            }
        }
    }
}
