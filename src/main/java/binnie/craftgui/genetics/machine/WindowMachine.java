package binnie.craftgui.genetics.machine;

import binnie.craftgui.minecraft.Window;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public abstract class WindowMachine extends Window {
    public WindowMachine(final int width, final int height, final EntityPlayer player, final IInventory inventory, final Side side) {
        super(width, height, player, inventory, side);
    }

    public abstract String getTitle();

    @Override
    public void initialiseClient() {
        this.setTitle(this.getTitle());
    }
}
