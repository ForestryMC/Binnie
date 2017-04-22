package binnie.core.liquid;

import binnie.core.*;
import binnie.genetics.item.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;

public enum FluidContainer {
	Bucket,
	Capsule,
	Refractory,
	Can,
	Glass,
	Cylinder;

	protected IIcon bottle;
	protected IIcon contents;
	protected ItemFluidContainer item;

	public int getMaxStackSize() {
		return (this == FluidContainer.Bucket) ? 1 : 16;
	}

	@SideOnly(Side.CLIENT)
	public void updateIcons(IIconRegister register) {
		String liquidName = "liquids/" + toString().toLowerCase();
		String modName;
		if (this == FluidContainer.Cylinder) {
			modName = "binniecore";
		} else {
			modName = "forestry";
		}
		bottle = BinnieCore.proxy.getIcon(register, modName, liquidName + ".bottle");
		contents = BinnieCore.proxy.getIcon(register, modName, liquidName + ".contents");
	}

	public IIcon getBottleIcon() {
		return bottle;
	}

	public IIcon getContentsIcon() {
		return contents;
	}

	public String getName() {
		return BinnieCore.proxy.localise("item.container." + name().toLowerCase());
	}

	public boolean isActive() {
		return getEmpty() != null;
	}

	public ItemStack getEmpty() {
		switch (this) {
			case Bucket:
				return new ItemStack(Items.bucket, 1, 0);

			case Can:
				return Mods.Forestry.stack("canEmpty");

			case Capsule:
				return Mods.Forestry.stack("waxCapsule");

			case Glass:
				return new ItemStack(Items.glass_bottle, 1, 0);

			case Refractory:
				return Mods.Forestry.stack("refractoryEmpty");

			case Cylinder:
				return GeneticsItems.Cylinder.get(1);
		}
		return null;
	}

	public void registerContainerData(IFluidType fluid) {
		if (!isActive()) {
			return;
		}

		ItemStack filled = item.getContainer(fluid);
		ItemStack empty = getEmpty();
		if (filled == null || empty == null || fluid.get(1000) == null) {
			return;
		}

		FluidContainerRegistry.FluidContainerData data = new FluidContainerRegistry.FluidContainerData(fluid.get(1000), filled, empty);
		FluidContainerRegistry.registerFluidContainer(data);
	}
}
