package com.back.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "comreply")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ComReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long comReplyRno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ComBoard comBoard;

    @Column(length = 200)
    private String comReplyContent;

    @Column(length = 10)
    private String comReplyWriter;

    @Column
    private LocalDateTime crRegDate;

    @PrePersist
    public void prePersist() {
        crRegDate = LocalDateTime.now();
    }

    public void cbrChange(String updatedCrContent) {
        this.comReplyContent = updatedCrContent;
    }


}
