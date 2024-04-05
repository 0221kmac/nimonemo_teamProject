package com.back.controller;

import com.back.dto.CbpRequestDTO;
import com.back.dto.CbpResponseDTO;
import com.back.dto.ComReplyDTO;
import com.back.service.ComReplyService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@Log4j2
@RequestMapping("comreply")

public class ComReplyController {

    private final ComReplyService comReplyService;

    @Autowired
    public ComReplyController(ComReplyService comReplyService) {
        this.comReplyService = comReplyService;
    }

    @PostMapping("/register")
    public Long regComReply(@Valid @RequestBody ComReplyDTO comReplyDTO) {

        return comReplyService.regComReply(comReplyDTO);
    }

    @PutMapping("/{comReplyRno}")
    public void modComReply(@PathVariable Long comReplyRno, @Valid @RequestBody ComReplyDTO comReplyDTO) {
        comReplyDTO.setComReplyRno(comReplyRno);
        comReplyService.modComReply(comReplyDTO);
    }

    @DeleteMapping("/{comReplyRno}")
    public void delComReply(@PathVariable Long comReplyRno) {
        comReplyService.delComReply(comReplyRno);
    }
//
//    @GetMapping("/list/{comBoardBno}")
//    public CbpResponseDTO<ComReplyDTO> getCrList(@PathVariable("comBoardBno") Long comBoardBno, CbpRequestDTO cbpRequestDTO) {
//        return comReplyService.crList(comBoardBno, cbpRequestDTO);
//    }
}
