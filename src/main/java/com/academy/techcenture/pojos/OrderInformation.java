package com.academy.techcenture.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderInformation {
    @JsonProperty("id")
    private String id;
    @JsonProperty("bookId")
    private Integer bookId;
    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("timestamp")
    private Long timestamp;
}
