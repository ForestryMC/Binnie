package binnie.core.machines.errors;

import java.util.HashMap;
import java.util.Map;

public class ErrorStateRegistry {
	private static final Map<String, IErrorStateDefinition> states = new HashMap<>();

	public static void registerErrorState(IErrorStateDefinition state) {
		String uid = state.getUID();
		if (!uid.contains(":")) {
			throw new IllegalArgumentException("Binnie Error State name must be in the format <modid>:<name>.");
		}

		if (states.containsKey(uid)) {
			throw new IllegalArgumentException("Binnie Error State does not possess a unique name.");
		}

		states.put(uid, state);
	}

	public static IErrorStateDefinition getErrorState(String name) {
		if (name == null || name.isEmpty()) {
			return CoreErrorCode.UNKNOWN;
		}
		return states.get(name);
	}
}
