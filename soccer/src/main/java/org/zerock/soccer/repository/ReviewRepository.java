package org.zerock.soccer.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.soccer.entity.Player;
import org.zerock.soccer.entity.PlayerMember;
import org.zerock.soccer.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @EntityGraph(attributePaths = {"playerMember"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByPlayer(Player player);

    @Modifying
    @Query("delete from Review mr where mr.playerMember = :playerMember")
    void deleteByPlayerMember(PlayerMember playerMember);

    @Modifying
    @Query("delete  from Review r where r.player.pno = :pno")
    void deleteByPno(Long pno);
}
