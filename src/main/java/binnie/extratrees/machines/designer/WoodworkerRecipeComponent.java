package binnie.extratrees.machines.designer;

import binnie.core.machines.Machine;
import binnie.core.machines.component.ComponentRecipe;
import binnie.core.machines.component.IComponentRecipe;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IErrorStateSource;
import binnie.core.util.I18N;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.carpentry.EnumDesign;
import cpw.mods.fml.relauncher.Side;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WoodworkerRecipeComponent extends ComponentRecipe
        implements IComponentRecipe, INetwork.GuiNBT, IErrorStateSource {
    public DesignerType type;

    private IDesign design;

    public WoodworkerRecipeComponent(Machine machine, DesignerType type) {
        super(machine);
        design = EnumDesign.Diamond;
        this.type = type;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        setDesign(CarpentryManager.carpentryInterface.getDesign(nbttagcompound.getInteger("design")));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("design", CarpentryManager.carpentryInterface.getDesignIndex(design));
    }

    @Override
    public boolean isRecipe() {
        return getProduct() != null;
    }

    @Override
    public ItemStack getProduct() {
        ItemStack plank1 = getUtil().getStack(Designer.DESIGN_1_SLOT);
        ItemStack plank2 = getUtil().getStack(Designer.DESIGN_2_SLOT);
        if (plank1 == null || plank2 == null) {
            return null;
        }

        IDesignMaterial type1 = type.getSystem().getMaterial(plank1);
        IDesignMaterial type2 = type.getSystem().getMaterial(plank2);
        IDesign design = getDesign();
        ItemStack stack = type.getBlock(type1, type2, design);
        return stack;
    }

    @Override
    public ItemStack doRecipe(boolean takeItem) {
        if (!isRecipe() || canWork() != null) {
            return null;
        }

        ItemStack product = getProduct();
        if (takeItem) {
            ItemStack a = getUtil().decreaseStack(Designer.DESIGN_1_SLOT, 1);
            if (a == null) {
                getUtil().decreaseStack(Designer.DESIGN_2_SLOT, 1);
            } else if (design != EnumDesign.Blank) {
                getUtil().decreaseStack(Designer.DESIGN_2_SLOT, 1);
            }
            getUtil().decreaseStack(Designer.GLUE_SLOT, 1);
        }
        return product;
    }

    public IDesign getDesign() {
        return design;
    }

    private void setDesign(IDesign design) {
        this.design = design;
    }

    @Override
    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
        if (name.equals("recipe")) {
            InventoryPlayer playerInv = player.inventory;
            ItemStack recipe = doRecipe(false);
            if (recipe == null) {
                return;
            }

            if (playerInv.getItemStack() == null) {
                playerInv.setItemStack(doRecipe(true));
            } else if (playerInv.getItemStack().isItemEqual(recipe)
                    && ItemStack.areItemStackTagsEqual(playerInv.getItemStack(), recipe)) {
                int fit = recipe.getMaxStackSize() - (recipe.stackSize + playerInv.getItemStack().stackSize);
                if (fit >= 0) {
                    ItemStack doRecipe;
                    ItemStack rec = doRecipe = doRecipe(true);
                    doRecipe.stackSize += playerInv.getItemStack().stackSize;
                    playerInv.setItemStack(rec);
                }
            }

            player.openContainer.detectAndSendChanges();
            if (player instanceof EntityPlayerMP) {
                ((EntityPlayerMP) player).updateHeldItem();
            }
        } else if (name.equals("design")) {
            setDesign(CarpentryManager.carpentryInterface.getDesign(nbt.getShort("d")));
        }
    }

    @Override
    public ErrorState canWork() {
        if (getUtil().isSlotEmpty(Designer.GLUE_SLOT)) {
            return new ErrorState.NoItem(
                    I18N.localise("extratrees.machine.worker.error.glueRequired"), Designer.GLUE_SLOT);
        }
        return null;
    }

    @Override
    public ErrorState canProgress() {
        return null;
    }

    @Override
    public void sendGuiNBT(Map<String, NBTTagCompound> nbts) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setShort("d", (short) CarpentryManager.carpentryInterface.getDesignIndex(getDesign()));
        nbts.put("design", tag);
    }

    public DesignerType getDesignerType() {
        return type;
    }
}
