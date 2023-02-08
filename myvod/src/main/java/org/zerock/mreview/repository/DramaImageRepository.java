package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.DramaImage;

public interface DramaImageRepository extends JpaRepository<DramaImage, Long> {
    @Modifying
    @Query("delete  from DramaImage r where r.drama.dno = :dno")
    void deleteByDno(Long dno);
}
