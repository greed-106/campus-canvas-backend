package com.ymj.campuscanvas.pojo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ymj.campuscanvas.annotation.Sortable;
import com.ymj.campuscanvas.pojo.DTO.GetPostResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Long id;
    private Long userId;
    private String title;
    // 文本内容
    private String content;
    // 图片地址，使用json数组存放多个图片地址
    private String imageUrls;
    @Sortable
    private LocalDateTime createdTime;
    @Sortable
    private int viewCount = 0;

    public GetPostResponse toGetPostResponse() {
        GetPostResponse response = new GetPostResponse();
        response.setId(this.id);
        response.setUserId(this.userId);
        response.setTitle(this.title);
        response.setContent(this.content);
        response.setCreatedTime(this.createdTime);
        response.setViewCount(this.viewCount);

        // 反序列化 imageUrls 字符串为 List<String>
        ObjectMapper objectMapper = new ObjectMapper();
        if (imageUrls != null && !imageUrls.isEmpty()) {
            try {
                List<String> urls = objectMapper.readValue(imageUrls, new TypeReference<List<String>>() {});
                response.setImageUrls(urls);
            } catch (IOException e) {
                // 可以记录日志或抛出运行时异常
                throw new RuntimeException("Failed to deserialize imageUrls from JSON", e);
            }
        } else {
            response.setImageUrls(Collections.emptyList());
        }

        return response;
    }
}
