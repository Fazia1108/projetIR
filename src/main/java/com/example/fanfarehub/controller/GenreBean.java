package com.example.fanfarehub.controller;

import com.example.fanfarehub.dao.GenreDao;
import com.example.fanfarehub.model.Genre;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class GenreBean {

    @Inject
    private GenreDao genreDao;

    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    @PostConstruct
    public void init() {
        this.genres = genreDao.findAll();
    }
}