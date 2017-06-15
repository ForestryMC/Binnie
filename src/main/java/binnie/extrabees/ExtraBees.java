package binnie.extrabees;

import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.AlleleSpeciesRegisterEvent;
import forestry.core.gui.GuiIdRegistry;
import forestry.core.gui.GuiType;
import forestry.core.proxy.Proxies;

import binnie.Constants;
import binnie.extrabees.alveary.TileEntityExtraBeesAlvearyPart;
import binnie.extrabees.genetics.ExtraBeeMutation;
import binnie.extrabees.genetics.ExtraBeesBranch;
import binnie.extrabees.genetics.ExtraBeesFlowers;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import binnie.extrabees.genetics.effect.ExtraBeesEffect;
import binnie.extrabees.init.BlockRegister;
import binnie.extrabees.init.ItemRegister;
import binnie.extrabees.init.RecipeRegister;
import binnie.extrabees.items.ItemHoneyComb;
import binnie.extrabees.items.ItemHoneyCrystal;
import binnie.extrabees.proxy.ExtraBeesCommonProxy;
import binnie.extrabees.utils.AlvearyMutationHandler;
import binnie.extrabees.utils.MaterialBeehive;
import binnie.extrabees.utils.Utils;
import binnie.extrabees.utils.config.ConfigHandler;
import binnie.extrabees.utils.config.ConfigurationMain;
import binnie.extrabees.worldgen.ExtraBeesWorldGenerator;

@Mod(modid = ExtraBees.MODID, name = "Binnie's Extra Bees", useMetadata = true, dependencies = "required-after:" + Constants.CORE_MOD_ID)
public class ExtraBees {

	public static final String MODID = "extrabees";

	@Mod.Instance(MODID)
	public static ExtraBees instance;

	@SidedProxy(clientSide = "binnie.extrabees.proxy.ExtraBeesClientProxy", serverSide = "binnie.extrabees.proxy.ExtraBeesCommonProxy")
	public static ExtraBeesCommonProxy proxy;
	
	public ExtraBees(){
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public static ConfigHandler configHandler;

	public static Material materialBeehive;
	public static Block hive;
	public static Block alveary;
	public static Block ectoplasm;
	public static Item comb;
	public static Item propolis;
	public static Item honeyDrop;
	public static ItemHoneyCrystal honeyCrystal;
	public static Item dictionary;
	public static Item itemMisc;

	@Mod.EventHandler
	@SuppressWarnings("all")
	public void preInit(final FMLPreInitializationEvent event) {
		materialBeehive = new MaterialBeehive();
		configHandler = new ConfigHandler(event.getSuggestedConfigurationFile());
		configHandler.addConfigurable(new ConfigurationMain());
		BlockRegister.preInitBlocks();
		ItemRegister.preInitItems();
		try {
			Method m = GuiIdRegistry.class.getDeclaredMethod("registerGuiHandlers", GuiType.class, List.class);
			m.setAccessible(true);
			m.invoke(null, new Object[]{GuiType.Tile, Lists.newArrayList(TileEntityExtraBeesAlvearyPart.class)});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Proxies.render.registerModels();
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent evt) {
		configHandler.reload(true);
		ItemHoneyComb.addSubtypes();
		GameRegistry.registerWorldGenerator(new ExtraBeesWorldGenerator(), 0);
		ExtraBeesEffect.doInit();
		ExtraBeesFlowers.doInit();
		ExtraBeesSpecies.doInit();
		ExtraBeeMutation.doInit();
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent evt) {
		BlockRegister.postInitBlocks();
		RecipeRegister.postInitRecipes();
		//Register mutations
		AlvearyMutationHandler.addMutationItem(new ItemStack(Blocks.SOUL_SAND), 1.5f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("UranFuel"), 4.0f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("MOXFuel"), 10.0f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("Plutonium"), 8.0f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("smallPlutonium"), 5.0f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("Uran235"), 4.0f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("smallUran235"), 2.5f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("Uran238"), 2.0f);
		AlvearyMutationHandler.addMutationItem(new ItemStack(Items.ENDER_PEARL), 2.0f);
		AlvearyMutationHandler.addMutationItem(new ItemStack(Items.ENDER_EYE), 4.0f);
	}
	
	@SubscribeEvent
	public void onRegisterSpecies(AlleleSpeciesRegisterEvent<IAlleleBeeSpecies> event){
		if(event.getRoot() != BeeManager.beeRoot){
			return;
		}
		ExtraBeesBranch.setSpeciesBranches();
		for (final ExtraBeesSpecies species : ExtraBeesSpecies.values()) {
			AlleleManager.alleleRegistry.registerAllele(species);
		}
	}
}
