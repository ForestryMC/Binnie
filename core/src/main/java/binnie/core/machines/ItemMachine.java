package binnie.core.machines;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

import binnie.core.BinnieCore;

public class ItemMachine extends ItemBlock implements IItemModelRegister {
	private final IBlockMachine associatedBlock;

	public ItemMachine(Block block) {
		super(block);
		this.setHasSubtypes(true);
		this.associatedBlock = (IBlockMachine) block;
		setRegistryName(block.getRegistryName());
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		return this.associatedBlock.getMachineName(itemstack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		for (int i = 0; i < associatedBlock.getGroup().size(); i++) {
			BinnieCore.getBinnieProxy().registerModel(item, i, new ModelResourceLocation(getRegistryName(), "machine_type=" + i));
		}
	}
}
