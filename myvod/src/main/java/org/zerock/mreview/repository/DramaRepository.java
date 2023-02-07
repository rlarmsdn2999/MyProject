package org.zerock.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Drama;

import java.util.List;

public interface DramaRepository extends JpaRepository<Drama, Long> {
    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Drama m " +
            "left outer join DramaImage mi on mi.drama = m "+
            "left outer join DReview r on r.drama = m group by m" )
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(r)" +
            " from Drama m left outer join DramaImage mi on mi.drama = m "+
            " left outer join DReview r on r.drama = m " +
            "where m.dno = :dno group by mi")
    List<Object[]> getDramaWithAll(Long dno);
}
