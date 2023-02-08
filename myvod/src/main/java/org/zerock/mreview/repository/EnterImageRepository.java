package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.EnterImage;

public interface EnterImageRepository extends JpaRepository<EnterImage, Long> {
    @Modifying
    @Query("delete  from EnterImage r where r.enter.mno = :mno")
    void deleteByMno(Long mno);
}
