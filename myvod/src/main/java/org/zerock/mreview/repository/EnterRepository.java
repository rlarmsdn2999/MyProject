package org.zerock.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Enter;
import org.zerock.mreview.repository.searchRepository.SearchEnterRepository;

import java.util.List;

public interface EnterRepository extends JpaRepository<Enter, Long>, SearchEnterRepository {
    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Enter m " +
            "left outer join EnterImage mi on mi.enter = m "+
            "left outer join EReview r on r.enter = m group by m" )
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(r)" +
            " from Enter m left outer join EnterImage mi on mi.enter = m "+
            " left outer join EReview r on r.enter = m " +
            "where m.mno = :mno group by mi")
    List<Object[]> getEnterWithAll(Long mno);
}
