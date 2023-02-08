package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.*;

import java.util.List;

public interface DReviewRepository extends JpaRepository<DReview, Long> {
    @EntityGraph(attributePaths = {"dmember"}, type = EntityGraph.EntityGraphType.FETCH)
    List<DReview> findByDrama(Drama drama);

    @Modifying
    @Query("delete from DReview mr where mr.dmember = :dmember")
    void deleteByDMember(DMember dmember);

    @Modifying
    @Query("delete  from DReview r where r.drama.dno = :dno")
    void deleteByDno(Long dno);
}
