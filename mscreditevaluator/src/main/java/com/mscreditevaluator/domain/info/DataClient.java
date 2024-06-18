package com.mscreditevaluator.domain.info;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class DataClient implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idClient;
    private String fullName;
    private Integer age;
    private List<DataAddress> addresses;
}
