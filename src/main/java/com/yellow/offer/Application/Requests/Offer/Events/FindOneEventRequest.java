package com.yellow.offer.Application.Requests.Offer.Events;

import com.yellow.offer.Application.Requests.Common.FindOneRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class FindOneEventRequest extends FindOneRequest<String> implements Serializable {

}
