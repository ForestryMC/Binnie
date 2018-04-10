package binnie.core.modules;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Set;

public interface Module {
	default String getFailMessage(){
		return StringUtils.EMPTY;
	}

	default Set<String> getDependencyUids(){
		return Collections.emptySet();
	}

	default boolean canBeDisabled(){
		return true;
	}

	default boolean isAvailable(){
		return true;
	}

	default void setupAPI(){
	}

	default void disabledSetupAPI(){
	}

	default void registerItemsAndBlocks(){
	}


	default void preInit() {
	}

	default void init() {
	}

	default void postInit() {
	}
}
