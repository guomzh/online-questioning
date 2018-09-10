package com.guomzh.onlineq.dao;

import com.guomzh.onlineq.domain.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zgm
 * @date 2018/9/9 10:08
 */
@Mapper
public interface MessageDao {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by created_date desc " +
            "limit #{offset}, #{limit}"})
    List<Message> selectConversationDetail(@Param("conversationId") String conversationId,
                                           @Param("offset") int offset,
                                           @Param("limit") int limit);

    @Select({"select ", INSERT_FIELDS, " ,count(id) as id from ( select * from ", TABLE_NAME, " where " +
            " from_id=#{userId} or to_id=#{userId} order by created_date desc ) tt " +
            "group by conversation_id order by created_date desc limit #{offset}, #{limit}"})
    List<Message> selectConversationList(@Param("userId") int userId,
                                           @Param("offset") int offset,
                                           @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int selectConversationUnreadCount(@Param("userId") int userId,
                                      @Param("conversationId") String conversationId);

    @Update({"update "+ TABLE_NAME, " set has_read=1 where to_id=#{userId} and conversation_id=#{conversationId}"})
    void updateHasRead(@Param("userId") int userId,
                       @Param("conversationId")String conversationId);

}
