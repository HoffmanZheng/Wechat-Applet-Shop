package com.github.NervousOrange.wxshop.entity;

public class PagedResponse<T> {

    private Integer pageNum;

    private Integer pageSize;

    private Integer totalPage;

    private T data;

    public PagedResponse() {
    }

    public PagedResponse(Integer pageNum, Integer pageSize, Integer totalPage, T data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.data = data;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
