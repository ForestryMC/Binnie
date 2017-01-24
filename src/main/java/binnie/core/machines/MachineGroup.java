package binnie.core.machines;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class MachineGroup {
	private AbstractMod mod;
	private String blockName;
	private String uid;
	private Map<String, MachinePackage> packages;
	private Map<Integer, MachinePackage> packagesID;
	private BlockMachine block;
	public boolean customRenderer;
	private boolean renderedTileEntity;

	public MachineGroup(final AbstractMod mod, final String uid, final String blockName, final IMachineType[] types) {
		this.packages = new LinkedHashMap<>();
		this.packagesID = new LinkedHashMap<>();
		this.customRenderer = true;
		this.renderedTileEntity = true;
		this.mod = mod;
		this.uid = uid;
		this.blockName = blockName;
		for (final IMachineType type : types) {
			if (type.getPackageClass() != null) {
				if (type.isActive()) {
					try {
						final MachinePackage pack = type.getPackageClass().newInstance();
						pack.assignMetadata(type.ordinal());
						pack.setActive(type.isActive());
						this.addPackage(pack);
					} catch (InstantiationException | IllegalAccessException e) {
						throw new RuntimeException("Failed to create machine package " + type.toString(), e);
					}
				}
			}
		}
		Binnie.MCHINE.registerMachineGroup(this);
		this.block = new BlockMachine(this, blockName);
		mod.getProxy().registerBlock(this.block);
		Item i = mod.getProxy().registerItem(new ItemMachine(this.block));
		for (int j = 0; j < types.length; j++) {
			BinnieCore.proxy.registermodel(i, j, new ModelResourceLocation(i.getRegistryName(), "machine_type=" + j));
		}

		for (final MachinePackage pack2 : this.getPackages()) {
			pack2.register();
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

	boolean isTileEntityRenderered() {
		return this.renderedTileEntity;
	}

	public void renderAsBlock() {
		this.renderedTileEntity = false;
	}

	public void setCreativeTab(final CreativeTabs tab) {
		this.block.setCreativeTab(tab);
	}

	public AbstractMod getMod() {
		return this.mod;
	}
}
