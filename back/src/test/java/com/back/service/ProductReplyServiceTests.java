package com.back.service;

import com.back.dto.ProductReplyDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ProductReplyServiceTests {
    @Autowired
    private ProductReplyService productReplyService;

    @Test
    public void testRegister(){
        ProductReplyDTO productReplyDTO = ProductReplyDTO.builder()
                .productReplyText("ReplyDTO Text")
                .productReplyer("replyer")
                .pno(6L)
                .build();

        log.info(productReplyService.register(productReplyDTO));
    }
}
