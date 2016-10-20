// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines;

import net.minecraft.creativetab.CreativeTabs;
import java.util.Collection;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import binnie.Binnie;
import java.util.LinkedHashMap;
import java.util.Map;
import binnie.core.AbstractMod;

public class MachineGroup
{
	private AbstractMod mod;
	private String blockName;
	private String uid;
	private Map<String, MachinePackage> packages;
	private Map<Integer, MachinePackage> packagesID;
	private BlockMachine block;
	public boolean customRenderer;
	private boolean renderedTileEntity;

	public MachineGroup(final AbstractMod mod, final String uid, final String blockName, final IMachineType[] types) {
		this.packages = new LinkedHashMap<String, MachinePackage>();
		this.packagesID = new LinkedHashMap<Integer, MachinePackage>();
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
					} catch (Exception e) {
						throw new RuntimeException("Failed to create machine package " + type.toString(), e);
					}
				}
			}
		}
		Binnie.Machine.registerMachineGroup(this);
		this.block = new BlockMachine(this, blockName);
		if (this.block != null) {
			GameRegistry.registerBlock(this.block, ItemMachine.class, blockName);
			for (final MachinePackage pack2 : this.getPackages()) {
				pack2.register();
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
