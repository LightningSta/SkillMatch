package org.skillmatch.analizservice.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.skillmatch.analizservice.model.Vacancy;

@Mapper
public interface AnalizyMapper{

    @Select("""
            select name from analizy.vacancys where id = #{id}
            """)
    Vacancy getVacancyById(@Param("id") Integer id);
}
