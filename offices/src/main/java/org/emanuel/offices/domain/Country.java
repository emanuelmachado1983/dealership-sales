package org.emanuel.offices.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Country {
    private Long id;
    private String name;
    private LocalDateTime deletedAt;

    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
