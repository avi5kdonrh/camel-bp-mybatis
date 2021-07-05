package org.example;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ExampleMapper {
    @Select("SELECT * FROM Examples WHERE id = #{id}")
    Example getExample(@Param("id") String id);
}
