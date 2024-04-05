package com.back.repository;

import com.back.domain.ComReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComReplyRepository extends JpaRepository <ComReply, Long> {
    Page<ComReply> findAllByComBoard_ComBoardBno(Long comBoardBno, Pageable pageable);
}
