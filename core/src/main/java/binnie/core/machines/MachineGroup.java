package binnie.core.machines;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IBlockFeature;
import binnie.core.features.IChildFeature;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.IModFeature;
import binnie.core.modules.BinnieCoreModuleUIDs;

public class MachineGroup implements IBlockFeature<BlockMachine, ItemMachine>, IChildFeature {
	private final Map<String, MachinePackage> packages = new LinkedHashMap<>();
	private final Map<Integer, MachinePackage> packagesID = new LinkedHashMap<>();
	private final String identifier;
	private final String uid;
	private final IFeatureConstructor<BlockMachine> constructorBlock;
	private final IFeatureConstructor<ItemMachine> constructorItem;
	private final IMachineType[] types;
	@Nullable
	private CreativeTabs tab = null;
	@Nullable
	private IModFeature parent;
	@Nullable
	private BlockMachine block;
	@Nullable
	private ItemMachine item;

	public MachineGroup(String uid, String identifier, IMachineType[] types) {
		this.uid = uid;
		this.types = types;
		this.identifier = identifier;
		this.constructorBlock = () -> new BlockMachine(this, identifier);
		this.constructorItem = () -> new ItemMachine(block);
	}

	private void createPackages(IMachineType... types) {
		for (IMachineType type : types) {
			MachinePackage pack = type.getSupplier().get();
			if (pack != null) {
				pack.assignMetadata(type.ordinal());
				pack.setActive(true);
				addPackage(pack);
			}
		}
	}

	private void addPackage(MachinePackage pack) {
		this.packages.put(pack.getUID(), pack);
		this.packagesID.put(pack.getMetadata(), pack);
		pack.setGroup(this);
	}

	public Collection<MachinePackage> getPackages() {
		return this.packages.values();
	}

	public int size() {
		return getPackages().size();
	}

	@Nullable
	public MachinePackage getPackage(int metadata) {
		return this.packagesID.get(metadata);
	}

	public MachinePackage getPackage(String name) {
		return this.packages.get(name);
	}

	public String getUID() {
		return getModId() + '.' + this.uid;
	}

	public String getShortUID() {
		return this.uid;
	}

	public MachineGroup setCreativeTab(CreativeTabs tab) {
		this.tab = tab;
		return this;
	}

	/* Block Feature */
	@Override
	public void setParent(IModFeature parent) {
		this.parent = parent;
	}

	@Nullable
	@Override
	public BlockMachine getBlock() {
		return block;
	}

	@Override
	public boolean hasBlock() {
		return block != null;
	}

	@Override
	public IFeatureConstructor<BlockMachine> getConstructor() {
		return constructorBlock;
	}

	@Nullable
	@Override
	public IFeatureConstructor<ItemMachine> getItemConstructor() {
		return constructorItem;
	}

	@Nullable
	@Override
	public ItemMachine getItem() {
		return item;
	}

	@Override
	public boolean hasItem() {
		return item != null;
	}

	@Override
	public BlockMachine apply(BlockMachine block) {
		if (tab != null) {
			block.setCreativeTab(tab);
		}
		return IBlockFeature.super.apply(block);
	}

	@Override
	public void init() {
		createPackages(types);
		Binnie.MACHINE.registerMachineGroup(this);
	}

	@Override
	public void setBlock(@Nullable BlockMachine block) {
		this.block = block;
	}

	@Override
	public void setItem(@Nullable ItemMachine item) {
		this.item = item;
	}

	@Override
	public FeatureType getType() {
		return FeatureType.BLOCK;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public String getModId() {
		return parent != null ? parent.getModId() : Constants.CORE_MOD_ID;
	}

	@Override
	public String getModuleId() {
		return parent != null ? parent.getModuleId() : BinnieCoreModuleUIDs.CORE;
	}
}
