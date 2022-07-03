package binnie.core.triggers;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieIcon;
import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.IStatement;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IStatementParameter;
import buildcraft.api.statements.StatementManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

class BinnieAction implements IActionExternal {
    private static int incrementalID = 800;
    public static BinnieAction actionPauseProcess;
    public static BinnieAction actionCancelTask;
    private String desc;
    private BinnieIcon icon;
    private String tag;
    private int id;

    BinnieAction(String desc, String tag, String iconFile) {
        this(desc, tag, BinnieCore.instance, iconFile);
    }

    private BinnieAction(String desc, String tag, AbstractMod mod, String iconFile) {
        // TODO why? Where is used?
        id = BinnieAction.incrementalID++;
        this.tag = tag;
        StatementManager.registerStatement(this);
        icon = Binnie.Resource.getItemIcon(mod, iconFile);
        this.desc = desc;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public String getUniqueTag() {
        return tag;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon() {
        return icon.getIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icon.registerIcon(iconRegister);
    }

    @Override
    public int maxParameters() {
        return 0;
    }

    @Override
    public int minParameters() {
        return 0;
    }

    @Override
    public IStatementParameter createParameter(int index) {
        return null;
    }

    @Override
    public IStatement rotateLeft() {
        return this;
    }

    @Override
    public void actionActivate(
            TileEntity target, ForgeDirection side, IStatementContainer source, IStatementParameter[] parameters) {
        // ignored
    }
}
