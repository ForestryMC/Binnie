package binnie.design;

import javax.annotation.Nullable;
import java.util.Objects;

import binnie.core.Constants;
import binnie.core.machines.errors.ErrorStateRegistry;
import binnie.core.machines.inventory.ValidatorSprite;
import binnie.design.api.DesignAPI;
import binnie.design.api.IDesignManager;
import binnie.design.gui.DesignErrorCode;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = Constants.DESIGN_MOD_ID,
		name = "Binnie's Design",
		version = "@VERSION@",
		acceptedMinecraftVersions = Constants.ACCEPTED_MINECRAFT_VERSIONS,
		dependencies = "required-after:" + Constants.CORE_MOD_ID + ";"
)
public class Design {
	@SuppressWarnings("NullableProblems")
	@Mod.Instance(Constants.DESIGN_MOD_ID)
	public static Design instance;
	@Nullable
	private static ValidatorSprite spritePolish;

	@Nullable
	private static IDesignManager designManager;

	public static IDesignManager getDesignManager() {
		Objects.requireNonNull(designManager);
		return designManager;
	}

	public static ValidatorSprite getSpritePolish() {
		Objects.requireNonNull(spritePolish);
		return spritePolish;
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		DesignAPI.manager = designManager = new DesignerManager();
		spritePolish = new ValidatorSprite(Constants.DESIGN_MOD_ID, "validator/polish.0", "validator/polish.1");
		for (DesignErrorCode errorCode : DesignErrorCode.values()) {
			ErrorStateRegistry.registerErrorState(errorCode);
		}
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		IDesignManager designManager = getDesignManager();
		for (EnumDesign design : EnumDesign.values()) {
			designManager.registerDesign(design.ordinal(), design);
		}
	}
}
