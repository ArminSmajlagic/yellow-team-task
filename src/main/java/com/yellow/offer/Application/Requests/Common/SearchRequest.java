package com.yellow.offer.Application.Requests.Common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest implements GetAllRequest, Serializable {
    private Integer size;
    private Integer page;
}
