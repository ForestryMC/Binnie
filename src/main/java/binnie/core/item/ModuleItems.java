package binnie.core.item;

import binnie.botany.*;
import binnie.botany.api.*;
import binnie.botany.flower.*;
import binnie.botany.network.*;
import binnie.core.*;
import binnie.core.network.packet.*;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.event.entity.player.*;

public class ModuleItems implements IInitializable {
	@Override
	public void preInit() {
		BinnieCore.fieldKit = new ItemFieldKit();
		BinnieCore.genesis = new ItemGenesis();
	}

	@Override
	public void init() {
		// ignored
	}

	@Override
	public void postInit() {
		GameRegistry.addRecipe(
			new ItemStack(BinnieCore.fieldKit, 1, 63),
			"g  ", " is", " pi",
			'g', Blocks.glass_pane,
			'i', Items.iron_ingot,
			'p', Items.paper,
			's', new ItemStack(Items.dye, 1)
		);
	}

	@SubscribeEvent
	public void onUseFieldKit(PlayerInteractEvent event) {
		if (!BinnieCore.isBotanyActive() || event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (event.entityPlayer != null
			&& event.entityPlayer.getHeldItem() != null
			&& event.entityPlayer.getHeldItem().getItem() == BinnieCore.fieldKit
			&& event.entityPlayer.isSneaking()) {
			TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
			if (!(tile instanceof TileEntityFlower)) {
				return;
			}

			TileEntityFlower tileFlower = (TileEntityFlower) tile;
			IFlower flower = tileFlower.getFlower();
			if (flower == null) {
				return;
			}

			NBTTagCompound info = new NBTTagCompound();
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
