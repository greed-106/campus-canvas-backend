package com.ymj.campuscanvas.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tag {
    private Long id;
    private String name;
    private int viewCount = 0;
    private LocalDateTime createdTime;
}