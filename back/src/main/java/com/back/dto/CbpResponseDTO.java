package com.back.dto;


import lombok.Builder;
import lombok.Data;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Data
public class CbpResponseDTO<E> {

    private List<E> dtoList;
    private List<Integer> pageNumList;
    private CbpRequestDTO cbpRequestDTO;
    private boolean prev, next;
    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll")
    public CbpResponseDTO(List<E> dtoList, CbpRequestDTO cbpRequestDTO, long totalCount) {

        this.dtoList = dtoList;
        this.cbpRequestDTO = cbpRequestDTO;
        this.totalCount = (int) totalCount;

        int end = (int) (Math.ceil(cbpRequestDTO.getPage() / 10.0)) * 10;
        int start = end - 9;
        int last = (int) (Math.ceil((totalCount / (double) cbpRequestDTO.getSize())));

        end = end > last ? last : end;
        this.prev = start > 1;
        this.next = totalCount > end * cbpRequestDTO.getSize();
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        if (prev) {
            this.prevPage = start - 1;
        }

        if (next) {
            this.nextPage = end + 1;
        }

        this.totalPage = this.pageNumList.size();
        this.current = cbpRequestDTO.getPage();
    }

}
