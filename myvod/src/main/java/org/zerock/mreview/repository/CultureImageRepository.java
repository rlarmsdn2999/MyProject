package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.CultureImage;

public interface CultureImageRepository extends JpaRepository<CultureImage, Long> {
    @Modifying
    @Query("delete  from CultureImage r where r.culture.mno = :mno")
    void deleteByMno(Long mno);
}
