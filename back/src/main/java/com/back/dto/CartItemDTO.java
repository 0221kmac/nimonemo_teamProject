package com.back.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private String email;
    private Long pno;
    private int qty; // 수량
    private Long cino;
}