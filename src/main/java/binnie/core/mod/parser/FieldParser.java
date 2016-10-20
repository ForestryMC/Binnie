package binnie.core.mod.parser;

import binnie.core.AbstractMod;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public abstract class FieldParser {
    public static Collection<FieldParser> parsers;

    public abstract boolean isHandled(final Field p0, final AbstractMod p1);

    public void preInit(final Field field, final AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
    }

    public void init(final Field field, final AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
    }

    public void postInit(final Field field, final AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
    }

    public static void preInitParse(final Field field, final AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
        for (final FieldParser parser : FieldParser.parsers) {
            if (parser.isHandled(field, mod)) {
                parser.preInit(field, mod);
            }
        }
    }

    public static void initParse(final Field field, final AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
        for (final FieldParser parser : FieldParser.parsers) {
            if (parser.isHandled(field, mod)) {
                parser.init(field, mod);
            }
        }
    }

    public static void postInitParse(final Field field, final AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
        for (final FieldParser parser : FieldParser.parsers) {
            if (parser.isHandled(field, mod)) {
                parser.postInit(field, mod);
            }
        }
    }

    static {
        FieldParser.parsers = new ArrayList<FieldParser>();
    }
}
