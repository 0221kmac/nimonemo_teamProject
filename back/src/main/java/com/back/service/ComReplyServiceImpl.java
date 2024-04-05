package com.back.service;


import com.back.domain.ComReply;
import com.back.dto.CbpRequestDTO;
import com.back.dto.CbpResponseDTO;
import com.back.dto.ComReplyDTO;
import com.back.repository.ComReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2

public class ComReplyServiceImpl implements ComReplyService {
    private final ComReplyRepository comReplyRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long regComReply(ComReplyDTO comReplyDTO) {
        ComReply comReply = dtoToEntity(comReplyDTO);
        comReplyRepository.save(comReply);
        return comReply.getComReplyRno();
    }

    @Override
    public void modComReply(ComReplyDTO comReplyDTO) {
        ComReply existingComReply = comReplyRepository.findById(comReplyDTO.getComReplyRno())
                .orElseThrow(() -> new IllegalArgumentException("ComReply with rno " + comReplyDTO.getComReplyRno() + " does not exist"));

        // 수정된 내용 업데이트
        existingComReply.cbrChange(comReplyDTO.getComReplyContent());
        // 나머지 필드 업데이트 가능

        comReplyRepository.save(existingComReply);
    }

    @Override
    public void delComReply(Long comReplyRno) {
        comReplyRepository.deleteById(comReplyRno);
    }


    @Override
    @Transactional
    public ComReplyDTO getComReplyDTOById(Long comReplyRno) {
        // 댓글 ID를 기반으로 댓글 조회 로직 작성
        Optional<ComReply> comReplyOptional = comReplyRepository.findById(comReplyRno);
        ComReply comReply = comReplyOptional.orElseThrow(() -> new IllegalArgumentException("ComReply with rno " + comReplyRno + " does not exist"));

        // 댓글 엔티티를 DTO로 변환
        return entityToDTO(comReply);
    }

//    @Override
//    public CbpResponseDTO<ComReplyDTO> crList(Long comBoardBno, CbpRequestDTO cbpRequestDTO) {
//        // 요청된 페이지와 페이지 크기를 기반으로 페이지네이션하여 댓글 목록을 조회
//        Pageable pageable = cbpRequestDTO.getPageable();
//        Page<ComReply> comReplyPage = comReplyRepository.findAllByComBoard_ComBoardBno(comBoardBno, pageable);
//
//        // 조회된 댓글 페이지를 DTO로 변환하여 응답 객체에 담음
//        List<ComReplyDTO> comReplyDTOList = comReplyPage.getContent().stream()
//                .map(this::entityToDTO)
//                .collect(Collectors.toList());
//        return new CbpResponseDTO<>(comReplyDTOList, comReplyPage.getTotalElements(), pageable.getPageNumber(), pageable.getPageSize());
//    }
    private ComReplyDTO entityToDTO(ComReply comReply) {
        ComReplyDTO comReplyDTO = new ComReplyDTO();
        comReplyDTO.setComReplyRno(comReply.getComReplyRno());
        comReplyDTO.setComReplyContent(comReply.getComReplyContent());
        // 나머지 필드에 대한 매핑도 수행해야 합니다.
        return comReplyDTO;
    }

private ComReply dtoToEntity(ComReplyDTO comReplyDTO) {
    return modelMapper.map(comReplyDTO, ComReply.class);
}



}
