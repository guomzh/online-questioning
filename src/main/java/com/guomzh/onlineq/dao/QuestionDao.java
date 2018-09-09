package com.guomzh.onlineq.dao;

import com.guomzh.onlineq.domain.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface QuestionDao {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    @Select({"select ", SELECT_FIELDS, " from ",TABLE_NAME, " where id=#{id}"})
    Question selectById(int id);

    @Update({"update ",TABLE_NAME, " set comment_count=#{commentCount} where id=#{id}"})
    void updateCommentCount(@Param("commentCount")int commentCount, @Param("id") int id);

    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);

}
