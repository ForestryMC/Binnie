package binnie.core;

public interface IInitializable {
	void preInit();

	void init();

	void postInit();

	default void setupAPI(){

	}

	default void disabledSetupAPI(){

	}
}
