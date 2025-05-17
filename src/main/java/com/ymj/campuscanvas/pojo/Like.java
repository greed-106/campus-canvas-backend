package com.ymj.campuscanvas.pojo;

import com.ymj.campuscanvas.annotation.Sortable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private Long id;
    private Long userId;
    private Long targetId;
    private LikeType type;
    @Sortable
    private LocalDateTime createdTime;

    public enum LikeType {
        POST, COMMENT;

        public static boolean isValidType(String type) {
            if(type == null || type.isEmpty()) {
                return false;
            }

            for (LikeType likeType : LikeType.values()) {
                if (likeType.name().equalsIgnoreCase(type)) {
                    return true;
                }
            }

            return false;
        }
    }
}
