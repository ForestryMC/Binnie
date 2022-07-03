package binnie.core.gui;

import binnie.core.craftgui.WindowFieldKit;
import binnie.core.craftgui.WindowGenesis;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.machines.storage.WindowCompartment;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public enum BinnieCoreGUI implements IBinnieGUID {
    Compartment,
    FieldKit,
    Genesis;

    public Window getWindow(EntityPlayer player, IInventory object, Side side) throws Exception {
        switch (this) {
            case Compartment:
                return new WindowCompartment(player, object, side);

            case FieldKit:
                return new WindowFieldKit(player, null, side);

            case Genesis:
                return new WindowGenesis(player, null, side);
        }
        return null;
    }

    @Override
    public Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        IInventory object = null;
        if (tileEntity instanceof IInventory) {
            object = (IInventory) tileEntity;
        }

        Window window = null;
        try {
            window = getWindow(player, object, side);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return window;
    }
}
