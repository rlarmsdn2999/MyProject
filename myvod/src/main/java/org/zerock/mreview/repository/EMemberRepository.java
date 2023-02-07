package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mreview.entity.EMember;

public interface EMemberRepository extends JpaRepository<EMember, Long> {
}
