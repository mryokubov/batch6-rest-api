package com.academy.techcenture.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCofirmation {

    @JsonProperty("created")
    private Boolean created;
    @JsonProperty("orderId")
    private String orderId;
}
