package com.yellow.offer.Application.Requests.Offer.Events;


import com.yellow.offer.Application.Requests.Common.Request;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class FindByStartsAtRequest implements Request {
    @NotNull(message = "startsAt is required to find event by date")
    @NotEmpty(message = "startsAt is required to find event by date")
    private String startsAt;

}
