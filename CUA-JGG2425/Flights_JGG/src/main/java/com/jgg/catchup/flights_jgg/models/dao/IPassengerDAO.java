package com.jgg.catchup.flights_jgg.models.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPassengerDAO extends CrudRepository<Passenger, Long> {
}
