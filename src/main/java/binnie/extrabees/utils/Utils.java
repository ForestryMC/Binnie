package binnie.extrabees.utils;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.common.registry.ForgeRegistries;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeRoot;
import forestry.apiculture.genetics.BeeDefinition;

public class Utils {

	public static IBeeRoot getBeeRoot() {
		return BeeManager.beeRoot;
	}

	public static IAlleleBeeSpecies getSpecies(BeeDefinition species) {
		return species.getGenome().getPrimary();
	}

	@Nullable
	public static Item getIC2Item(String name) {
		return getModItem("ic2", name);
	}

	@Nullable
	public static Item getBotaniaItem(String name) {
		return getModItem("botania", name);
	}

	@Nullable
	public static Block getBotaniaBlock(String name) {
		ResourceLocation key = new ResourceLocation("botania", name);
		return ForgeRegistries.BLOCKS.containsKey(key) ? ForgeRegistries.BLOCKS.getValue(key) : null;
	}

	private static Item getModItem(String mod, String name) {
		ResourceLocation key = new ResourceLocation(mod, name);
		return ForgeRegistries.ITEMS.containsKey(key) ? ForgeRegistries.ITEMS.getValue(key) : null;
	}

	@Nullable
	public static <T> T getCapability(World world, BlockPos pos, Capability<T> capability, EnumFacing facing) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity != null && tileEntity.hasCapability(capability, facing)) {
			return tileEntity.getCapability(capability, facing);
		}
		return null;
	}

	public static FluidStack getFluidFromName(String name, int amount) {
		return FluidRegistry.getFluidStack(name.toLowerCase(), amount);
	}
}
