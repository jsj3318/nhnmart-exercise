/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.thread.scc;

import com.nhnacademy.customer.cart.Cart;
import com.nhnacademy.customer.cart.CartItem;
import com.nhnacademy.customer.domain.Customer;
import com.nhnacademy.customer.exception.InsufficientFundsException;
import com.nhnacademy.nhnmart.product.domain.Product;
import com.nhnacademy.nhnmart.product.service.ProductService;
import com.nhnacademy.thread.util.Executable;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class SelfCheckoutRequest implements Executable {

    private final Customer customer;
    private final Cart cart;
    private final ProductService productService;

    public SelfCheckoutRequest(Customer customer, Cart cart, ProductService productService) {
        //TODO#9-2-1 customer, cart, productService null 이면 IllegalArgumentException 발생
        if(customer == null || cart == null || productService == null){
            throw new IllegalArgumentException();
        }

        //TODO#9-2-2 customer, cart, productService 초기화
        this.customer = customer;
        this.cart = cart;
        this.productService = productService;
    }

    @Override
    public void execute(){

        /*TODO#9-2-3 execute method를 구현 합니다.
           - getTotalAmountFromCart() - 결제금액을 계산하는 method
           - customer.pay() method를 이용해서 결제를 진행 합니다.
           - 결제할 총 금액이 < customer.money 이면 모든 제품을 반납 합니다. productService.returnProduct() 이용해서 구현 합니다.
        */

        try {
            customer.pay(getTotalAmountFromCart());
        } catch (InsufficientFundsException e) {
            // 결제할 금액이 잔액 초과함
            for(CartItem item : cart.getCartItems()){
                productService.returnProduct(item.getProductId(), item.getQuantity());
            }
        }

        try {
            //1초 단위로 결제를 진행 합니다.
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTotalAmountFromCart(){
        //TODO#9-2-4 결제 금액을 계산 후 반환 합니다.

        int totalPrice = 0;

        for(CartItem cartItem : cart.getCartItems()){
            totalPrice += productService.getProduct(cartItem.getProductId()).getPrice();
        }
        return totalPrice;
    }
}
