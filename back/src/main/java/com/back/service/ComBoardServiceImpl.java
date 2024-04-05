package com.back.service;

import com.back.domain.ComBoard;
import com.back.domain.ComBoardImage;
import com.back.dto.*;
import com.back.repository.ComBoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Log4j2


public class ComBoardServiceImpl implements ComBoardService {

    private final ComBoardRepository comBoardRepository;

    @Override
    public CbpResponseDTO<ComBoardDTO> getCbList(CbpRequestDTO cbpRequestDTO) {
        log.info("getList..............");

        Pageable pageable = PageRequest.of(
                cbpRequestDTO.getPage() - 1,  //페이지 시작 번호가 0부터 시작하므로
                cbpRequestDTO.getSize(),
                Sort.by("comBoardBno").descending());

        Page<Object[]> result = comBoardRepository.selectList(pageable);

        log.info("rrrrrrrrrrrrrrrr" + result);


        List<ComBoardDTO> dtoList = result.get().map(arr -> {
            ComBoard comBoard = (ComBoard) arr[0];
            ComBoardImage comBoardImage = (ComBoardImage) arr[1];

            ComBoardDTO comBoardDTO = ComBoardDTO.builder()
                    .comBoardBno(comBoard.getComBoardBno())
                    .comBoardTitle(comBoard.getComBoardTitle())
                    .comBoardContent(comBoard.getComBoardContent())
                    .comBoardWriter(comBoard.getComBoardWriter())
                    .delFlag(comBoard.isDelFlag()) // 추가수정
                    .build();

            // ComBoardImage 객체가 null이 아닌 경우에만 이미지 파일명을 설정합니다.
            if (comBoardImage != null) {
                String imageStr = comBoardImage.getFileName();
                comBoardDTO.setUploadFileNames(List.of(imageStr));
            }

            return comBoardDTO;
        }).collect(Collectors.toList());


        long totalCount = result.getTotalElements();

        return CbpResponseDTO.<ComBoardDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .cbpRequestDTO(cbpRequestDTO)
                .build();
    }


    @Override
    public Long regComBoard(ComBoardDTO comBoardDTO) {
        ComBoard comBoard = dtoToEntity(comBoardDTO);
        ComBoard result = comBoardRepository.save(comBoard);
        return result.getComBoardBno();
    }

    private ComBoard dtoToEntity(ComBoardDTO comBoardDTO) {
        ComBoard comBoard = ComBoard.builder()
                .comBoardBno(comBoardDTO.getComBoardBno())
                .comBoardTitle(comBoardDTO.getComBoardTitle())
                .comBoardContent(comBoardDTO.getComBoardContent())
                .comBoardWriter(comBoardDTO.getComBoardWriter())
                .build();


        //업로드 처리가 끝난 파일들의 이름 리스트
        List<String> uploadFileNames = comBoardDTO.getUploadFileNames();

        if (uploadFileNames == null) {
            return comBoard;
        }

        uploadFileNames.stream().forEach(uploadName -> {
            comBoard.addImageString(uploadName);
        });

        return comBoard;
    }

    @Override
    public ComBoardDTO getComBoard(Long comBoardBno) {
        Optional<ComBoard> result = comBoardRepository.selectOne(comBoardBno);
        ComBoard comBoard = result.orElseThrow();
        ComBoardDTO comBoardDTO = entityToDTO(comBoard);

        return comBoardDTO;
    }


    private ComBoardDTO entityToDTO(ComBoard comBoard) {

        ComBoardDTO comBoardDTO = ComBoardDTO.builder()
                .comBoardBno(comBoard.getComBoardBno())
                .comBoardTitle(comBoard.getComBoardTitle())
                .comBoardContent(comBoard.getComBoardContent())
                .comBoardWriter(comBoard.getComBoardWriter())
                .build();

        List<ComBoardImage> imageList = comBoard.getImageList();

        if (imageList == null || imageList.size() == 0) {
            return comBoardDTO;
        }

        List<String> fileNameList = imageList.stream().map(comBoardImage ->
                comBoardImage.getFileName()).toList();

        comBoardDTO.setUploadFileNames(fileNameList);

        return comBoardDTO;
    }

    @Override
    @Transactional
    public void modComBoard(ComBoardDTO comBoardDTO) {
        // 게시글 번호로 게시글 정보를 조회합니다.
        Optional<ComBoard> optionalComBoard = comBoardRepository.findById(comBoardDTO.getComBoardBno());

        // Optional에서 게시글 정보를 가져옵니다.
        ComBoard comBoard = optionalComBoard.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        // 게시글 정보를 변경합니다.
        comBoard.changeTitle(comBoardDTO.getComBoardTitle());
        comBoard.changeContent(comBoardDTO.getComBoardContent());

        // 업로드 파일을 초기화합니다.
        comBoard.clearList();

        // 업로드 파일을 추가합니다.
        List<String> uploadFileNames = comBoardDTO.getUploadFileNames();

//        if (uploadFileNames != null && !uploadFileNames.isEmpty()) {
//            uploadFileNames.forEach(uploadName -> comBoard.addImageString(uploadName));
//        }

        if (uploadFileNames != null && uploadFileNames.size() > 0) {
            uploadFileNames.stream().forEach(uploadName -> {
                comBoard.addImageString(uploadName);
            });

            log.info(comBoard + "1111111111111111111111111111111111111111");

            // 변경된 게시글 정보를 저장합니다.
            comBoardRepository.save(comBoard);
        }
    }

    @Override
    public void delComBoard(Long comBoardBno) {
        comBoardRepository.updateToDelete(comBoardBno, true);
    }


//    @Override
//    public List<String> getUploadFileNames(Long comBoardBno) {
//        List<String> fileNames = null;
//        // 파일명을 가져오는 로직을 구현하여 fileNames에 값을 설정합니다.
//        return fileNames;
//    }
}