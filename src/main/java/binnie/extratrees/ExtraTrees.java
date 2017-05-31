package binnie.extratrees;

import binnie.Constants;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.models.DoublePassBakedModel;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extratrees.alcohol.ModuleAlcohol;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.ModuleBlocks;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.carpentry.ModuleCarpentry;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.core.ModuleCore;
import binnie.extratrees.genetics.ButterflySpecies;
import binnie.extratrees.genetics.ETTreeDefinition;
import binnie.extratrees.genetics.FruitSprite;
import binnie.extratrees.genetics.ModuleGenetics;
import binnie.extratrees.item.ModuleItems;
import binnie.extratrees.kitchen.ModuleKitchen;
import binnie.extratrees.machines.ModuleMachine;
import binnie.extratrees.proxy.Proxy;
import com.google.common.base.Preconditions;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.AlleleSpeciesRegisterEvent;
import forestry.api.lepidopterology.IButterflyRoot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mod(modid = Constants.EXTRA_TREES_MOD_ID, name = "Binnie's Extra Trees", useMetadata = true, dependencies = "required-after:" + Constants.CORE_MOD_ID)
public class ExtraTrees extends AbstractMod {

	@SuppressWarnings("NullableProblems")
	@Mod.Instance(Constants.EXTRA_TREES_MOD_ID)
	public static ExtraTrees instance;

	@SuppressWarnings("NullableProblems")
	@SidedProxy(clientSide = "binnie.extratrees.proxy.ProxyClient", serverSide = "binnie.extratrees.proxy.ProxyServer")
	public static Proxy proxy;

	@Nullable
	private static ModuleBlocks blocks;
	@Nullable
	private static ModuleItems items;
	@Nullable
	private static ModuleAlcohol alcohol;
	@Nullable
	private static ModuleCarpentry carpentry;
	@Nullable
	private static ModuleMachine machine;
	@Nullable
	private static ModuleKitchen kitchen;

	public ExtraTrees() {
		MinecraftForge.EVENT_BUS.register(ModuleGenetics.class);
	}

	public static ModuleItems items() {
		Preconditions.checkState(items != null);
		return items;
	}

	public static ModuleBlocks blocks() {
		Preconditions.checkState(blocks != null);
		return blocks;
	}

	public static ModuleAlcohol alcohol() {
		Preconditions.checkState(alcohol != null);
		return alcohol;
	}

	public static ModuleCarpentry carpentry() {
		Preconditions.checkState(carpentry != null);
		return carpentry;
	}

	public static ModuleMachine machine() {
		Preconditions.checkState(machine != null);
		return machine;
	}

	public static ModuleKitchen getKitchen() {
		Preconditions.checkState(kitchen != null);
		return kitchen;
	}

	@Mod.EventHandler
	public void onConstruction(FMLConstructionEvent e) {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent evt) {
		this.preInit();
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent evt) {
		this.init();
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent evt) {
		this.postInit();
	}

	@Override
	protected void registerModules() {
		this.addModule(blocks = new ModuleBlocks());
		this.addModule(items = new ModuleItems());
		this.addModule(alcohol = new ModuleAlcohol());
		this.addModule(new ModuleGenetics());
		this.addModule(carpentry = new ModuleCarpentry());
		this.addModule(machine = new ModuleMachine());
		this.addModule(new ModuleCore());
		this.addModule(kitchen = new ModuleKitchen());
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return ExtraTreesGUID.values();
	}

	@Override
	public Class<?>[] getConfigs() {
		return new Class[]{ConfigurationMain.class};
	}

	@Override
	public String getChannel() {
		return "ET";
	}

	@Override
	public IProxyCore getProxy() {
		return ExtraTrees.proxy;
	}

	@Override
	public String getModID() {
		return Constants.EXTRA_TREES_MOD_ID;
	}

	@SubscribeEvent
	public void speciesRegister(AlleleSpeciesRegisterEvent event) {
		if (event.getRoot() instanceof ITreeRoot) {
			ETTreeDefinition.preInitTrees();
			PlankType.ExtraTreePlanks.initWoodTypes();
		} else if (event.getRoot() instanceof IButterflyRoot && BinnieCore.isLepidopteryActive()) {
			ButterflySpecies.preInit();
		}
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	@Override
	public boolean isActive() {
		return BinnieCore.isExtraTreesActive();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onBakedEvent(ModelBakeEvent e) {
		//Find all ExtraTrees saplings
		List<ModelResourceLocation> models = e.getModelRegistry().getKeys().stream()
			.filter(mrl -> mrl.getResourceDomain().startsWith(Constants.EXTRA_TREES_MOD_ID))
			.filter(mrl -> mrl.getResourcePath().startsWith("germlings")).collect(Collectors.toList());
		//Replace model
		Map<String, ETTreeDefinition> map = Arrays.stream(ETTreeDefinition.values()).collect(Collectors.toMap(o -> o.name().toLowerCase(), o -> o));
		models.forEach(model -> {
			String species = model.getVariant().split("=")[1];
			ETTreeDefinition treeSpecies = map.get(species);
			int primaryColor = treeSpecies.getLeafColor().getRGB();
			int secondaryColor = treeSpecies.getWoodColor().getRGB();
			e.getModelRegistry().putObject(model, new DoublePassBakedModel(e.getModelRegistry().getObject(model), primaryColor, secondaryColor));
		});
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerSprites(TextureStitchEvent.Pre event) {
		for (FruitSprite sprite : FruitSprite.VALUES) {
			sprite.registerSprites();
		}
		TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
		for (IPlankType type : PlankType.ExtraTreePlanks.VALUES) {
			type.registerSprites(map);
		}
		for (IPlankType type : PlankType.ForestryPlanks.values()) {
			type.registerSprites(map);
		}
		for (IPlankType type : PlankType.VanillaPlanks.values()) {
			type.registerSprites(map);
		}
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(ExtraTrees.instance);
		}
	}
}
