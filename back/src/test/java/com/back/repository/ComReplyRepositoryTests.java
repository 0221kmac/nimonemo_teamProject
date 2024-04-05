package com.back.repository;

import com.back.domain.ComBoard;
import com.back.domain.ComReply;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2

public class ComReplyRepositoryTests {
    @Autowired
    private ComReplyRepository comReplyRepository;

    @Autowired
    private ComBoardRepository comBoardRepository;

    @Test
    public void testInsertCr() {
        Long comBoardBno = 2L;
        ComBoard comBoard = ComBoard.builder().comBoardBno(comBoardBno).build();

        IntStream.rangeClosed(1, 10).forEach(i -> {
            ComReply comReply = ComReply.builder()
                    .comBoard(comBoard)
                    .comReplyContent("reply test " + i)
                    .comReplyWriter("cruser" + i)
                    .build();
            comReplyRepository.save(comReply);
        });
    }

    @Test
    public void testModComReply() {
        Long comReplyRno = 1L;
        String updatedContent = "Updated comReply content";
        Optional<ComReply> result = comReplyRepository.findById(comReplyRno);
        ComReply comReply = result.orElseThrow(() -> new NoSuchElementException("ComReply with rno " + comReplyRno + " does not exist"));
        comReply.cbrChange(updatedContent);
        comReplyRepository.save(comReply);
    }

    @Test
    public void testDelComReply(){
        Long comReplyRno = 1L;
        Optional<ComReply> optionalComReply = comReplyRepository.findById(comReplyRno);
        assertTrue(optionalComReply.isPresent(), "댓글이 데이터베이스에 존재하지 않습니다.");
        comReplyRepository.deleteById(comReplyRno);
        optionalComReply = comReplyRepository.findById(comReplyRno);
        assertFalse(optionalComReply.isPresent(), "댓글이 삭제되지 않았습니다.");


    }
}


