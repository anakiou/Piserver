package com.anakiou.repository;

import com.anakiou.domain.Output;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OutputRepository extends JpaRepository<Output, Long> {

    Optional<Output> findOneByName(String outputName);

    Optional<Output> findOneByOutputNumber(int outputNo);

    @Query("SELECT name FROM Output")
    List<String> findNames();

    @Query("SELECT name FROM Output WHERE outputNumber = :no")
    String findNameByNo(@Param("no") int no);
}
