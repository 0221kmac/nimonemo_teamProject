package com.back.service;

import com.back.dto.CbpRequestDTO;
import com.back.dto.CbpResponseDTO;
import com.back.dto.ComReplyDTO;

public interface ComReplyService {
    Long regComReply(ComReplyDTO comReplyDTO);
    void modComReply(ComReplyDTO comReplyDTO);
    void delComReply(Long comReplyRno);
    ComReplyDTO getComReplyDTOById(Long comReplyRno);
//    CbpResponseDTO<ComReplyDTO> crList(Long comBoardBno, CbpRequestDTO cbpRequestDTO);
}
