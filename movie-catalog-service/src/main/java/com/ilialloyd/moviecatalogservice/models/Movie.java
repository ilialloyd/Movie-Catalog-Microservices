package com.ilialloyd.moviecatalogservice.models;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie {

    String movieId;
    String name;

}
