package com.ymj.campuscanvas.pojo.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ymj.campuscanvas.pojo.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadPostRequest {
    private Long userId;
    private String title;
    private String content;
    private List<String> imageUrls;
    private List<Long> tagIds;

    public Post toPost() {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArray = null;
        if (imageUrls != null) {
            try {
                jsonArray = objectMapper.writeValueAsString(imageUrls); // 将 List 转为 JSON 数组字符串
            } catch (JsonProcessingException e) {
                // 处理异常（如记录日志或抛出运行时异常）
                throw new RuntimeException("Failed to serialize imageUrls to JSON", e);
            }
        }
        return new Post(null, userId, title, content, jsonArray, null, 0);
    }
}
