package org.zerock.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Culture;
import org.zerock.mreview.repository.searchRepository.SearchCultureRepository;

import java.util.List;

public interface CultureRepository extends JpaRepository<Culture, Long>, SearchCultureRepository{
    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Culture m " +
            "left outer join CultureImage mi on mi.culture = m "+
            "left outer join CReview r on r.culture = m group by m" )
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(r)" +
            " from Culture m left outer join CultureImage mi on mi.culture = m "+
            " left outer join CReview r on r.culture = m " +
            "where m.mno = :mno group by mi")
    List<Object[]> getCultureWithAll(Long mno);
}
