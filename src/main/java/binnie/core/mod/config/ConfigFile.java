// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.mod.config;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigFile {
	String filename();
}
