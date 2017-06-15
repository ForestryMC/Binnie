/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package binnie.extrabees.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.extrabees.alveary.BlockAlveary;
import binnie.extrabees.alveary.EnumAlvearyLogicType;

public class ItemBlockAlveary extends ItemBlock implements IItemModelProvider {
	public ItemBlockAlveary(Block block) {
		super(block);
		setRegistryName(block.getRegistryName());
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ((BlockAlveary) block).getUnlocalizedName(stack.getMetadata());
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item) {
		int i = 0;
		for(EnumAlvearyLogicType type : EnumAlvearyLogicType.values()){
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation("extrabees:alveary", "type=" + type.getName()));
			i++;
		}
	}
}
