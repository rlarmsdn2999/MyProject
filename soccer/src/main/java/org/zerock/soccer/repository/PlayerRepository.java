package org.zerock.soccer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.soccer.entity.Player;
import org.zerock.soccer.repository.searchRepository.SearchRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> , SearchRepository {
    @Query("select m, max(mi), AVG(coalesce(r.grade,0)), count(distinct r) from Player m " +
            "left outer join PlayerImage mi on mi.player = m " +
            "left outer join Review r on r.player = m group by m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, AVG(coalesce(r.grade,0)),count(r)" +
            " from Player m left outer join PlayerImage mi on mi.player = m "+
            " left outer join Review r on r.player = m " +
            "where m.pno = :pno group by mi")
    List<Object[]> getPlayerWithAll(Long pno);
}
