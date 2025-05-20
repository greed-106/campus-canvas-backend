package com.ymj.campuscanvas.mapper;

import com.ymj.campuscanvas.pojo.Tag;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TagMapper {
    @Select("SELECT id, name FROM tag WHERE name LIKE CONCAT('%', #{keyword}, '%')")
    List<Tag> selectTagsByKeyword(@Param("keyword") String keyword);

    // 在Service中处理插入时的重复问题，捕获异常但不处理
    @Insert("INSERT INTO tag(name) VALUES(#{name})")
    @SelectKey(
            statement = "SELECT id, created_time FROM tag WHERE id = LAST_INSERT_ID()",
            keyProperty = "id,createdTime",
            resultType = Tag.class,
            before = false
    )
    void insertTag(Tag tag);

    @Update("UPDATE tag SET view_count = view_count + #{increment} WHERE id = #{tagId}")
    void updateTagViewCount(@Param("tagId") Long tagId, @Param("increment") int increment);

    List<Long> selectExistingTagIds(@Param("tagIds") List<Long> tagIds);

    @Select("SELECT * FROM tag WHERE id = #{tagId}")
    Tag selectTagByTagId(@Param("tagId") Long tagId);
    @Select("SELECT * FROM tag WHERE name = #{tagName}")
    Tag selectTagByTagName(@Param("tagName") String tagName);
}
