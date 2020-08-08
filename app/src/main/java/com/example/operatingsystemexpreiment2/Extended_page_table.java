package com.example.operatingsystemexpreiment2;

import java.io.Serializable;

public class Extended_page_table implements Serializable {
    int page_number;//页号
    int block_number;//内存块号
    boolean status_bit;//状态位
    boolean modify_bit;//修改位
    int external_storage_address;//置换空间块号

    public Extended_page_table() {

    }

    public Extended_page_table(int page_number, int block_number, boolean status_bit, boolean modify_bit, int external_storage_address) {
        this.page_number = page_number;
        this.block_number = block_number;
        this.status_bit = status_bit;
        this.modify_bit = modify_bit;
        this.external_storage_address = external_storage_address;
    }

    @Override
    public String toString() {
        return "{" +
                "页号=" + page_number +
                ", 块号=" + block_number +
                ", 状态位=" + status_bit +
                ", 修改位=" + modify_bit +
                ", 置换空间块号=" + external_storage_address +
                '}';
    }
}
