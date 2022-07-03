package binnie.extratrees.core;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.block.ILogType;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.genetics.ExtraTreeFruitGene;
import binnie.extratrees.genetics.ExtraTreeSpecies;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class ModuleCore implements IInitializable {
    @Override
    public void preInit() {
        // ignored
    }

    @Override
    public void init() {
        // ignored
    }

    @Override
    public void postInit() {
        if (!BinnieCore.proxy.isDebug()) {
            return;
        }

        try {
            PrintWriter outputSpecies = new PrintWriter(new FileWriter("data/species.html"));
            PrintWriter outputLogs = new PrintWriter(new FileWriter("data/logs.html"));
            PrintWriter outputPlanks = new PrintWriter(new FileWriter("data/planks.html"));
            PrintWriter outputFruit = new PrintWriter(new FileWriter("data/fruit.html"));
            PrintWriter outputDesigns = new PrintWriter(new FileWriter("data/designs.html"));
            Queue<IAlleleTreeSpecies> speciesQueue = new LinkedList<>();
            Collections.addAll(speciesQueue, ExtraTreeSpecies.values());

            Queue<ILogType> logQueue = new LinkedList<>();
            Collections.addAll(logQueue, ILogType.ExtraTreeLog.values());

            Queue<IDesignMaterial> plankQueue = new LinkedList<>();
            Collections.addAll(plankQueue, PlankType.ExtraTreePlanks.values());

            Queue<ExtraTreeFruitGene> fruitQueue = new LinkedList<>();
            Collections.addAll(fruitQueue, ExtraTreeFruitGene.values());

            Queue<IDesign> designQueue = new LinkedList<>();
            designQueue.addAll(CarpentryManager.carpentryInterface.getSortedDesigns());

            fruitQueue.remove(ExtraTreeFruitGene.Apple);
            outputSpecies.println("<table style=\"width: 100%;\">");
            while (!speciesQueue.isEmpty()) {
                outputSpecies.println("<tr>");
                for (int i = 0; i < 4; ++i) {
                    IAlleleTreeSpecies species = speciesQueue.poll();
                    outputSpecies.println("<td>" + ((species == null) ? "" : species.getName()) + "</td>");
                }
                outputSpecies.println("</tr>");
            }

            outputSpecies.println("</table>");
            outputLogs.println("<table style=\"width: 100%;\">");

            while (!logQueue.isEmpty()) {
                outputLogs.println("<tr>");
                for (int i = 0; i < 4; ++i) {
                    ILogType.ExtraTreeLog wood5 = (ILogType.ExtraTreeLog) logQueue.poll();
                    if (wood5 == null) {
                        outputLogs.println("<td></td>");
                    } else {
                        String img = "<img alt=\"" + wood5.getName() + "\" src=\"images/logs/"
                                + wood5.toString().toLowerCase() + "Bark.png\">";
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
                    IDesignMaterial wood2 = plankQueue.poll();
                    if (wood2 == null) {
                        outputPlanks.println("<td></td>");
                    } else {
                        String img = "<img alt=\"" + wood2.getName() + "\" src=\"images/planks/" + wood2.getName()
                                + ".png\">";
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
                    ExtraTreeFruitGene wood6 = fruitQueue.poll();
                    if (wood6 == null) {
                        outputFruit.println("<td></td>");
                    } else {
                        String fruit = wood6.getNameOfFruit();
                        String img2 = "<img alt=\"" + wood6.getName() + "\" src=\"images/fruits/" + fruit + ".png\">";
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
                    IDesign wood4 = designQueue.poll();
                    if (wood4 == null) {
                        outputDesigns.println("<td></td>");
                    } else {
                        String texture =
                                wood4.getTopPattern().getPattern().toString().toLowerCase();
                        String img2 = "<img alt=\"" + texture + "\" src=\"images/pattern/" + texture + ".png\">";
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
