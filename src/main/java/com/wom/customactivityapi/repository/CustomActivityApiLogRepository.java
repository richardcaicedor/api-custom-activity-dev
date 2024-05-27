package com.wom.customactivityapi.repository;

import com.wom.customactivityapi.models.CustomActivityApiLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
@Repository
public class CustomActivityApiLogRepository implements MongoRepository<CustomActivityApiLog, String> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public <S extends CustomActivityApiLog> S save(S entity) {
        mongoTemplate.save(entity);
        return entity;
    }

    @Override
    public <S extends CustomActivityApiLog> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends CustomActivityApiLog> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends CustomActivityApiLog> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends CustomActivityApiLog> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends CustomActivityApiLog> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends CustomActivityApiLog> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends CustomActivityApiLog> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends CustomActivityApiLog> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends CustomActivityApiLog, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends CustomActivityApiLog> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<CustomActivityApiLog> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<CustomActivityApiLog> findAll() {
        return null;
    }

    @Override
    public List<CustomActivityApiLog> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(CustomActivityApiLog entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends CustomActivityApiLog> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<CustomActivityApiLog> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<CustomActivityApiLog> findAll(Pageable pageable) {
        return null;
    }
}
