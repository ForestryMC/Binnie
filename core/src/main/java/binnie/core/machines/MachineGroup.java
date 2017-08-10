package binnie.core.machines;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import binnie.core.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;

public class MachineGroup {
	private AbstractMod mod;
	private String uid;
	private Map<String, MachinePackage> packages;
	private Map<Integer, MachinePackage> packagesID;
	private BlockMachine block;

	public MachineGroup(final AbstractMod mod, final String uid, final String blockName, final IMachineType[] types) {
		this.packages = new LinkedHashMap<>();
		this.packagesID = new LinkedHashMap<>();
		this.mod = mod;
		this.uid = uid;
		createPackages(types);
		Binnie.MACHINE.registerMachineGroup(this);
		this.block = new BlockMachine(this, blockName);
		mod.getProxy().registerBlock(this.block);
		Item i = mod.getProxy().registerItem(new ItemMachine(this.block));
		for (int j = 0; j < types.length; j++) {
			BinnieCore.getBinnieProxy().registerModel(i, j, new ModelResourceLocation(i.getRegistryName(), "machine_type=" + j));
		}

		for (final MachinePackage pack : this.getPackages()) {
			pack.register();
		}
	}

	private void createPackages(IMachineType[] types) {
		for (IMachineType type : types) {
			if (type.getPackageClass() != null) {
				if (type.isActive()) {
					try {
						MachinePackage pack = type.getPackageClass().newInstance();
						pack.assignMetadata(type.ordinal());
						pack.setActive(type.isActive());
						this.addPackage(pack);
					} catch (InstantiationException | IllegalAccessException e) {
						throw new RuntimeException("Failed to create machine package " + type.toString(), e);
					}
				}
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

	public BlockMachine getBlock() {
		return this.block;
	}

	@Nullable
	public MachinePackage getPackage(final int metadata) {
		return this.packagesID.get(metadata);
	}

	public MachinePackage getPackage(final String name) {
		return this.packages.get(name);
	}

	public String getUID() {
		return this.mod.getModID() + "." + this.uid;
	}

	public String getShortUID() {
		return this.uid;
	}

	public void setCreativeTab(final CreativeTabs tab) {
		this.block.setCreativeTab(tab);
	}

	public AbstractMod getMod() {
		return this.mod;
	}
}
