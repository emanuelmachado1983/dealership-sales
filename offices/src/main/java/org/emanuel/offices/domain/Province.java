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
public class Province {
    private Long id;
    private String name;
    private LocalDateTime deletedAt;
    private Country country;

    public Province(Long id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
