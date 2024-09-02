package com.nhnacademy.customer.domain;

import com.nhnacademy.customer.exception.InsufficientFundsException;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerTest {

    Customer customer;
    @BeforeEach
    void setUp(){
        customer = new Customer(1l,"NHN아카데미",100_0000);
    }

    @Order(1)
    @Test()
    @DisplayName("id < 0")
    void testConstructor1(){
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            customer = new Customer(-1l, "NHN아카데미",10_0000);
        });
    }

    @Order(2)
    @Test()
    @DisplayName("monry < 0")
    void testConstructor3(){
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            customer = new Customer(1l, "NHN아카데미",-1_0000);
        });
    }

    @Order(3)
    @Test()
    @DisplayName("name is ( empty or null ) ")
    void testConstructor2(){
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            customer = new Customer(1l, "",100_000);
        });
    }

    @Order(4)
    @Test
    void getId() {
        long actual = customer.getId();
        Assertions.assertEquals(1l, actual);
    }

    @Order(5)
    @Test
    void getName() {
        String actual = customer.getName();
        Assertions.assertEquals("NHN아카데미",actual);
    }

    @Order(6)
    @Test
    void getMoney() {
        Assertions.assertEquals(100_0000,customer.getMoney());
    }

    @Order(7)
    @Test
    @DisplayName("결제 : 100_0000 - 10_00000 = 90_00000")
    void pay1() throws InsufficientFundsException {
        customer.pay(10_0000);
        int actual = customer.getMoney();
        Assertions.assertEquals(90_0000,actual);
    }

    @Order(8)
    @Test
    @DisplayName("결제 amount < 0 ")
    void pay2() throws InsufficientFundsException {
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            customer.pay(-10_0000);
            int actual = customer.getMoney();
        });
    }

    @Order(9)
    @Test
    @DisplayName("customer money = 100만원, 200만원 결제 시도")
    void pay3(){
        Assertions.assertThrows(InsufficientFundsException.class,()->{
            customer.pay(200_0000);
        });
    }

    @Order(10)
    @Test
    @DisplayName("id 와 name, money이 일치하면 동일한 객체로 식별")
    void testEquals1() {
        Customer excepted = new Customer(1l,"NHN아카데미",100_0000);
        Assertions.assertEquals(excepted, customer);
    }

    @Order(11)
    @Test
    @DisplayName("name, money 알치, 아이디는 불일치")
    void testEquals2() {
        Customer excepted = new Customer(2l,"NHN아카데미",100_0000);
        Assertions.assertNotEquals(excepted, customer);
    }

}