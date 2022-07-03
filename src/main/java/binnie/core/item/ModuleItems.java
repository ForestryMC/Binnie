package binnie.core.item;

import binnie.botany.Botany;
import binnie.botany.api.IFlower;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.network.PacketID;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.network.packet.MessageNBT;
import binnie.genetics.item.ItemFieldKit;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

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
                "g  ",
                " is",
                " pi",
                'g',
                Blocks.glass_pane,
                'i',
                Items.iron_ingot,
                'p',
                Items.paper,
                's',
                new ItemStack(Items.dye, 1));
    }

    @SubscribeEvent
    public void onUseFieldKit(PlayerInteractEvent event) {
        if (!BinnieCore.isBotanyActive() || event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        EntityPlayer player = event.entityPlayer;
        if (player != null
                && player.getHeldItem() != null
                && player.getHeldItem().getItem() == BinnieCore.fieldKit
                && player.isSneaking()) {
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
            info.setShort(
                    "Colour2", (short) flower.getGenome().getSecondaryColor().getID());
            info.setBoolean("Wilting", flower.isWilted());
            info.setBoolean("Flowered", flower.hasFlowered());
            Botany.proxy.sendToPlayer(new MessageNBT(PacketID.FieldKit.ordinal(), info), event.entityPlayer);
            event.entityPlayer.getHeldItem().damageItem(1, event.entityPlayer);
        }
    }
}
