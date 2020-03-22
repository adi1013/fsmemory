package com.adi.fsmemory.uim.constant;

/**
 * 用户申请加入群组时的状态
 */
public enum FunUserJoinGroupEnum {
    JOIN_APPLY(0),  //申请加入
    JOIN_PASS(1),   //已加入
    JOIN_INVITE(2); //邀请加入


    private Integer join;

    FunUserJoinGroupEnum(Integer join) {
        this.join = join;
    }

    public Integer getJoin() {
        return join;
    }

    public void setJoin(Integer join) {
        this.join = join;
    }
}
