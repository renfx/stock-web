package com.rfxdevelop.stockweb.dao.dynamic;


import lombok.Data;

@Data
public class PageQuery {
    private static final int MAX_PAGE_NO = 5000;
    private int pageNo;
    private int pageSize;
    private int start;
    private int end;
    private int totalCount;
    private String orderBy;
    private String orderType;
    private int maxPageNo;

    public PageQuery() {
        this.totalCount = -1;
    }

    public PageQuery(int pageNo, int pageSize) {
        this(pageNo, pageSize, MAX_PAGE_NO, (String)null, "asc");
    }

    public PageQuery(int pageNo, int pageSize, int maxPageNo) {
        this(pageNo, pageSize, maxPageNo, (String)null, "asc");
    }

    public PageQuery(int pageNo, int pageSize, int maxPageNo, String orderBy, String orderType) {
        this.totalCount = -1;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.maxPageNo = maxPageNo;
        this.orderType = orderType;
        if (pageNo == 1) {
            this.start = 0;
        } else {
            this.start = (pageNo - 1) * pageSize;
        }

        this.end = this.start + pageSize - 1;
    }

    public PageQuery(int pageNo, int pageSize, String orderBy, String orderType) {
        this.totalCount = -1;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.maxPageNo = 5000;
        this.orderType = orderType;
        if (pageNo == 1) {
            this.start = 0;
        } else {
            this.start = (pageNo - 1) * pageSize;
        }

        this.end = this.start + pageSize - 1;
    }

    public PageQuery(String orderBy, String orderType, int start, int pageSize) {
        this.totalCount = -1;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.maxPageNo = 5000;
        this.orderType = orderType;
        if (start < 0) {
            start = 0;
        }

        this.start = start;
        this.pageNo = this.start % this.pageSize == 0 ? this.start / this.pageSize : this.start / this.pageSize + 1;
        this.end = this.start + pageSize - 1;
    }

    public PageQuery(int maxPageNo, String orderBy, String orderType, int start, int pageSize) {
        this.totalCount = -1;
        this.maxPageNo = maxPageNo;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.orderType = orderType;
        if (start < 0) {
            start = 0;
        }

        this.start = start;
        this.pageNo = this.start % this.pageSize == 0 ? this.start / this.pageSize : this.start / this.pageSize + 1;
        this.end = this.start + pageSize - 1;
    }

}