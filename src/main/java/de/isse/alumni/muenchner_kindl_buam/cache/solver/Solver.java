package de.isse.alumni.muenchner_kindl_buam.cache.solver;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Allocation;
import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;

public interface Solver {
	Allocation solve(Input input);
}
