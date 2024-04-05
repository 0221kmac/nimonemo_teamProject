package com.back.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ComReplyDTO {
    private Long comReplyRno;

    @NotNull
    private Long comBoardBno;

    @NotEmpty
    private String comReplyContent;

    @NotEmpty
    private String comReplyWriter;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime crRegDate;
}
