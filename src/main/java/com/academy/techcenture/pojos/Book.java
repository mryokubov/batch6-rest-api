package com.academy.techcenture.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("author")
    private String author;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("type")
    private String type;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("current-stock")
    private Integer currentStock;
    @JsonProperty("available")
    private Boolean available;

}
