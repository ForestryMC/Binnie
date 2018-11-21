package binnie.extrabees.modules;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleSpeciesRegisterEvent;
import forestry.api.modules.ForestryModule;

import binnie.core.Constants;
import binnie.core.item.ItemMisc;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.ExtraBeesModuleUIDs;
import binnie.extrabees.genetics.ExtraBeeDefinition;
import binnie.extrabees.genetics.ExtraBeesFlowers;
import binnie.extrabees.genetics.effect.ExtraBeesEffect;
import binnie.extrabees.init.BlockRegister;
import binnie.extrabees.init.ItemRegister;
import binnie.extrabees.init.RecipeRegister;
import binnie.extrabees.items.ItemHoneyCrystal;
import binnie.extrabees.items.ItemPropolis;
import binnie.extrabees.items.types.EnumHoneyComb;
import binnie.extrabees.items.types.ExtraBeeItems;
import binnie.extrabees.utils.AlvearyMutationHandler;
import binnie.extrabees.utils.MaterialBeehive;
import binnie.extrabees.worldgen.ExtraBeesWorldGenerator;

@ForestryModule(moduleID = ExtraBeesModuleUIDs.CORE, containerID = Constants.EXTRA_BEES_MOD_ID, name = "Core", unlocalizedDescription = "extrabees.module.core", coreModule = true)
public class ModuleCore extends BinnieModule {

	@Nullable
	public static Material materialBeehive;
	@Nullable
	public static Block hive;
	@Nullable
	public static Block ectoplasm;
	@Nullable
	public static Item comb;
	@Nullable
	public static ItemPropolis propolis;
	@Nullable
	public static Item honeyDrop;
	@Nullable
	public static ItemHoneyCrystal honeyCrystal;
	@Nullable
	public static ItemMisc<ExtraBeeItems> itemMisc;
	@Nullable
	public static Item dictionaryBees;

	public ModuleCore() {
		super("forestry", "apiculture");
	}

	@Override
	public void setupAPI() {

	}

	@Override
	public void disabledSetupAPI() {

	}

	@Override
	public void registerItemsAndBlocks() {
		materialBeehive = new MaterialBeehive();
		BlockRegister.preInitBlocks();
		ItemRegister.preInitItems();
	}

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void doInit() {
		EnumHoneyComb.addSubtypes();
		ExtraBeesWorldGenerator extraBeesWorldGenerator = new ExtraBeesWorldGenerator();
		extraBeesWorldGenerator.doInit();
		GameRegistry.registerWorldGenerator(extraBeesWorldGenerator, 0);
		ExtraBeesEffect.doInit();
		ExtraBeesFlowers.doInit();
		ExtraBeeDefinition.doInit();
		BlockRegister.doInitBlocks();
	}

	@Override
	public void registerRecipes() {
		RecipeRegister.doInitRecipes();
	}

	@Override
	public void postInit() {
		AlvearyMutationHandler.registerMutationItems();
	}

	@SubscribeEvent
	public static void onRegisterSpecies(AlleleSpeciesRegisterEvent<IAlleleBeeSpecies> event) {
		if (event.getRoot() != BeeManager.beeRoot) {
			return;
		}
		ExtraBeeDefinition.doPreInit();
	}

	@SubscribeEvent
	public void onMissingItem(RegistryEvent.MissingMappings<Item> event) {
		for (RegistryEvent.MissingMappings.Mapping<Item> entry : event.getAllMappings()) {
			if (entry.key.toString().equals("genetics:dictionary")) {
				ResourceLocation newTotem = new ResourceLocation("extrabees:dictionary");
				Item value = ForgeRegistries.ITEMS.getValue(newTotem);
				if (value != null) {
					entry.remap(value);
				}
			}
		}
	}
}
