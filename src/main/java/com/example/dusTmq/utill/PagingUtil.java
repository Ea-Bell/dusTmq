package com.example.dusTmq.utill;


import lombok.Data;

@Data
public class PagingUtil {

    private long currentPage;
    private long total;
    private long offset;
    private long limit;
}
