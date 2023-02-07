package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mreview.entity.CMember;

public interface CMemberRepository extends JpaRepository<CMember, Long> {
}
