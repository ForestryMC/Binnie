package binnie.extratrees.core;

import binnie.core.craftgui.minecraft.Window;
import binnie.core.gui.IBinnieGUID;
import binnie.extratrees.craftgui.WindowArboristDatabase;
import binnie.extratrees.craftgui.WindowLepidopteristDatabase;
import binnie.extratrees.craftgui.WindowLumbermill;
import binnie.extratrees.craftgui.WindowSetSquare;
import binnie.extratrees.craftgui.WindowWoodworker;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public enum ExtraTreesGUID implements IBinnieGUID {
    Database,
    Woodworker,
    Lumbermill,
    DatabaseNEI,
    Incubator,
    MothDatabase,
    MothDatabaseNEI,
    Infuser,
    SetSquare;

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
                window = WindowArboristDatabase.create(player, side, this != ExtraTreesGUID.Database);
                break;

            case Woodworker:
                window = WindowWoodworker.create(player, object, side);
                break;

            case Lumbermill:
                window = WindowLumbermill.create(player, object, side);
                break;

            case MothDatabase:
            case MothDatabaseNEI:
                window = WindowLepidopteristDatabase.create(player, side, this != ExtraTreesGUID.MothDatabase);
                break;

            case SetSquare:
                window = WindowSetSquare.create(player, world, x, y, z, side);
                break;
        }
        return window;
    }
}
