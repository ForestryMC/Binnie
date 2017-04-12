package binnie.core.item;

import binnie.botany.Botany;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.network.PacketID;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.network.packet.MessageNBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleItems implements IInitializable {
	@Override
	public void preInit() {
		BinnieCore.setFieldKit(new ItemFieldKit());
		BinnieCore.getBinnieProxy().registerItem(BinnieCore.getFieldKit());
		BinnieCore.setGenesis(new ItemGenesis());
		BinnieCore.getBinnieProxy().registerItem(BinnieCore.getGenesis());
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		GameRegistry.addRecipe(new ItemStack(BinnieCore.getFieldKit(), 1, 63), "g  ", " is", " pi", 'g', Blocks.GLASS_PANE, 'i', Items.IRON_INGOT, 'p', Items.PAPER, 's', new ItemStack(Items.DYE, 1));
	}
}
