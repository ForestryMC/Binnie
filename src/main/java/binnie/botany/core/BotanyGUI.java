package binnie.botany.core;

import binnie.botany.craftgui.WindowBotanistDatabase;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.gui.IBinnieGUID;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public enum BotanyGUI implements IBinnieGUID {
    Database,
    DatabaseNEI;

    @Override
    public Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side) {
        Window window = null;
        switch (this) {
            case Database:
            case DatabaseNEI: {
                window = WindowBotanistDatabase.create(player, side, this != BotanyGUI.Database);
                break;
            }
        }
        return window;
    }
}
