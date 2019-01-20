package binnie.genetics.modules.features;

import javax.annotation.Nullable;

import net.minecraft.item.Item;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.IItemFeature;
import binnie.core.features.RegisterFeatureEvent;
import binnie.core.item.ItemMisc;
import binnie.core.modules.GeneticsModuleUIDs;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.item.ItemAnalyst;
import binnie.genetics.item.ItemDatabase;
import binnie.genetics.item.ItemMasterRegistry;
import binnie.genetics.item.ItemRegistry;
import binnie.genetics.item.ItemSequence;
import binnie.genetics.item.ItemSerum;
import binnie.genetics.item.ItemSerumArray;

@Mod.EventBusSubscriber(modid = Constants.GENETICS_MOD_ID)
public enum GeneticItems implements IItemFeature<Item, Item> {
	SERUM("serum", ItemSerum::new),
	SERUM_ARRAY("serum_array", ItemSerumArray::new),
	SEQUENCE("sequence", ItemSequence::new),
	DATABASE("geneticdatabase", ItemDatabase::new),
	ANALYST("analyst", ItemAnalyst::new),
	REGISTRY("registry", ItemRegistry::new),
	MASTER_REGISTRY("master_registry", ItemMasterRegistry::new),
	GENETICS("misc", () -> new ItemMisc<>(CreativeTabGenetics.INSTANCE, GeneticsItems.values()));

	private final String identifier;
	private final IFeatureConstructor<Item> constructor;
	@Nullable
	private Item item;

	GeneticItems(String identifier, IFeatureConstructor<Item> supplier) {
		this.identifier = identifier;
		this.constructor = supplier;
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(GeneticItems.class);
	}

	@Override
	public boolean hasItem() {
		return item != null;
	}

	@Override
	@Nullable
	public Item getItem() {
		return item;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public void setItem(@Nullable Item item) {
		this.item = item;
	}

	@Override
	public IFeatureConstructor<Item> getConstructor() {
		return constructor;
	}

	@Override
	public FeatureType getType() {
		return FeatureType.ITEM;
	}

	@Override
	public String getModId() {
		return Constants.GENETICS_MOD_ID;
	}

	@Override
	public String getModuleId() {
		return GeneticsModuleUIDs.CORE;
	}
}
