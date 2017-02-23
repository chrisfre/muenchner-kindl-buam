package de.isse.alumni.muenchner_kindl_buam.cache.solver;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Allocation;
import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;

/**
 * Solver that returns an allocation that keeps all videos on the data center.
 *
 * @author Adrian Rumpold (a.rumpold@gmail.com)
 */
public class NopSolver implements Solver {

	@Override
	public Allocation solve(Input input) {
		final Allocation result = new Allocation(input);

		return result;
	}

}
