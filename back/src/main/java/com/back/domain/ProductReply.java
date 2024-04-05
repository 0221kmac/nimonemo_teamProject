package com.back.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ProductReply",
        indexes = {@Index(name = "idx_reply_product_pno", columnList = "product_pno")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString     ///(exclude = "board")
public class ProductReply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prno;

    // FetchType.EAGER
    @ManyToOne(fetch = FetchType.LAZY)  // Many: Reply, One: Board
    private Product product;

    private String productReplyText;
    private String productReplyer;
    public void changeText(String text){
        this.productReplyText = text;
    }
}