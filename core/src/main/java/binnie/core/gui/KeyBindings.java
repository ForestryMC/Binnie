package binnie.core.gui;

import java.util.List;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public final class KeyBindings {
	private static final String categoryName = "Binnie's Mods";

	public static final KeyBinding holdForHelpTooltips;
	private static final List<KeyBinding> allBindings;

	static {
		allBindings = ImmutableList.of(
			holdForHelpTooltips = new KeyBinding("binniecore.key.hold.for.help.tooltips", KeyConflictContext.GUI, KeyModifier.NONE, Keyboard.KEY_TAB, categoryName)
		);
	}

	private KeyBindings() {
	}

	public static void init() {
		for (KeyBinding binding : allBindings) {
			ClientRegistry.registerKeyBinding(binding);
		}
	}
}
