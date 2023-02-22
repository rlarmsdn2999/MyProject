package org.zerock.soccer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.soccer.entity.PlayerMember;

public interface PlayerMemberRepository extends JpaRepository<PlayerMember, Long> {
}
