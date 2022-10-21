package com.ironhack.banksystem.account.accountTypes;


public interface PenaltyFeeInterface {

    public boolean isMinimumBalanceGreaterThanBalance();
    public void applyPenaltyFee();
}
