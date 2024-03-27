package com.bobo.mp.repository.impl;

import com.mashibing.internalcommon.domain.pojo.DynaAmsOrgprovisions;
import com.bobo.mp.repository.ReactiveItemRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: kavanLi-R7000
 * @create: 2024-01-09 10:34
 * To change this template use File | Settings | File and Code Templates.
 */

@Service
public class ReactiveItemRepositoryImpl implements ReactiveItemRepository {
    @Override
    public <S extends DynaAmsOrgprovisions> Mono <S> save(S entity) {
        return null;
    }

    @Override
    public <S extends DynaAmsOrgprovisions> Flux <S> saveAll(Iterable <S> entities) {
        return null;
    }

    @Override
    public <S extends DynaAmsOrgprovisions> Flux <S> saveAll(Publisher <S> entityStream) {
        return null;
    }

    @Override
    public Mono <DynaAmsOrgprovisions> findById(String s) {
        return null;
    }

    @Override
    public Mono <DynaAmsOrgprovisions> findById(Publisher <String> id) {
        return null;
    }

    @Override
    public Mono <Boolean> existsById(String s) {
        return null;
    }

    @Override
    public Mono <Boolean> existsById(Publisher <String> id) {
        return null;
    }

    @Override
    public Flux <DynaAmsOrgprovisions> findAll() {
        return null;
    }

    @Override
    public Flux <DynaAmsOrgprovisions> findAllById(Iterable <String> strings) {
        return null;
    }

    @Override
    public Flux <DynaAmsOrgprovisions> findAllById(Publisher <String> idStream) {
        return null;
    }

    @Override
    public Mono <Long> count() {
        return null;
    }

    @Override
    public Mono <Void> deleteById(String s) {
        return null;
    }

    @Override
    public Mono <Void> deleteById(Publisher <String> id) {
        return null;
    }

    @Override
    public Mono <Void> delete(DynaAmsOrgprovisions entity) {
        return null;
    }

    @Override
    public Mono <Void> deleteAllById(Iterable <? extends String> strings) {
        return null;
    }

    @Override
    public Mono <Void> deleteAll(Iterable <? extends DynaAmsOrgprovisions> entities) {
        return null;
    }

    @Override
    public Mono <Void> deleteAll(Publisher <? extends DynaAmsOrgprovisions> entityStream) {
        return null;
    }

    @Override
    public Mono <Void> deleteAll() {
        return null;
    }

    /* fields -------------------------------------------------------------- */


    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */


    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
