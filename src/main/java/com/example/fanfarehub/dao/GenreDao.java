package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Genre;
import java.util.Optional;
import java.util.List;

public interface GenreDao {
    Optional<Genre> findById(Integer id);
    List<Genre> findAll();
}