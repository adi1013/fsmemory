package com.adi.fsmemory.uim.constant;

public enum FunUsefulFlag {
    USE(1), UN_USE(0);

    private Integer use;

    FunUsefulFlag(Integer use) {
        this.use = use;
    }

    public Integer getUse() {
        return use;
    }

    public void setUse(Integer use) {
        this.use = use;
    }
}
