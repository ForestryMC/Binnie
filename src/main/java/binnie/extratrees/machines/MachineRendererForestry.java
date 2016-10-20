// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.machines;

import java.util.HashMap;

import net.minecraftforge.common.util.ForgeDirection;

import java.lang.reflect.Method;
import java.util.Map;

import forestry.core.render.TankRenderInfo;

public class MachineRendererForestry
{
	static Map<String, Object> instances;
	static Method renderMethod;

	private static void loadMethod(final String file, final boolean waterTank, final boolean productTank) {
		try {
			final Class clss = Class.forName("forestry.core.render.RenderMachine");
			final Object instance = clss.getConstructor(String.class).newInstance(file);
			MachineRendererForestry.renderMethod = clss.getDeclaredMethod("render", TankRenderInfo.class, TankRenderInfo.class, ForgeDirection.class, Double.TYPE, Double.TYPE, Double.TYPE);
			MachineRendererForestry.renderMethod.setAccessible(true);
			MachineRendererForestry.instances.put(file, instance);
		} catch (Exception ex) {
			// ex.printStackTrace();
		}
	}

	public static void renderMachine(final String name, final double x, final double y, final double z, final float var8) {
		if (!MachineRendererForestry.instances.containsKey(name)) {
			loadMethod(name, false, false);
		}
		try {
			MachineRendererForestry.renderMethod.invoke(MachineRendererForestry.instances.get(name), TankRenderInfo.EMPTY, TankRenderInfo.EMPTY, ForgeDirection.UP, x, y, z);
		} catch (Exception ex) {
			// ex.printStackTrace();
		}
	}

	static {
		MachineRendererForestry.instances = new HashMap<String, Object>();
	}
}
