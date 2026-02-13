package org.emanuel.sales.domain.office;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Office {
    private Long id;
    private Long idCountry;
    private Long idProvince;
    private Long idLocality;
    private String address;
    private String name;
    private LocalDateTime openingDate;
    private TypeOffice typeOffice;


}
