package com.ymj.campuscanvas.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResponse {
    private Long id;
    private Long userId;
    private String username;
    private String avatarUrl;
    private String title;
    private String content;
    private List<String> imageUrls;
    private LocalDateTime createdTime;
    private int viewCount;
}
