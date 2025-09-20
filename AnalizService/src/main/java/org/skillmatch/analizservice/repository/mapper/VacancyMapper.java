package org.skillmatch.analizservice.repository.mapper;

import org.apache.ibatis.annotations.*;
import org.skillmatch.analizservice.model.VacancyEntity;

import java.util.List;

@Mapper
public interface VacancyMapper {

    @Insert("INSERT INTO vacancy_entity (name, description, user_id) " +
            "VALUES (#{name}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertVacancy(VacancyEntity vacancy);

    @Select("SELECT id, name, description, user_id FROM vacancy_entity WHERE id = #{id}")
    VacancyEntity selectVacancyById(Long id);

    @Select("SELECT id, name, description, user_id FROM vacancy_entity")
    List<VacancyEntity> selectAllVacancies();

    @Update("UPDATE vacancy_entity SET name = #{name}, description = #{description}, user_id = #{userId} " +
            "WHERE id = #{id}")
    void updateVacancy(VacancyEntity vacancy);

    @Delete("DELETE FROM vacancy_entity WHERE id = #{id}")
    void deleteVacancy(Long id);
}
