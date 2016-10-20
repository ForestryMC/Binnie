package binnie.craftgui.minecraft.control;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.MinecraftTooltip;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.inventory.IInventory;
import org.lwjgl.opengl.GL11;

public class ControlEnergyBar extends Control implements ITooltip {
    public static boolean isError;
    private Position direction;

    public ControlEnergyBar(final IWidget parent, final int x, final int y, final int width, final int height, final Position direction) {
        super(parent, x, y, width, height);
        this.direction = direction;
        this.addAttribute(Attribute.MouseOver);
    }

    public IPoweredMachine getClientPower() {
        final IInventory inventory = Window.get(this).getInventory();
        final TileEntityMachine machine = (inventory instanceof TileEntityMachine) ? (TileEntityMachine) inventory : null;
        if (machine == null) {
            return null;
        }
        final IPoweredMachine clientPower = machine.getMachine().getInterface(IPoweredMachine.class);
        return clientPower;
    }

    public float getPercentage() {
        float percentage = 100.0f * this.getStoredEnergy() / this.getMaxEnergy();
        if (percentage > 100.0f) {
            percentage = 100.0f;
        }
        return percentage;
    }

    private float getStoredEnergy() {
        return Window.get(this).getContainer().getPowerInfo().getStoredEnergy();
    }

    private float getMaxEnergy() {
        return Window.get(this).getContainer().getPowerInfo().getMaxEnergy();
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
        tooltip.add((int) this.getPercentage() + "% charged");
        tooltip.add(this.getStoredEnergy() + "/" + this.getMaxEnergy() + " RF");
    }

    @Override
    public void getHelpTooltip(final Tooltip tooltip) {
        tooltip.add("Energy Bar");
        tooltip.add("Current: " + this.getStoredEnergy() + " RF (" + (int) this.getPercentage() + "%)");
        tooltip.add("Capacity: " + this.getMaxEnergy() + " RF");
        final IProcess process = Machine.getInterface(IProcess.class, Window.get(this).getInventory());
        if (process != null) {
            tooltip.add("Usage: " + (int) process.getEnergyPerTick() + " RF");
        }
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.Render.texture(CraftGUITexture.EnergyBarBack, this.getArea());
        final float percentage = this.getPercentage() / 100.0f;
        CraftGUI.Render.colour(this.getColourFromPercentage(percentage));
        final IArea area = this.getArea();
        switch (this.direction) {
            case Top:
            case Bottom: {
                final float height = area.size().y() * percentage;
                area.setSize(new IPoint(area.size().x(), height));
                break;
            }
            case Left:
            case Right: {
                final float width = area.size().x() * percentage;
                area.setSize(new IPoint(width, area.size().y()));
                break;
            }
        }
        if (this.isMouseOver() && Window.get(this).getGui().isHelpMode()) {
            final int c = -1442840576 + MinecraftTooltip.getOutline(Tooltip.Type.Help);
            CraftGUI.Render.gradientRect(this.getArea().inset(1), c, c);
        } else if (ControlEnergyBar.isError) {
            final int c = -1442840576 + MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error);
            CraftGUI.Render.gradientRect(this.getArea().inset(1), c, c);
        }
        CraftGUI.Render.texture(CraftGUITexture.EnergyBarGlow, area);
        GL11.glColor3d(1.0, 1.0, 1.0);
        CraftGUI.Render.texture(CraftGUITexture.EnergyBarGlass, this.getArea());
    }

    @Override
    public void onRenderForeground() {
        if (this.isMouseOver() && Window.get(this).getGui().isHelpMode()) {
            final IArea area = this.getArea();
            CraftGUI.Render.colour(MinecraftTooltip.getOutline(Tooltip.Type.Help));
            CraftGUI.Render.texture(CraftGUITexture.Outline, area.outset(1));
        } else if (ControlEnergyBar.isError) {
            final IArea area = this.getArea();
            CraftGUI.Render.colour(MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error));
            CraftGUI.Render.texture(CraftGUITexture.Outline, area.outset(1));
        }
    }

    public int getColourFromPercentage(final float percentage) {
        int colour = 16777215;
        if (percentage > 0.5) {
            final int r = (int) ((1.0 - 2.0 * (percentage - 0.5)) * 255.0);
            colour = (r << 16) + 65280;
        } else {
            final int g = (int) (255.0f * (2.0f * percentage));
            colour = 16711680 + (g << 8);
        }
        return colour;
    }
}
