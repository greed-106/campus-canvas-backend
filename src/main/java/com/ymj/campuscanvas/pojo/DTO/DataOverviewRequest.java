package com.ymj.campuscanvas.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataOverviewRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}