package com.ilialloyd.moviecatalogservice.resource;

import com.ilialloyd.moviecatalogservice.models.CatalogItem;
import com.ilialloyd.moviecatalogservice.models.Movie;
import com.ilialloyd.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;


    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {


        //get all rated movie ID's
        UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId,
                UserRating.class);

        return ratings
                .getUserRating()
                .stream()
                .map(rating -> {
                    //Rest Template way
                    //for each movie ID, call movie info service get details
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

                    //Put them all together
                    return new CatalogItem(movie.getName(), "War happens between good robots and bad robots", rating.getRating());

                })
                .collect(Collectors.toList());


        /*
                    WebClient way

                    this whole block will give me an instance of movie
                    Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8082/movies/" + rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();
 */


//        return singletonList(
//                new CatalogItem("Transformers", "War happens between good robots and bad robots", 4)
//        );
    }
}
