package binnie.genetics.config;

import net.minecraftforge.common.config.Configuration;

import binnie.core.config.ConfigFile;

@ConfigFile(filename = "/config/forestry/genetics/main.conf")
public class ConfigurationMain implements IConfigurable {

	private static final String MACHINES = "Machines";

	public static int acclimatiserEnergy = 2;

	public static int analyserEnergy = 9000;
	public static int analyserTime = 300;

	public static int genepoolEnergy = 8000;
	public static int genepoolTime = 400;

	public static int incubatorEnergy = 2;

	public static int inoculatorEnergy = 60000;
	public static int inoculatorTime = 12000;

	public static int isolatorEnergy = 192000;
	public static int isolatorTime = 4800;

	public static int polymeriserEnergy = 96000;
	public static int polymeriserTime = 2400;

	public static int sequencerTimeMultiplier = 19200;
	public static int sequencerEnergyMultiplier = 20;

	public static int splicerEnergy = 12000000;
	public static int splicerTime = 1200;

	@Override
	public void configure(Configuration config) {
		config.addCustomCategoryComment(MACHINES, "Energy and time settings for machines");

		acclimatiserEnergy = config.getInt("acclimatiserEnergy", MACHINES, acclimatiserEnergy, 0, 10, "Energy used per tick by the acclimatiser");

		analyserEnergy = config.getInt("analyserEnergy", MACHINES, analyserEnergy, 0, 50000, "Energy used per process by the analyzer");
		analyserTime = config.getInt("analyserTime", MACHINES, analyserTime, 0, 1500, "Time per process for the analyzer");

		genepoolEnergy = config.getInt("genepoolEnergy", MACHINES, genepoolEnergy, 0, 50000, "Energy used per process by the genepool");
		genepoolTime = config.getInt("genepoolTime", MACHINES, genepoolTime, 0, 1500, "Time per process for the genepool");

		incubatorEnergy = config.getInt("incubatorEnergy", MACHINES, incubatorEnergy, 0, 10, "Energy used by the incubator per tick");

		inoculatorEnergy = config.getInt("inoculatorEnergy", MACHINES, inoculatorEnergy, 0, 100000, "Energy used per gene by the inoculator");
		inoculatorTime = config.getInt("inoculatorTime", MACHINES, inoculatorTime, 0, 20000, "Time per gene for the inoculator");

		isolatorEnergy = config.getInt("isolatorEnergy", MACHINES, isolatorEnergy, 0, 400000, "Energy used per process by the isolator");
		isolatorTime = config.getInt("isolatorTime", MACHINES, isolatorTime, 0, 10000, "Time per process for the isolator");

		polymeriserEnergy = config.getInt("polymeriserEnergy", MACHINES, polymeriserEnergy, 0, 200000, "Energy used per process by the polymeriser");
		polymeriserTime = config.getInt("polymeriserTime", MACHINES, polymeriserTime, 0, 5000, "Time per process by the polymeriser");

		sequencerTimeMultiplier = config.getInt("sequencerTimeMultiplier", MACHINES, sequencerTimeMultiplier, 0, 40000, "Time multiplier for sequencer");
		sequencerEnergyMultiplier = config.getInt("sequencerEnergyMultiplier", MACHINES, sequencerEnergyMultiplier, 0, 40, "Energy multiplier for sequencer");

		splicerEnergy = config.getInt("splicerEnergy", MACHINES, splicerEnergy, 0, 30000000, "Base energy for the splicer");
		splicerTime = config.getInt("splicerTime", MACHINES, splicerTime, 0, 500, "Base time for the splicer");
	}
}
