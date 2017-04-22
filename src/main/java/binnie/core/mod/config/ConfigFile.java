package binnie.core.mod.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigFile {
	String filename();
}
