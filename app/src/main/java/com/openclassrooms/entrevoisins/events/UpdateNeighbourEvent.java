package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class UpdateNeighbourEvent {
    /**
     * Neighbour to update
     */
    public Neighbour neighbour;

    /**
     * Constructor.
     * @param neighbour
     */
    public UpdateNeighbourEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}
