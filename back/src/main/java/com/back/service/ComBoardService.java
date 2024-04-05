package com.back.service;

import com.back.domain.ComBoard;
import com.back.dto.CbpRequestDTO;
import com.back.dto.CbpResponseDTO;
import com.back.dto.ComBoardDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ComBoardService {
    Long regComBoard(ComBoardDTO comBoardDTO);

    //    ComBoardDTO getCbOne(Long comBoardBno);
    void modComBoard(ComBoardDTO comBoardDTO);

    void delComBoard(Long comBoardBno);

    CbpResponseDTO<ComBoardDTO> getCbList(CbpRequestDTO cbpRequestDTO);

    ComBoardDTO getComBoard(Long comBoardBno);

//    List<String> getUploadFileNames(Long comBoardBno);

}
