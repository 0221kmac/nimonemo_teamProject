package com.back.repository;

import com.back.domain.ComBoard;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2

public class ComBoardRepositoryTests {
    @Autowired
    ComBoardRepository comBoardRepository;

    @Test
    public void testInsertDum() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
                    ComBoard comBoard = ComBoard.builder()
                            .comBoardTitle("cbtitle" + i)
                            .comBoardContent("cbcontent" + i)
                            .comBoardWriter("user" + (i % 10))
                            .build();

                    ComBoard result = comBoardRepository.save(comBoard);
                    log.info("CBNO: " + result.getComBoardBno());
                }
        );
    }

    @Test
    public void testSelect() {
        Long comBoardBno = 10L;
        Optional<ComBoard> result = comBoardRepository.findById(comBoardBno);
        System.out.println("===============");
        if (result.isPresent()) {
            ComBoard comBoard =result.get();
            System.out.println(comBoard);
        }

    }
    @Test
    public void testUpdate() {
        Long comBoardBno = 10L;
        Optional<ComBoard> result = comBoardRepository.findById(comBoardBno);

        ComBoard comBoard = result.orElseThrow();
        comBoard.cbChange("update title 10", "update content 10");
        comBoardRepository.save(comBoard);
        System.out.println("------------------");
    }

    @Test
    public void testDelete() {
        Long comBoardBno = 2L;
        comBoardRepository.deleteById(comBoardBno);
    }

    @Test
    public void testPaging() {
        // PageRequest.of(페이지 번호, 사이즈, Sort.Direction, 속성....)
        Pageable pageable =
                PageRequest.of(0, 10, Sort.by("comBoardBno")
                        .descending());

        Page<ComBoard> result = comBoardRepository.findAll(pageable);

        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("page number: " + result.getNumber());
        log.info("page size: " + result.getSize());

    }
}

