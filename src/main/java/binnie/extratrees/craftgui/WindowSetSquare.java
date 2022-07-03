package binnie.extratrees.craftgui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.minecraft.Window;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public class WindowSetSquare extends Window {
    public WindowSetSquare(EntityPlayer player, IInventory inventory, Side side) {
        super(150.0f, 150.0f, player, inventory, side);
    }

    public static Window create(EntityPlayer player, World world, int x, int y, int z, Side side) {
        return new WindowSetSquare(player, null, side);
    }

    @Override
    protected AbstractMod getMod() {
        return ExtraTrees.instance;
    }

    @Override
    protected String getName() {
        return null;
    }

    @Override
    public void initialiseClient() {
        // ignored
    }
}
