package binnie.extratrees.machines;

import forestry.core.render.TankRenderInfo;
import net.minecraft.util.EnumFacing;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MachineRendererForestry {
	static Map<String, Object> instances = new HashMap<>();
	static Method renderMethod;

	private static void loadMethod(final String file, final boolean waterTank, final boolean productTank) {
		try {
			final Class clss = Class.forName("forestry.core.render.RenderMachine");
			final Object instance = clss.getConstructor(String.class).newInstance(file);
			//MachineRendererForestry.renderMethod = clss.getDeclaredMethod("render", TankRenderInfo.class, TankRenderInfo.class, ForgeDirection.class, Double.TYPE, Double.TYPE, Double.TYPE);
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
			MachineRendererForestry.renderMethod.invoke(MachineRendererForestry.instances.get(name), TankRenderInfo.EMPTY, TankRenderInfo.EMPTY, EnumFacing.UP, x, y, z);
		} catch (Exception ex) {
			// ex.printStackTrace();
		}
	}

}
