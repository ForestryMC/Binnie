package binnie.extratrees.genetics;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.AlleleRegisterEvent;
import forestry.api.genetics.AlleleSpeciesRegisterEvent;
import forestry.api.lepidopterology.IButterflyRoot;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.extratrees.block.PlankType;

public class ModuleGenetics implements IInitializable {
	String[] branches;
	List<List<String>> classifications;

	public ModuleGenetics() {
		this.branches = new String[]{"Malus Maleae Amygdaloideae Rosaceae", "Musa   Musaceae Zingiberales Commelinids Angiosperms", "Sorbus Maleae", "Tsuga   Pinaceae", "Fraxinus Oleeae  Oleaceae Lamiales Asterids Angiospems"};
		this.classifications = new ArrayList<>();
	}

	@SubscribeEvent
	public static void onRegisterAllele(AlleleRegisterEvent<IAlleleFruit> event) {
		if (event.getAlleleClass() == IAlleleFruit.class) {
			AlleleETFruit.preInit();
		}
	}
	
	@SubscribeEvent
	public static void speciesRegister(AlleleSpeciesRegisterEvent event) {
		if (event.getRoot() instanceof ITreeRoot) {
			ETTreeDefinition.preInitTrees();
			PlankType.ExtraTreePlanks.initWoodTypes();
		} else if (event.getRoot() instanceof IButterflyRoot && BinnieCore.isLepidopteryActive()) {
			ButterflySpecies.preInit();
		}
	}

	@Override
	public void preInit() {
	}

	@Override
	public void init() {
		AlleleETFruit.init();
		ETTreeDefinition.initTrees();
		ExtraTreeMutation.init();
		if (BinnieCore.isLepidopteryActive()) {
			ButterflySpecies.initButterflies();
		}
	}

	@Override
	public void postInit() {
	}
}