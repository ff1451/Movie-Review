package com.ff1451.movie_review.service;

import com.ff1451.movie_review.dto.movie.*;
import com.ff1451.movie_review.entity.Director;
import com.ff1451.movie_review.entity.Genre;
import com.ff1451.movie_review.entity.Movie;
import com.ff1451.movie_review.exception.CustomException;
import com.ff1451.movie_review.exception.ErrorCode;
import com.ff1451.movie_review.repository.DirectorRepository;
import com.ff1451.movie_review.repository.GenreRepository;
import com.ff1451.movie_review.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieService {
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;

    @Value("${kobis.api.key}")
    private String apiKey;

    @Value("{kmdb.api.key}")
    private String kmdbApiKey;


    private static final String KOBIS_URL = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
    private static final String KMDB_URL = "https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
    private static final String KOBIS_MOVIE_LIST_URL = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";


    @Transactional
    public void updateAllMovies() {
        int curPage = 1;
        boolean hasMore = true;

        while (hasMore) {
            MovieListApiResponse response = getMovieListFromApi(curPage);
            List<String> movieCodes = response.movieListResult().movieList().stream()
                .map(MovieListApiResponse.Movie::movieCd)
                .toList();

            for (String movieCode : movieCodes) {
                if (movieRepository.findByMovieCode(movieCode).isEmpty()) {
                    MovieDetailInfoApiResponse movieDetailResponse = getMovieDetailFromApi(movieCode);
                    Movie movie = fromApiResponse(movieDetailResponse);
                    movieRepository.save(movie);
                }
            }

            curPage++;
            //hasMore = !response.movieListResult().movieList().isEmpty();
            if (curPage >= 2) {
                hasMore = false;
            } else {
                hasMore = !response.movieListResult().movieList().isEmpty();
            }
        }
    }


    public MovieListApiResponse getMovieListFromApi(int curPage) {
        String url = KOBIS_MOVIE_LIST_URL + "?key=" + apiKey + "&curPage=" + curPage;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, MovieListApiResponse.class);
    }

    public MovieDetailInfoApiResponse getMovieDetailFromApi(String movieCode){
        String url = KOBIS_URL + "?key=" + apiKey + "&movieCd=" + movieCode;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, MovieDetailInfoApiResponse.class);
    }

    private Movie fromApiResponse(MovieDetailInfoApiResponse response) {
        MovieDetailInfoApiResponse.MovieInfo movieInfo = response.movieInfoResult().movieInfo();

        String cast = movieInfo.actors().stream()
            .map(actor -> actor.peopleNm() + "(" + actor.cast() + ")")
            .collect(Collectors.joining(", "));
        if(cast.isEmpty()){
            cast = "N/A";
        }

        String country = movieInfo.nations().stream()
            .map(MovieDetailInfoApiResponse.Nation::nationNm)
            .collect(Collectors.joining(", "));
        if (country.isEmpty()) {
            country = "N/A";
        }

        String viewingAge = movieInfo.audits().stream()
            .map(MovieDetailInfoApiResponse.Audit::watchGradeNm)
            .findFirst()
            .orElse("N/A");

        Movie movie = new Movie(
            movieInfo.movieCd() != null ? movieInfo.movieCd() : "N/A",
            movieInfo.movieNm() != null ? movieInfo.movieNm() : "N/A",
            movieInfo.prdtYear() != null ? Integer.parseInt(movieInfo.prdtYear()) : 0,
            viewingAge,
            cast,
            country,
            movieInfo.showTm() != null ? movieInfo.showTm() : "N/A"
        );

        List<Genre> genres = movieInfo.genres().stream()
            .map(genre -> genreRepository.findByGenreName(genre.genreNm())
                .orElseGet(() -> genreRepository.save(new Genre(genre.genreNm() != null ? genre.genreNm() : "N/A"))))
            .collect(Collectors.toList());

        if (genres.isEmpty()) {
            Genre defaultGenre = genreRepository.findByGenreName("N/A")
                .orElseGet(() -> genreRepository.save(new Genre("N/A")));
            genres.add(defaultGenre);
        }
        movie.setGenres(genres);

        if (!movieInfo.directors().isEmpty()) {
            MovieDetailInfoApiResponse.Director directorInfo = movieInfo.directors().get(0);
            Director director = directorRepository.findByName(directorInfo.peopleNm())
                .orElseGet(() -> directorRepository.save(new Director(directorInfo.peopleNm())));
            movie.setDirector(director);
        } else{
            Director defaultDirector = directorRepository.findByName("Unknown Director")
                .orElseGet(() -> {
                    Director newDirector = new Director("Unknown Director");
                    return directorRepository.save(newDirector);
                });
            movie.setDirector(defaultDirector);
        }
        return movie;
    }

    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
            .orElseThrow(()->new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        return MovieResponse.from(movie);
    }
    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    public List<MovieResponse> getMovieByTitle(String title) {
        List<Movie> movies = movieRepository.findByTitle(title)
            .orElseThrow(()-> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    public List<MovieResponse> getMovieByGenre(String genre) {
        List<Movie> movies = movieRepository.findByGenre(genre)
            .orElseThrow(()-> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    public List<MovieResponse> getMovieByDirector(String director) {
        List<Movie> movies = movieRepository.findByDirector(director)
            .orElseThrow(()-> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    public List<MovieResponse> getMovieByCast(String castName) {
        List<Movie> movies = movieRepository.findByCast(castName)
            .orElseThrow(()-> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    public List<MovieResponse> getMovieByRatingRange(Float minRating, Float maxRating) {
        List<Movie> movies = movieRepository.findByRatingRange(minRating, maxRating)
            .orElseThrow(()-> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    public List<MovieResponse> getMovieByRating(Float rating) {
        List<Movie> movies = movieRepository.findByRating(rating)
            .orElseThrow(()-> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    @Transactional
    public MovieResponse insertMovie(MovieRequest request) {
        Movie movie = new Movie(
            request.title(),
            request.synopsis(),
            request.releaseYear(),
            request.viewingAge(),
            request.language(),
            request.cast(),
            request.country(),
            request.movieTime()
        );

        Director director = directorRepository.findByName(request.directorName())
            .orElseGet(()->{
                Director newDirector = new Director(request.directorName());
                return directorRepository.save(newDirector);
            });
        movie.setDirector(director);

        List<Genre> genres = request.genreName().stream()
            .map(name -> genreRepository.findByGenreName(name)
                .orElseGet(() -> {
                    Genre newGenre = new Genre(name);
                    return genreRepository.save(newGenre);
                }))
            .collect(Collectors.toList());
        movie.setGenres(genres);

        Movie savedMovie = movieRepository.save(movie);
        return MovieResponse.from(savedMovie);
    }

    @Transactional
    public MovieResponse updateMovie(Long id, MovieUpdateRequest request) {
        Movie movie = movieRepository.findById(id)
            .orElseThrow(()->new CustomException(ErrorCode.MOVIE_NOT_FOUND));

        request.title().ifPresent(movie::setTitle);
        request.synopsis().ifPresent(movie::setSynopsis);
        request.releaseYear().ifPresent(movie::setReleaseYear);
        request.viewingAge().ifPresent(movie::setViewingAge);
        request.language().ifPresent(movie::setLanguage);
        request.cast().ifPresent(movie::setCast);
        request.country().ifPresent(movie::setCountry);
        request.movieTime().ifPresent(movie::setMovieTime);

        if (request.directorName().isPresent()) {
            Director director = directorRepository.findByName(request.directorName().get())
                .orElseGet(() -> {
                    Director newDirector = new Director(request.directorName().get());
                    return directorRepository.save(newDirector);
                });
            movie.setDirector(director);
        }

        if (request.genreName().isPresent()) {
            List<Genre> genres = request.genreName().get().stream()
                .map(name -> genreRepository.findByGenreName(name)
                    .orElseGet(() -> {
                        Genre newGenre = new Genre();
                        newGenre.setGenreName(name);
                        return genreRepository.save(newGenre);
                    }))
                .toList();
            movie.setGenres(genres);
        }

        Movie savedMovie = movieRepository.save(movie);
        return MovieResponse.from(savedMovie);
    }

    @Transactional
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
