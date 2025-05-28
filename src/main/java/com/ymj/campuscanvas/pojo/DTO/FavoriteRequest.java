package com.ymj.campuscanvas.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRequest {
    private Long userId; // 用户ID
    private Long postId; // 帖子ID
}
