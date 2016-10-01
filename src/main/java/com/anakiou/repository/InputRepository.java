package com.anakiou.repository;


import com.anakiou.domain.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InputRepository extends JpaRepository<Input, Long> {

    Optional<Input> findOneByName(String inputName);

    Optional<Input> findOneByInputNumber(int inputNo);

    @Query("SELECT name FROM Input")
    List<String> findNames();

    @Query("SELECT name FROM Input WHERE inputNumber = :no")
    String findNameByNo(@Param("no") int no);

}
