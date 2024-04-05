package com.back.repository;

import com.back.domain.ComBoard;
import com.back.domain.Product;
import com.back.dto.ComBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ComBoardRepository extends JpaRepository<ComBoard, Long> {
    @EntityGraph(attributePaths = "imageList")
    @Query("SELECT b FROM ComBoard b WHERE b.comBoardBno = :comBoardBno")
    Optional<ComBoard> selectOne(@Param("comBoardBno") Long comBoardBno);

    @Modifying
    @Query("UPDATE ComBoard b SET b.delFlag = :flag WHERE b.comBoardBno = :comBoardBno")
    void updateToDelete(@Param("comBoardBno") Long comBoardBno, @Param("flag") boolean flag);


    // 이미지가 포함된 목록 처리
    @Query("SELECT b, bi FROM ComBoard b LEFT JOIN b.imageList bi ON bi.brd = 0 WHERE b.delFlag = false OR bi IS NULL")
    Page<Object[]> selectList(Pageable pageable);

}

