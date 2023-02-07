package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.*;

import java.util.List;

public interface EReviewRepository extends JpaRepository<EReview,Long> {
    @EntityGraph(attributePaths = {"emember"}, type = EntityGraph.EntityGraphType.FETCH)
    List<EReview> findByEnter(Enter enter);

    @Modifying
    @Query("delete from EReview mr where mr.emember = :emember")
    void deleteByEMember(EMember emember);
}
