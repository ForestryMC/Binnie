package binnie.core.item;

import binnie.botany.Botany;
import binnie.botany.api.IFlower;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.network.PacketID;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.network.packet.MessageNBT;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleItems implements IInitializable {
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
        GameRegistry.addRecipe(new ItemStack(BinnieCore.fieldKit, 1, 63), "g  ", " is", " pi", 'g', Blocks.GLASS_PANE, 'i', Items.IRON_INGOT, 'p', Items.PAPER, 's', new ItemStack(Items.DYE, 1));
    }

    @SubscribeEvent
    public void onUseFieldKit(final PlayerInteractEvent.RightClickBlock event) {
        if (!BinnieCore.isBotanyActive()) {
            return;
        }

        if (event.getEntityPlayer() != null && event.getEntityPlayer().getHeldItemMainhand() != null && event.getEntityPlayer().getHeldItemMainhand().getItem() == BinnieCore.fieldKit && event.getEntityPlayer().isSneaking()) {
            final TileEntity tile = event.getWorld().getTileEntity(event.getPos());
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
                    Botany.proxy.sendToPlayer(new MessageNBT(PacketID.Encylopedia.ordinal(), info), event.getEntityPlayer());
                    event.getEntityPlayer().getHeldItemMainhand().damageItem(1, event.getEntityPlayer());
                }
            }
        }
    }
}
