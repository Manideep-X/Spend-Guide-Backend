package com.manideep.spendguide.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manideep.spendguide.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    // SQL Query: select * from table_category where profile_id = ?1;
    List<CategoryEntity> findByProfileId(Long profileId);

    // SQL Query: select * from table_category where id = ?1 and profile_id = ?2;
    Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profileId);

    // SQL Query: select * from table_category where type = ?1 and profile_id = ?2;
    List<CategoryEntity> findByTypeAndProfileId(String type, Long profileId);

    // SQL Query: select case when count(*) > 0 then true else false end from table_category where name = ?1 and profile_id = ?2;
    Boolean existsByNameAndProfileId(String name, Long profileId);

}