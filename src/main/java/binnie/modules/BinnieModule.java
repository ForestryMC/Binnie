package binnie.modules;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BinnieModule {

	/**
	 * @return Unique identifier for the module, no spaces!
	 */
	String moduleID();

	/**
	 * @return True if this is a core module.
	 */
	boolean coreModule() default false;

	/**
	 * @return The unique identifier of the module container.
	 */
	String moduleContainerID();

	/**
	 * @return Nice and readable module name.
	 */
	String name();

	/**
	 * @return Version of the module, if any.
	 */
	String version() default "";

	/**
	 * @return Localization key for a short description what the module does.
	 */
	String unlocalizedDescription() default "";

}
