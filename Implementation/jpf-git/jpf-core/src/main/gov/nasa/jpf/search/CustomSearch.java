package gov.nasa.jpf.search;


import gov.nasa.jpf.Config;
import gov.nasa.jpf.vm.VM;


public class CustomSearch extends DFSearch{
    public CustomSearch(Config config, VM vm){
        super(config,vm);
    }

    /**
     * state model of the search
     *    next new  -> action
     *     T    T      forward
     *     T    F      backtrack, forward
     *     F    T      backtrack, forward
     *     F    F      backtrack, forward
     *
     * end condition
     *    backtrack failed (no saved states)
     *  | property violation (currently only checked in new states)
     *  | search constraint (depth or memory or time)
     *
     * <2do> we could split the properties into forward and backtrack properties,
     * the latter ones being usable for liveness properties that are basically
     * condition accumulators for sub-paths of the state space, to be checked when
     * we backtrack to the state where they were introduced.
     */
    @Override
    public void search () {
        boolean depthLimitReached = false;
        depth = 0;
        notifySearchStarted();
        while (!done) {
            if (checkAndResetBacktrackRequest() || !isNewState() || isEndState() || isIgnoredState() || depthLimitReached ) {
                if (!backtrack()) { // backtrack not possible, done
                    break;
                }

                depthLimitReached = false;
                //depth--;
                notifyStateBacktracked();
            }

            if (forward()) {
                //depth++;
                notifyStateAdvanced();

                if (currentError != null){
                    notifyPropertyViolated();

                    if (hasPropertyTermination()) {
                        break;
                    }
                    // for search.multiple_errors we go on and treat this as a new state
                    // but hasPropertyTermination() will issue a backtrack request
                }

                if (depth >= depthLimit) {
                    depthLimitReached = true;
                    notifySearchConstraintHit("depth limit reached: " + depthLimit);
                    continue;
                }

                if (!checkStateSpaceLimit()) {
                    notifySearchConstraintHit("memory limit reached: " + minFreeMemory);
                    // can't go on, we exhausted our memory
                    break;
                }

            } else { // forward did not execute any instructions
                notifyStateProcessed();
            }
        }
        notifySearchFinished();
    }
}