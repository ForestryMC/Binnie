package binnie.genetics.modules.features;

import binnie.core.Constants;
import binnie.core.features.FeatureItem;
import binnie.core.features.FeatureProvider;
import binnie.core.features.IFeatureRegistry;
import binnie.core.item.ItemMisc;
import binnie.core.modules.GeneticsModuleUIDs;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.item.ItemAnalyst;
import binnie.genetics.item.ItemDatabase;
import binnie.genetics.item.ItemMasterRegistry;
import binnie.genetics.item.ItemRegistry;
import binnie.genetics.item.ItemSequence;
import binnie.genetics.item.ItemSerum;
import binnie.genetics.item.ItemSerumArray;

@FeatureProvider(containerId = Constants.GENETICS_MOD_ID, moduleID = GeneticsModuleUIDs.CORE)
public class GeneticItems {
	public static final FeatureItem<ItemSerum> SERUM = features().createItem("serum", ItemSerum::new);
	public static final FeatureItem<ItemSerumArray> SERUM_ARRAY = features().createItem("serum_array", ItemSerumArray::new);
	public static final FeatureItem<ItemSequence> SEQUENCE = features().createItem("sequence", ItemSequence::new);
	public static final FeatureItem<ItemDatabase> DATABASE = features().createItem("geneticdatabase", ItemDatabase::new);
	public static final FeatureItem<ItemAnalyst> ANALYST = features().createItem("analyst", ItemAnalyst::new);
	public static final FeatureItem<ItemRegistry> REGISTRY = features().createItem("registry", ItemRegistry::new);
	public static final FeatureItem<ItemMasterRegistry> MASTER_REGISTRY = features().createItem("master_registry", ItemMasterRegistry::new);
	public static final FeatureItem<ItemMisc<GeneticsItems>> GENETICS = features().createItem("misc", () -> new ItemMisc<>(CreativeTabGenetics.INSTANCE, GeneticsItems.values()));

	private static IFeatureRegistry features() {
		return Genetics.instance.registry(GeneticsModuleUIDs.CORE);
	}

	private GeneticItems() {
	}
}
