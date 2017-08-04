package binnie.core;

public interface IInitializable {
	void preInit();

	void init();

	void postInit();

	/**
	 * For compat modules if the mod is not loaded the module will not be registered.
	 */
	default boolean isAvailable(){
		return true;
	}

	default void setupAPI(){

	}

	default void disabledSetupAPI(){

	}
}
