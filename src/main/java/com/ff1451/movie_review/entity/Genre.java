package com.ff1451.movie_review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Genres")
public class Genre {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String genreName;

    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;

    public Genre() {}

    public Genre(String genreName) {}
}
