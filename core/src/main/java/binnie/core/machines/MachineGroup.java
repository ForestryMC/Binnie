package binnie.core.machines;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;

import binnie.core.Binnie;
import binnie.core.features.FeatureBlock;
import binnie.core.features.FeatureBlockBased;
import binnie.core.features.IFeatureRegistry;
import binnie.core.modules.ModuleProvider;

public class MachineGroup extends FeatureBlockBased<BlockMachine, ItemMachine> {
	private final Map<String, MachinePackage> packages = new LinkedHashMap<>();
	private final Map<Integer, MachinePackage> packagesID = new LinkedHashMap<>();
	private final String uid;
	private final IMachineType[] types;

	public MachineGroup(ModuleProvider mod, String uid, String identifier, IMachineType[] types) {
		super(mod.registry(mod.getModId() + ".core"), identifier);
		setBlock(new FeatureBlock<>(registry, identifier, () -> new BlockMachine(this, identifier), ItemMachine::new));
		init();
		this.uid = uid;
		this.types = types;
	}

	public MachineGroup(IFeatureRegistry registry, String uid, String identifier, IMachineType... types) {
		super(registry, identifier);
		setBlock(new FeatureBlock<>(registry, identifier, () -> new BlockMachine(this, identifier), ItemMachine::new));
		init();
		this.types = types;
		this.uid = uid;
	}

	@Override
	protected void create() {
		super.create();
		createPackages(types);
		Binnie.MACHINE.registerMachineGroup(this);
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

	private void addPackage(final MachinePackage pack) {
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
	public MachinePackage getPackage(final int metadata) {
		return this.packagesID.get(metadata);
	}

	public MachinePackage getPackage(final String name) {
		return this.packages.get(name);
	}

	public String getUID() {
		return registry.getModId() + '.' + this.uid;
	}

	public String getShortUID() {
		return this.uid;
	}

	public MachineGroup setCreativeTab(final CreativeTabs tab) {
		onBlock(block -> block.setCreativeTab(tab));
		return this;
	}
}
