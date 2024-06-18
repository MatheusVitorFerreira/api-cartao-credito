package com.mscreditevaluator.domain.info;

import lombok.Data;

import java.io.Serializable;


@Data
public class DataAddress implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String street;
    private String city;
    private String state;
    private String country;
}

