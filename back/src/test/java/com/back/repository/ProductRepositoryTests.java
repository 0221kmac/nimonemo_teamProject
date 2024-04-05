package com.back.repository;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.back.domain.Product;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {
    @Autowired
    ProductRepository productRepository;

    @Test
    public void testSearch() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
        String artist = "성춘향";
        String pname = "상품";
        // productRepository.searchList(artist, pname, pageable);
        Page<Object[]> result = productRepository.searchList(artist, pname, pageable);
        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }

    @Test
    public void testInsert() {
        for (int i = 40; i < 50; i++) {
            Product product = Product.builder()
                    .pname("상품" + i)
                    .price(1000 * i)
                    .pdesc("상품설명 " + i)
                    .artist("성춘향")
                    .build();

            product.addImageString("IMG_1.jpg");
            product.addImageString("IMG_2.jpg");
            productRepository.save(product);

            log.info("-------------------");
        }
    }

    @Transactional
    @Test
    public void testRead() {
        Long pno = 1L;
        Optional<Product> result = productRepository.findById(pno);
        Product product = result.orElseThrow();

        log.info("1-----------"+product);
        log.info("2------------"+product.getImageList());
    }

    @Test
    public void testRead2() {
        Long pno = 1L;
        Optional<Product> result = productRepository.selectOne(pno);
        Product product = result.orElseThrow();

        log.info("1-----------"+product);
        log.info("2------------"+product.getImageList());

    }

    @Commit
    @Transactional
    @Test
    public void testDelte() {
        Long pno = 2L;
        productRepository.updateToDelete(pno, true);
    }

    @Test
    public void testUpdate(){// 상품의 설명이나 가격변동, 이미지 3개도 변경
        Long pno = 10L;
        Product product = productRepository.selectOne(pno).get();
        product.changeName("10번 상품");
        product.changeDesc("10번 상품 설명입니다.");
        product.changePrice(5000);
        //첨부파일 수정
        product.clearList();

        product.addImageString(UUID.randomUUID().toString() + "_" + "NEWIMG_1.jpg");
        product.addImageString(UUID.randomUUID().toString() + "_" + "NEWIMG_2.jpg");
        product.addImageString(UUID.randomUUID().toString() + "_" + "NEWIMG_3.jpg");

        productRepository.save(product);
    }

    @Test
    public void testList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
        Page<Object[]> result = productRepository.selectList(pageable);
        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }
}