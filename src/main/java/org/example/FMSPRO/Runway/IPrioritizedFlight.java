package org.example.FMSPRO.Runway;

import org.example.FMSPRO.IFlight;
import org.example.FMSPRO.Priority;

public interface IPrioritizedFlight extends IFlight {
    Priority getPriority();
}
