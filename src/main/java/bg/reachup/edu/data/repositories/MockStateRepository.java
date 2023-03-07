package bg.reachup.edu.data.repositories;

import bg.reachup.edu.data.entities.State;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class MockStateRepository implements StateRepository{
    @Override
    public List<State> findAll() {
        return null;
    }

    @Override
    public List<State> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<State> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<State> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(State entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends State> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends State> S save(S entity) {
        return null;
    }

    @Override
    public <S extends State> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<State> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends State> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends State> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<State> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public State getOne(Long aLong) {
        return null;
    }

    @Override
    public State getById(Long aLong) {
        return null;
    }

    @Override
    public State getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends State> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends State> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends State> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends State> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends State> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends State> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends State, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
