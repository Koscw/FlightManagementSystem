package org.example.FMSPRO.Runway;

import org.example.FMSPRO.Common.IFlight;
import org.example.FMSPRO.Storage.Priority;

public interface IPrioritizedFlight extends IFlight {
    Priority getPriority();
}
