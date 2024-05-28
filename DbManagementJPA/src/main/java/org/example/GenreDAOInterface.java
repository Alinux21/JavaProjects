package org.example;

import org.example.JPA.JPAentities.BaseEntity;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface GenreDAOInterface <T extends BaseEntity>{
    <S extends T> S save(@NonNull S entity);
    List<T> findAll();
    Optional<T> findByName(String name);
    Optional<T> findById(Integer id);

}
