package com.ymj.campuscanvas.pojo;

import com.ymj.campuscanvas.annotation.Sortable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {
    private Long id;
    private Long userId;
    private Long postId;
    @Sortable
    private LocalDateTime createdTime;
}
