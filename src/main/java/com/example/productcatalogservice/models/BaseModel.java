package com.example.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseModel {
    private Long id;
    private Date created_at;
    private Date updated_at;
    private State state;
}
