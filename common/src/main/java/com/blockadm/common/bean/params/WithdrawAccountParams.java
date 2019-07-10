package com.blockadm.common.bean.params;

/**
 * Created by hp on 2019/2/20.
 */

public class WithdrawAccountParams {


    /*
    *
    * {
  "accountNumber": "1",
  "name": "1",
  "typeId": 0
}
    * */
    private String accountNumber;
    private String name;
    private String typeId;
    private String withdrawUrl;

    public String getWithdrawUrl() {
        return withdrawUrl;
    }

    public void setWithdrawUrl(String withdrawUrl) {
        this.withdrawUrl = withdrawUrl;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
