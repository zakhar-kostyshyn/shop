package com.example.shopapplication.Payload.Request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterRequest {

    List<String> brands;

    String priceRange;

}
