package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonStatisticsDTO {

    private Long revenue;

    private String personName;

    private Long personId;

}
