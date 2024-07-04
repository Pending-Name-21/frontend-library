package com.bridge.renderHandler.repository;

import java.util.List;

/**
 * A repository interface for handling storage and retrieval of items.
 *
 * @param <T> the type of items to be handled by the repository.
 */
public interface IRepository<T> {

    /**
     * Adds an item to the repository.
     *
     * @param item the item to add.
     */
    void add(T item);

    void delete(T item);

    /**
     * Retrieves all items from the repository.
     *
     * @return a list of items from the repository.
     */
    List<T> retrieve();
}
