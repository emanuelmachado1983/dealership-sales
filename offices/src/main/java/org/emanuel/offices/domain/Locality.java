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
public class Locality {

    private Long id;

    private String name;

    private LocalDateTime deletedAt;

    private Long provinceId;

}
