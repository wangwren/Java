package interfaces.interfaceprocessor;

import interfaces.filters.Filter;
import interfaces.filters.Waveform;

public class LowPass extends Filter {

	double cutoff;
	public LowPass(double cutoff) {
		this.cutoff = cutoff;
	}
	public Waveform pocess(Waveform input) {
		return input;
	}
}
