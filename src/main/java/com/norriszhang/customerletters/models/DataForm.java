package com.norriszhang.customerletters.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DataForm {
    private String name;
    private String address;
}
