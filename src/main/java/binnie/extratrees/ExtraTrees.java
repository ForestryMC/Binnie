package binnie.extratrees;

import binnie.Constants;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extrabees.ExtraBees;
import binnie.extratrees.alcohol.ModuleAlcohol;
import binnie.extratrees.alcohol.drink.ItemDrink;
import binnie.extratrees.block.ModuleBlocks;
import binnie.extratrees.block.decor.BlockHedge;
import binnie.extratrees.block.decor.BlockMultiFence;
import binnie.extratrees.carpentry.BlockCarpentry;
import binnie.extratrees.carpentry.BlockStainedDesign;
import binnie.extratrees.carpentry.ModuleCarpentry;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.core.ModuleCore;
import binnie.extratrees.genetics.ButterflySpecies;
import binnie.extratrees.genetics.ExtraTreeFruitGene;
import binnie.extratrees.genetics.ExtraTreeSpecies;
import binnie.extratrees.genetics.ModuleGenetics;
import binnie.extratrees.item.ModuleItems;
import binnie.extratrees.machines.ModuleMachine;
import binnie.extratrees.proxy.Proxy;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.AlleleSpeciesRegisterEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Constants.EXTRA_TREES_MOD_ID, name = "Binnie's Extra Trees", useMetadata = true, dependencies = "required-after:" + Constants.CORE_MOD_ID)
public class ExtraTrees extends AbstractMod {

    @Mod.Instance(Constants.EXTRA_TREES_MOD_ID)
    public static ExtraTrees instance;
    @SidedProxy(clientSide = "binnie.extratrees.proxy.ProxyClient", serverSide = "binnie.extratrees.proxy.ProxyServer")
    public static Proxy proxy;
    public static Item itemDictionary;
    public static Item itemDictionaryLepi;
    public static Item itemMisc;
    public static Item itemFood;
    public static Block blockStairs;
    //public static Block blockLog;
    public static BlockCarpentry blockCarpentry;
    public static Block blockPlanks;
    public static Block blockMachine;
    public static Block blockFence;
    public static BlockCarpentry blockPanel;
    public static Block blockKitchen;
    public static Block blockSlab;
    public static Block blockDoubleSlab;
    public static Item itemHammer;
    public static Item itemDurableHammer;
    public static Block blockGate;
    public static Block blockDoor;
    public static Block blockBranch;
    public static ItemDrink drink;
    public static BlockMultiFence blockMultiFence;
    public static Block blockDrink;
    public static BlockHedge blockHedge;
    public static BlockStainedDesign blockStained;
    public static int fruitPodRenderId;
    public static int doorRenderId;
    public static int branchRenderId;
    public static int fenceID;
    public static int stairsID;

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
        this.addModule(new ModuleBlocks());
        this.addModule(new ModuleItems());
        this.addModule(new ModuleAlcohol());
        this.addModule(new ModuleGenetics());
        this.addModule(new ModuleCarpentry());
        this.addModule(new ModuleMachine());
        this.addModule(new ModuleCore());
    }

    @Override
    public IBinnieGUID[] getGUIDs() {
        return ExtraTreesGUID.values();
    }

    @Override
    public Class[] getConfigs() {
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

    @Override
    protected Class<? extends BinniePacketHandler> getPacketHandler() {
        return PacketHandler.class;
    }

    @Override
    public boolean isActive() {
        return BinnieCore.isExtraTreesActive();
    }

    @SubscribeEvent
    public void speciesRegister(AlleleSpeciesRegisterEvent event) {
        if (event.getRoot() instanceof ITreeRoot) {
            for (final ExtraTreeFruitGene fruit : ExtraTreeFruitGene.values()) {
                AlleleManager.alleleRegistry.registerAllele(fruit);
            }
            for (final ExtraTreeSpecies species : ExtraTreeSpecies.values()) {
                AlleleManager.alleleRegistry.registerAllele(species, EnumTreeChromosome.SPECIES);
            }
        }

        if (BinnieCore.isLepidopteryActive()) {
            for (final ButterflySpecies species2 : ButterflySpecies.values()) {
                AlleleManager.alleleRegistry.registerAllele(species2);
            }
        }
    }

    public static class PacketHandler extends BinniePacketHandler {
        public PacketHandler() {
            super(ExtraBees.instance);
        }
    }

}
