package binnie.core.triggers;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieIcon;
import buildcraft.api.statements.IStatement;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IStatementParameter;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.StatementManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

class BinnieTrigger implements ITriggerExternal {
    private static int incrementalID = 800;
    protected static BinnieTrigger triggerNoBlankTemplate;
    protected static BinnieTrigger triggerNoTemplate;
    protected static BinnieTrigger triggerIsWorking;
    protected static BinnieTrigger triggerIsNotWorking;
    protected static BinnieTrigger triggerCanWork;
    protected static BinnieTrigger triggerCannotWork;
    protected static BinnieTrigger triggerPowerNone;
    protected static BinnieTrigger triggerPowerLow;
    protected static BinnieTrigger triggerPowerMedium;
    protected static BinnieTrigger triggerPowerHigh;
    protected static BinnieTrigger triggerPowerFull;
    protected static BinnieTrigger triggerSerumFull;
    protected static BinnieTrigger triggerSerumPure;
    protected static BinnieTrigger triggerSerumEmpty;
    protected static BinnieTrigger triggerAcclimatiserNone;
    protected static BinnieTrigger triggerAcclimatiserHot;
    protected static BinnieTrigger triggerAcclimatiserCold;
    protected static BinnieTrigger triggerAcclimatiserWet;
    protected static BinnieTrigger triggerAcclimatiserDry;
    private String desc;
    private String tag;
    private BinnieIcon icon;
    private int id;

    public BinnieTrigger(String desc, String tag, String iconFile) {
        this(desc, tag, BinnieCore.instance, iconFile);
    }

    public BinnieTrigger(String desc, String tag, AbstractMod mod, String iconFile) {
        // TODO why? Where is used?
        id = BinnieTrigger.incrementalID++;
        this.tag = tag;
        StatementManager.registerStatement(this);
        TriggerProvider.triggers.add(this);
        icon = Binnie.Resource.getItemIcon(mod, iconFile);
        this.desc = desc;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IIconRegister register) {
        return icon.getIcon(register);
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
        return null;
    }

    @Override
    public boolean isTriggerActive(
            TileEntity target, ForgeDirection side, IStatementContainer source, IStatementParameter[] parameters) {
        return false;
    }
}
