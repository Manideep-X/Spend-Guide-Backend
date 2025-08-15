package com.manideep.spendguide.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.manideep.spendguide.entity.ExpenseEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    // Query: select * from table_expense where profile_id = ?1 order by date desc;
    List<ExpenseEntity> findByProfileEntity_IdOrderByDateDesc(Long profileId);

    // Query: select * from table_expense where profile_id = ?1 order by date desc limit 5;
    List<ExpenseEntity> findTop5ByProfileEntity_IdOrderByDateDesc(Long profileId);

    // This will calculate the total expense of a user
    @Query("SELECT SUM(ex.amount) FROM ExpenseEntity ex WHERE ex.profileEntity.id = :profile_id")
    BigDecimal findTotalAmountByProfileEntity_Id(@Param("profile_id") Long profileId);

    /* Query:
        select * from table_expense where profile_id = ?1 and date between ?2 and ?3 and lower(?4) like lower('%?4%');
    */
    List<ExpenseEntity> findByProfileEntity_IdAndDateBetweenAndNameContainingIgnoreCase(
        Long profileId, LocalDate startDate, LocalDate endDate, String like, Sort sort
    );

    // Query: select * from table_expense where profile_id = ?1 and date between ?2 and ?3 and lower(?4);
    List<ExpenseEntity> findByProfileEntity_IdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);

    // Query: select * from table_expense where profile_id = ?1 and date = ?2;
    List<ExpenseEntity> findByProfileEntity_IdAndDate(Long profileId, LocalDate date);

}
