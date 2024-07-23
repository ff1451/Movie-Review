package com.ff1451.movie_review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Movies")
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String synopsis;

    @Column(nullable = false)
    private int releaseYear;

    @Column(nullable = false)
    private String viewingAge;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String cast;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Float rating;

    @Column(nullable = false)
    private String movieTime;

    @ManyToOne
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;

    @ManyToMany
    @JoinTable(
        name = "MovieGenres",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
