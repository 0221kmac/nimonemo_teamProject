package com.back.service;

import com.back.dto.ComBoardDTO;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2

public class ComBoardServiceTests {

    @Autowired
    private ComBoardService comBoardService;

    @Test
    public void testRegComBoard() {
        log.info(comBoardService.getClass().getName());
        ComBoardDTO comBoardDTO = ComBoardDTO.builder()
                .comBoardTitle("regTest title")
                .comBoardContent("regTest content")
                .comBoardWriter("reTest writer")
                .build();

        Long comBoardBno = comBoardService.regComBoard(comBoardDTO);
        log.info("cbno: " + comBoardBno);
    }

    @Test
    public void testModComBoard() {

        ComBoardDTO comBoardDTO = ComBoardDTO.builder()
                .comBoardBno(101L)
                .comBoardTitle("update------title05")
                .comBoardContent("update-------content05")
                .build();

        comBoardService.modComBoard(comBoardDTO);

    }

}