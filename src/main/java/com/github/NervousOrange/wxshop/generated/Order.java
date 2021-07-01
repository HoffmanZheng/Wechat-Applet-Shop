package com.github.NervousOrange.wxshop.generated;

import java.util.Date;

public class Order {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order.id
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order.user_id
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order.total_price
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    private Long totalPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order.address
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order.express_company
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    private String expressCompany;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order.express_id
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    private String expressId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order.status
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order.created_at
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    private Date createdAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order.updated_at
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    private Date updatedAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order.id
     *
     * @return the value of tb_order.id
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order.id
     *
     * @param id the value for tb_order.id
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order.user_id
     *
     * @return the value of tb_order.user_id
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order.user_id
     *
     * @param userId the value for tb_order.user_id
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order.total_price
     *
     * @return the value of tb_order.total_price
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public Long getTotalPrice() {
        return totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order.total_price
     *
     * @param totalPrice the value for tb_order.total_price
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order.address
     *
     * @return the value of tb_order.address
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order.address
     *
     * @param address the value for tb_order.address
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order.express_company
     *
     * @return the value of tb_order.express_company
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public String getExpressCompany() {
        return expressCompany;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order.express_company
     *
     * @param expressCompany the value for tb_order.express_company
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany == null ? null : expressCompany.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order.express_id
     *
     * @return the value of tb_order.express_id
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public String getExpressId() {
        return expressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order.express_id
     *
     * @param expressId the value for tb_order.express_id
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public void setExpressId(String expressId) {
        this.expressId = expressId == null ? null : expressId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order.status
     *
     * @return the value of tb_order.status
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order.status
     *
     * @param status the value for tb_order.status
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order.created_at
     *
     * @return the value of tb_order.created_at
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order.created_at
     *
     * @param createdAt the value for tb_order.created_at
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order.updated_at
     *
     * @return the value of tb_order.updated_at
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order.updated_at
     *
     * @param updatedAt the value for tb_order.updated_at
     *
     * @mbg.generated Wed Jun 23 22:42:07 CST 2021
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}