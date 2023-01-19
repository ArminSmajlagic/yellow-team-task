package com.yellow.offer.Domain.Entities.Common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    @NotNull(message = "Field id must not be null!")
    @NotEmpty(message = "Field id must not be empty!")
    @NotBlank(message = "Field id must not be blank!")
    @Column(name = "id", nullable = false)
    @Id
    public String id;
    Date createdAt;
    Date updatedAt;

    public BaseEntity(String id) {
        this.id = id;
    }
}
