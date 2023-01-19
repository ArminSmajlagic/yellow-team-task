package com.yellow.offer.Application.Requests.Offer.Events;

import com.yellow.offer.Application.Requests.Common.GetAllRequest;
import com.yellow.offer.Application.Requests.Common.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class GetAllEventsRequest extends SearchRequest implements Serializable {
}
