package org.zerock.soccer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.soccer.entity.PlayerImage;

public interface PlayerImageRepository extends JpaRepository<PlayerImage,Long> {
    @Modifying
    @Query("delete  from PlayerImage r where r.player.pno = :pno")
    void deleteByMno(Long pno);
}
