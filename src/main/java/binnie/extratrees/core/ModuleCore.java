// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.core;

import java.util.Queue;
import java.io.IOException;

import binnie.extratrees.carpentry.EnumPattern;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.genetics.ExtraTreeFruitGene;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.block.ILogType;
import binnie.extratrees.genetics.ExtraTreeSpecies;
import forestry.api.arboriculture.IAlleleTreeSpecies;

import java.util.LinkedList;
import java.io.PrintWriter;
import java.io.FileWriter;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;

public class ModuleCore implements IInitializable
{
	@Override
	public void preInit() {
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		if (BinnieCore.proxy.isDebug()) {
			try {
				final PrintWriter outputSpecies = new PrintWriter(new FileWriter("data/species.html"));
				final PrintWriter outputLogs = new PrintWriter(new FileWriter("data/logs.html"));
				final PrintWriter outputPlanks = new PrintWriter(new FileWriter("data/planks.html"));
				final PrintWriter outputFruit = new PrintWriter(new FileWriter("data/fruit.html"));
				final PrintWriter outputDesigns = new PrintWriter(new FileWriter("data/designs.html"));
				final Queue<IAlleleTreeSpecies> speciesQueue = new LinkedList<IAlleleTreeSpecies>();
				for (final IAlleleTreeSpecies s : ExtraTreeSpecies.values()) {
					speciesQueue.add(s);
				}
				final Queue<ILogType> logQueue = new LinkedList<ILogType>();
				for (final ILogType wood : ILogType.ExtraTreeLog.values()) {
					logQueue.add(wood);
				}
				final Queue<IDesignMaterial> plankQueue = new LinkedList<IDesignMaterial>();
				for (final IDesignMaterial wood2 : PlankType.ExtraTreePlanks.values()) {
					plankQueue.add(wood2);
				}
				final Queue<ExtraTreeFruitGene> fruitQueue = new LinkedList<ExtraTreeFruitGene>();
				for (final ExtraTreeFruitGene wood3 : ExtraTreeFruitGene.values()) {
					fruitQueue.add(wood3);
				}
				final Queue<IDesign> designQueue = new LinkedList<IDesign>();
				for (final IDesign wood4 : CarpentryManager.carpentryInterface.getSortedDesigns()) {
					designQueue.add(wood4);
				}
				fruitQueue.remove(ExtraTreeFruitGene.Apple);
				outputSpecies.println("<table style=\"width: 100%;\">");
				while (!speciesQueue.isEmpty()) {
					outputSpecies.println("<tr>");
					for (int i = 0; i < 4; ++i) {
						final IAlleleTreeSpecies species = speciesQueue.poll();
						outputSpecies.println("<td>" + ((species == null) ? "" : species.getName()) + "</td>");
					}
					outputSpecies.println("</tr>");
				}
				outputSpecies.println("</table>");
				outputLogs.println("<table style=\"width: 100%;\">");
				while (!logQueue.isEmpty()) {
					outputLogs.println("<tr>");
					for (int i = 0; i < 4; ++i) {
						final ILogType.ExtraTreeLog wood5 = (ILogType.ExtraTreeLog) logQueue.poll();
						if (wood5 == null) {
							outputLogs.println("<td></td>");
						}
						else {
							final String img = "<img alt=\"" + wood5.getName() + "\" src=\"images/logs/" + wood5.toString().toLowerCase() + "Bark.png\">";
							outputLogs.println("<td>" + img + " " + wood5.getName() + "</td>");
						}
					}
					outputLogs.println("</tr>");
				}
				outputLogs.println("</table>");
				outputPlanks.println("<table style=\"width: 100%;\">");
				while (!plankQueue.isEmpty()) {
					outputPlanks.println("<tr>");
					for (int i = 0; i < 4; ++i) {
						final IDesignMaterial wood2 = plankQueue.poll();
						if (wood2 == null) {
							outputPlanks.println("<td></td>");
						}
						else {
							final String img = "<img alt=\"" + wood2.getName() + "\" src=\"images/planks/" + wood2.getName() + ".png\">";
							outputPlanks.println("<td>" + img + " " + wood2.getName() + "</td>");
						}
					}
					outputPlanks.println("</tr>");
				}
				outputPlanks.println("</table>");
				outputFruit.println("<table style=\"width: 100%;\">");
				while (!fruitQueue.isEmpty()) {
					outputFruit.println("<tr>");
					for (int i = 0; i < 4; ++i) {
						final ExtraTreeFruitGene wood6 = fruitQueue.poll();
						if (wood6 == null) {
							outputFruit.println("<td></td>");
						}
						else {
							final String fruit = wood6.getNameOfFruit();
							final String img2 = "<img alt=\"" + wood6.getName() + "\" src=\"images/fruits/" + fruit + ".png\">";
							outputFruit.println("<td>" + img2 + " " + wood6.getName() + "</td>");
						}
					}
					outputFruit.println("</tr>");
				}
				outputFruit.println("</table>");
				outputDesigns.println("<table style=\"width: 100%;\">");
				while (!designQueue.isEmpty()) {
					outputDesigns.println("<tr>");
					for (int i = 0; i < 4; ++i) {
						final IDesign wood4 = designQueue.poll();
						if (wood4 == null) {
							outputDesigns.println("<td></td>");
						}
						else {
							final String texture = ((EnumPattern) wood4.getTopPattern().getPattern()).toString().toLowerCase();
							final String img2 = "<img alt=\"" + texture + "\" src=\"images/pattern/" + texture + ".png\">";
							outputDesigns.println("<td>" + img2 + " " + wood4.getName() + "</td>");
						}
					}
					outputDesigns.println("</tr>");
				}
				outputDesigns.println("</table>");
				outputSpecies.close();
				outputLogs.close();
				outputPlanks.close();
				outputFruit.close();
				outputDesigns.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
