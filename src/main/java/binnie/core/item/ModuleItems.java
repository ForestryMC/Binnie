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

	@SubscribeEvent
	public void onUseFieldKit(PlayerInteractEvent.RightClickBlock event) {
		if (!BinnieCore.isBotanyActive()) {
			return;
		}
		BlockPos pos = event.getPos();
		World world = event.getWorld();
		if (!world.isRemote) {
			EntityPlayer player = event.getEntityPlayer();

			if (player != null) {
				ItemStack heldItem = player.getHeldItemMainhand();
				if (!heldItem.isEmpty() && heldItem.getItem() == BinnieCore.getFieldKit() && player.isSneaking()) {
					TileEntity tile = world.getTileEntity(pos);
					if (tile instanceof TileEntityFlower) {
						TileEntityFlower tileFlower = (TileEntityFlower) tile;
						IFlower flower = tileFlower.getFlower();
						if (flower != null) {
							IFlowerGenome flowerGenome = flower.getGenome();
							NBTTagCompound info = new NBTTagCompound();
							info.setString("Species", flowerGenome.getPrimary().getUID());
							info.setString("Species2", flowerGenome.getSecondary().getUID());
							info.setFloat("Age", flower.getAge() / flowerGenome.getLifespan());
							info.setShort("Colour", (short) flowerGenome.getPrimaryColor().getID());
							info.setShort("Colour2", (short) flowerGenome.getSecondaryColor().getID());
							info.setBoolean("Wilting", flower.isWilted());
							info.setBoolean("Flowered", flower.hasFlowered());
							//TODO: Find out why minecraft post 2 messages
							Botany.proxy.sendToPlayer(new MessageNBT(PacketID.FIELDKIT.ordinal(), info), player);
							heldItem.damageItem(1, event.getEntityPlayer());
						}
					}
				}
			}
		}
	}
}
