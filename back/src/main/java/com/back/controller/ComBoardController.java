package com.back.controller;

import com.back.dto.*;
import com.back.service.ComBoardService;
import com.back.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/api/comBoard")

public class ComBoardController {

    private final ComBoardService comBoardService;
    private final CustomFileUtil fileUtil;


    @PostMapping("/register")
    public Map<String, Long> regComBoard(ComBoardDTO comBoardDTO) {
        log.info("register: " + comBoardDTO);

        List<MultipartFile> files = comBoardDTO.getFiles();

        List<String> uploadFileNames = fileUtil.saveFiles(files);
        comBoardDTO.setUploadFileNames(uploadFileNames);

        log.info(uploadFileNames);

        //서비스 호출
        Long comBoardBno = comBoardService.regComBoard(comBoardDTO);

        try {  // front의 fetching 진행모달창 1초동안 보이기
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Map.of("result", comBoardBno);
    }


    @GetMapping("/{comBoardBno}")
    public ComBoardDTO getComBoard(@PathVariable(name = "comBoardBno") Long comBoardBno) {
        return comBoardService.getComBoard(comBoardBno);
    }
//
//    @GetMapping("/{comBoardBno}")
//    public ComBoardDTO getComBoard(@PathVariable(name = "ComBoardBno") Long comBoardBno) {
//        ComBoardDTO comBoardDTO = comBoardService.getComBoard(comBoardBno);
//        List<String> uploadFileNames = getUploadFileNamesForComBoard(comBoardBno); // 게시글에 관련된 파일명 가져오기
//        comBoardDTO.setUploadFileNames(uploadFileNames); // 파일명을 DTO에 설정
//        return comBoardDTO;
//    }
//
//    private List<String> getUploadFileNamesForComBoard(Long comBoardBno) {
//        // 파일명을 가져오는 로직을 작성하거나 서비스나 유틸리티 클래스를 호출하여 처리합니다.
//        // 예를 들어, ComBoardService를 사용하여 파일명을 가져오는 방법은 다음과 같을 수 있습니다.
//        return comBoardService.getUploadFileNames(comBoardBno);
//    }


    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {
        return fileUtil.getFile(fileName);
    }



    @GetMapping("/list")
    public CbpResponseDTO<ComBoardDTO> getCbList(CbpRequestDTO cbpRequestDTO) {
        log.info("list----------------------" + cbpRequestDTO);

        log.info("----------d" + comBoardService.getCbList(cbpRequestDTO));
        return comBoardService.getCbList(cbpRequestDTO);
    }


    @PutMapping("/{comBoardBno}")
    public Map<String, String> modComBoard(@PathVariable(name = "comBoardBno") Long comBoardBno, ComBoardDTO comBoardDTO) {
        comBoardDTO.setComBoardBno(comBoardBno);
        ComBoardDTO oldComBoardDTO = comBoardService.getComBoard(comBoardBno);
        //기존의 파일들 (데이터베이스에 존재하는 파일들 - 수정 과정에서 삭제되었을 수 있음)
        List<String> oldFileNames = oldComBoardDTO.getUploadFileNames();
        //새로 업로드 해야 하는 파일들
        List<MultipartFile> files = comBoardDTO.getFiles();
        //새로 업로드되어서 만들어진 파일 이름들
        List<String> currentUploadFileNames = fileUtil.saveFiles(files);
        //화면에서 변화 없이 계속 유지된 파일들
        List<String> uploadedFileNames = comBoardDTO.getUploadFileNames();

        //유지되는 파일들  + 새로 업로드된 파일 이름들이 저장해야 하는 파일 목록이 됨
        if (currentUploadFileNames != null && currentUploadFileNames.size() > 0) {
            uploadedFileNames.addAll(currentUploadFileNames);
        }

        //수정 작업
        comBoardService.modComBoard(comBoardDTO);

        if (oldFileNames != null && oldFileNames.size() > 0) {
            //지워야 하는 파일 목록 찾기
            //예전 파일들 중에서 지워져야 하는 파일이름들
            List<String> removeFiles = oldFileNames
                    .stream()
                    .filter(fileName -> uploadedFileNames.indexOf(fileName) == -1).collect(Collectors.toList());
            //실제 파일 삭제
            fileUtil.deleteFiles(removeFiles);
        }
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{comBoardBno}")
    public Map<String, String> delComBoard(@PathVariable("comBoardBno") Long comBoardBno) {
        //삭제해야할 파일들 알아내기
        List<String> oldFileNames = comBoardService.getComBoard(comBoardBno).getUploadFileNames();
        comBoardService.delComBoard(comBoardBno);
        fileUtil.deleteFiles(oldFileNames);

        return Map.of("RESULT", "SUCCESS");
    }
}



