package com.openclassrooms.entrevoisins.service;

import android.util.Log;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */

    @Override
    public void updateNeighbour(Neighbour neighbour) {
        Log.d("Update Detail", ""+neighbour.toString());
        Log.d("Update Detail", ""+neighbours.get(0).toString());
        int position = neighbours.indexOf(neighbour);
        Log.d("position Detail", ""+position);
        Log.d("position Detail", ""+neighbours.contains(neighbour));
        neighbours.get(position).setFavorite(!neighbour.isFavorite());
    }
}
