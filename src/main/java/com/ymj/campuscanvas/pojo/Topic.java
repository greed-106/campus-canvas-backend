package com.ymj.campuscanvas.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    private Long id;
    private Long postId;
    private Long tagId;
}
