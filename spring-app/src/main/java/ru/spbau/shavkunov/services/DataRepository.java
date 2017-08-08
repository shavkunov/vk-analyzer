package ru.spbau.shavkunov.services;

import org.springframework.data.repository.CrudRepository;
import ru.spbau.shavkunov.primitives.Statistics;

public interface DataRepository extends CrudRepository<Statistics, String> {
}