package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.io.Serializable;

/**
 * cms板块数据映射表--乐视儿童
 */
public class ChannelDataExtendMysqlTable implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 116398883235244009L;
    /*
     * 父级板块id
     */
    private String parent_block_id;
    /*
     * 父级板块描述
     */
    private String parent_block_desc;
    /*
     * 年龄
     */
    private String age;
    /*
     * 性别
     */
    private String gender;
    /*
     * 板块id
     */
    private String block_id;
    /*
     * 板块描述
     */
    private String block_desc;

    private String week;

    public String getParent_block_id() {
        return this.parent_block_id;
    }

    public void setParent_block_id(String parent_block_id) {
        this.parent_block_id = parent_block_id;
    }

    public String getParent_block_desc() {
        return this.parent_block_desc;
    }

    public void setParent_block_desc(String parent_block_desc) {
        this.parent_block_desc = parent_block_desc;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBlock_id() {
        return this.block_id;
    }

    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }

    public String getBlock_desc() {
        return this.block_desc;
    }

    public void setBlock_desc(String block_desc) {
        this.block_desc = block_desc;
    }

    public String getWeek() {
        return this.week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

}
