package com.back.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comboard")
@Getter
@ToString(exclude = "imageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ComBoard {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comBoardBno;

    @Column(length = 30, nullable = false)
    private String comBoardTitle;

    @Column(length = 500, nullable = false)
    private String comBoardContent;

    @Column(length = 10, nullable = false)
    private String comBoardWriter;

    private boolean delFlag;

    public void changeDel(boolean delFlag) {
        this.delFlag = delFlag;
    }


    @ElementCollection
    @Builder.Default
    private List<ComBoardImage> imageList = new ArrayList<>();


    public void addImage(ComBoardImage image) {
        image.setBrd(this.imageList.size());
        imageList.add(image);
    }

    public void addImageString(String fileName){
        ComBoardImage comBoardImage = ComBoardImage.builder()
                .fileName(fileName)
                .build();
        addImage(comBoardImage);
    }

    @Column(name = "cb_reg_date")
    private LocalDateTime regDate;

    @PrePersist
    protected void onCreate() {
        regDate = LocalDateTime.now();
    }

    public void clearList() {
        this.imageList.clear();
    }

    public void changeTitle(String comBoardTitle) {
        this.comBoardTitle = comBoardTitle;
    }

    public void changeContent(String comBoardContent) {
        this.comBoardContent = comBoardContent;
    }
}


//    public void cbChange(String comBoardTitle, String comBoardContent) {
//        this.comBoardTitle = comBoardTitle;
//        this.comBoardContent = comBoardContent;
//    }
//
//    public void setComBoardBno(Long comBoardBno) {
//        this.comBoardBno = comBoardBno;
//    }
//
//    @Builder
//    public ComBoard(String comBoardTitle, String comBoardContent, String comBoardWriter) {
//        this.comBoardTitle = comBoardTitle;
//        this.comBoardContent = comBoardContent;
//        this.comBoardWriter = comBoardWriter;
//    }
//
//    @Column(nullable = false)
//    private LocalDateTime cbRegDate;
//
//    public void setCbRegDate(LocalDateTime cbRegDate) {
//        this.cbRegDate = cbRegDate;
//    }
//
//    @PrePersist
//    public void prePersist() {
//        this.cbRegDate = LocalDateTime.now();
//    }
//
//    public String getFormattedCbRegDate() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        return cbRegDate.format(formatter);
//    }
//
//
//    public void setComBoardContent(String contentWithImagePath) {
//    }
//}
