// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.item;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import binnie.botany.api.IFlower;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.EntityLivingBase;
import binnie.core.network.packet.MessageNBT;
import binnie.botany.network.PacketID;
import binnie.botany.Botany;
import net.minecraft.nbt.NBTTagCompound;
import binnie.botany.flower.TileEntityFlower;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;

public class ModuleItems implements IInitializable
{
	@Override
	public void preInit() {
		BinnieCore.fieldKit = new ItemFieldKit();
		BinnieCore.genesis = new ItemGenesis();
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		GameRegistry.addRecipe(new ItemStack(BinnieCore.fieldKit, 1, 63), new Object[] { "g  ", " is", " pi", 'g', Blocks.glass_pane, 'i', Items.iron_ingot, 'p', Items.paper, 's', new ItemStack(Items.dye, 1) });
	}

	@SubscribeEvent
	public void onUseFieldKit(final PlayerInteractEvent event) {
		if (!BinnieCore.isBotanyActive()) {
			return;
		}
		if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (event.entityPlayer != null && event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() == BinnieCore.fieldKit && event.entityPlayer.isSneaking()) {
			final TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
			if (tile instanceof TileEntityFlower) {
				final TileEntityFlower tileFlower = (TileEntityFlower) tile;
				final IFlower flower = tileFlower.getFlower();
				if (flower != null) {
					final NBTTagCompound info = new NBTTagCompound();
					info.setString("Species", flower.getGenome().getPrimary().getUID());
					info.setString("Species2", flower.getGenome().getSecondary().getUID());
					info.setFloat("Age", flower.getAge() / flower.getGenome().getLifespan());
					info.setShort("Colour", (short) flower.getGenome().getPrimaryColor().getID());
					info.setShort("Colour2", (short) flower.getGenome().getSecondaryColor().getID());
					info.setBoolean("Wilting", flower.isWilted());
					info.setBoolean("Flowered", flower.hasFlowered());
					Botany.proxy.sendToPlayer(new MessageNBT(PacketID.Encylopedia.ordinal(), info), event.entityPlayer);
					event.entityPlayer.getHeldItem().damageItem(1, event.entityPlayer);
				}
			}
		}
	}
}
