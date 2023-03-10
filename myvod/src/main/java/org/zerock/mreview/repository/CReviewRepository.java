package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.*;
import org.zerock.mreview.repository.searchRepository.SearchCultureRepository;

import java.util.List;

public interface CReviewRepository extends JpaRepository<CReview, Long> {
    @EntityGraph(attributePaths = {"cmember"}, type = EntityGraph.EntityGraphType.FETCH)
    List<CReview> findByCulture(Culture culture);

    @Modifying
    @Query("delete from CReview mr where mr.cmember = :cmember")
    void deleteByCMember(CMember cmember);

    @Modifying
    @Query("delete  from CReview r where r.culture.mno = :mno")
    void deleteByMno(Long mno);
}
