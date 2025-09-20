package org.skillmatch.analizservice.repository.mapper;

import org.apache.ibatis.annotations.*;
import org.skillmatch.analizservice.model.AnalizeEntity;


import java.util.List;

@Mapper
public interface AnalizeMapper {

    @Insert("INSERT INTO analize_entity (file, result, id_vacancy) " +
            "VALUES (#{file}, #{result}, #{vacancyId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertAnalize(AnalizeEntity analize);

    @Select("SELECT id, file, result, id_vacancy AS vacancyId FROM analize_entity WHERE id = #{id}")
    AnalizeEntity selectAnalizeById(Long id);

    @Select("SELECT id, file, result, id_vacancy AS vacancyId FROM analize_entity")
    List<AnalizeEntity> selectAllAnalizes();

    @Update("UPDATE analize_entity SET file = #{file}, result = #{result}, id_vacancy = #{vacancyId} " +
            "WHERE id = #{id}")
    void updateAnalize(AnalizeEntity analize);

    @Delete("DELETE FROM analize_entity WHERE id = #{id}")
    void deleteAnalize(Long id);
}