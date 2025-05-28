package com.ymj.campuscanvas.pojo;

import com.ymj.campuscanvas.annotation.Sortable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
    private Long id;
    // 粉丝
    private Long followerId;
    // 被关注者
    private Long followeeId;
    @Sortable
    private LocalDateTime createdTime;
}
