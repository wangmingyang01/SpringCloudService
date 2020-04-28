package com.shls.aop.agent.cglib;

public class AccountService {
    public void transfer() {
        System.out.println("调用dao层,完成转账主业务.");
    }
}
