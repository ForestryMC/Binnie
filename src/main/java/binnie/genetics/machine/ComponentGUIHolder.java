package binnie.genetics.machine;

import binnie.botany.Botany;
import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.network.INetwork;
import binnie.extrabees.ExtraBees;
import binnie.extratrees.ExtraTrees;
import binnie.genetics.Genetics;
import buildcraft.api.tools.IToolWrench;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ComponentGUIHolder extends MachineComponent implements INetwork.TilePacketSync, IInteraction.RightClick {
    private ItemStack stack;

    public ComponentGUIHolder(IMachine machine) {
        super(machine);
    }

    public ItemStack getStack() {
        return stack;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        if (nbttagcompound == null) {
            return;
        }
        stack = ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag("Item"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        NBTTagCompound nbt = new NBTTagCompound();
        if (stack != null) {
            stack.writeToNBT(nbt);
        }
        nbttagcompound.setTag("Item", nbt);
    }

    @Override
    public void syncToNBT(NBTTagCompound nbt) {
        writeToNBT(nbt);
    }

    @Override
    public void syncFromNBT(NBTTagCompound nbt) {
        readFromNBT(nbt);
    }

    @Override
    public void onDestruction() {
        super.onDestruction();
        if (stack == null) {
            return;
        }

        IMachine machine = getMachine();
        TileEntity tile = machine.getTileEntity();
        World world = machine.getWorld();
        Random rand = world.rand;
        float xOffset = rand.nextFloat() * 0.8f + 0.1f;
        float yOffset = rand.nextFloat() * 0.8f + 0.1f;
        float zOffset = rand.nextFloat() * 0.8f + 0.1f;
        if (stack.stackSize == 0) {
            stack.stackSize = 1;
        }

        EntityItem entityitem = new EntityItem(
                world, tile.xCoord + xOffset, tile.yCoord + yOffset, tile.zCoord + zOffset, stack.copy());
        float accel = 0.05f;
        entityitem.motionX = (float) rand.nextGaussian() * accel;
        entityitem.motionY = (float) rand.nextGaussian() * accel + 0.2f;
        entityitem.motionZ = (float) rand.nextGaussian() * accel;
        world.spawnEntityInWorld(entityitem);
    }

    @Override
    public void onRightClick(World world, EntityPlayer player, int x, int y, int z) {
        if (BinnieCore.proxy.isSimulating(world) && player.getHeldItem() != null) {
            if (stack != null && player.getHeldItem().getItem() instanceof IToolWrench) {
                Random rand = world.rand;
                float f = 0.7f;
                double fOffset = (1.0f - f) * 0.5;
                double xOffset = rand.nextFloat() * f + fOffset;
                double yOffset = rand.nextFloat() * f + fOffset;
                double zOffset = rand.nextFloat() * f + fOffset;

                EntityItem entityitem = new EntityItem(world, x + xOffset, y + yOffset, z + zOffset, stack);
                entityitem.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(entityitem);
                stack = null;
                ((IToolWrench) player.getHeldItem().getItem()).wrenchUsed(player, x, y, z);
                getUtil().refreshBlock();
                return;
            }

            List<Item> validSelections = new ArrayList<>();
            if (BinnieCore.isBotanyActive()) {
                validSelections.add(Botany.database);
            }
            if (BinnieCore.isExtraBeesActive()) {
                validSelections.add(ExtraBees.dictionary);
            }
            if (BinnieCore.isExtraTreesActive()) {
                validSelections.add(ExtraTrees.itemDictionary);
            }
            if (BinnieCore.isLepidopteryActive()) {
                validSelections.add(ExtraTrees.itemDictionaryLepi);
            }

            validSelections.add(Genetics.database);
            validSelections.add(Genetics.analyst);
            validSelections.add(Genetics.registry);
            validSelections.add(Genetics.masterRegistry);
            validSelections.add(BinnieCore.genesis);

            if (stack == null && validSelections.contains(player.getHeldItem().getItem())) {
                stack = player.getHeldItem().copy();
                ItemStack heldItem = player.getHeldItem();
                heldItem.stackSize--;
                world.markBlockForUpdate(x, y, z);
                return;
            }

            if (stack != null && player.getHeldItem().getItem() instanceof IToolWrench) {
                stack.getItem().onItemRightClick(stack, world, player);
            }
        }

        if (stack != null) {
            stack.getItem().onItemRightClick(stack, world, player);
        }
    }
}
