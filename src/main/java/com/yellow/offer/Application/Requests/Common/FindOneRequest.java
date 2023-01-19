package com.yellow.offer.Application.Requests.Common;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class FindOneRequest<IDType> implements Request {
    @Getter
    @Setter
    @NotNull(message = "Field id is required!")
    IDType id;
}
