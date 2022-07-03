package binnie.extrabees.core;

import binnie.core.craftgui.minecraft.Window;
import binnie.core.gui.IBinnieGUID;
import binnie.extrabees.gui.WindowAlvearyFrame;
import binnie.extrabees.gui.WindowAlvearyHatchery;
import binnie.extrabees.gui.WindowAlvearyMutator;
import binnie.extrabees.gui.WindowAlvearyStimulator;
import binnie.extrabees.gui.database.WindowApiaristDatabase;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public enum ExtraBeeGUID implements IBinnieGUID {
    Database,
    DatabaseNEI,
    AlvearyMutator,
    AlvearyFrame,
    AlvearyStimulator,
    PunnettSquare,
    AlvearyHatchery;

    @Override
    public Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side) {
        Window window = null;
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        IInventory object = null;
        if (tileEntity instanceof IInventory) {
            object = (IInventory) tileEntity;
        }

        switch (this) {
            case Database:
            case DatabaseNEI:
                window = WindowApiaristDatabase.create(player, side, this != ExtraBeeGUID.Database);
                break;

            case AlvearyMutator:
                window = WindowAlvearyMutator.create(player, object, side);
                break;

            case AlvearyFrame:
                window = WindowAlvearyFrame.create(player, object, side);
                break;

            case AlvearyStimulator:
                window = WindowAlvearyStimulator.create(player, object, side);
                break;

            case AlvearyHatchery:
                window = WindowAlvearyHatchery.create(player, object, side);
                break;
        }
        return window;
    }
}
