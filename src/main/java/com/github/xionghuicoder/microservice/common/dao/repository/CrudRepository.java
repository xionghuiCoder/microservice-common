package com.github.xionghuicoder.microservice.common.dao.repository;

/**
 * 基本增删改查(CRUD)数据访问接口
 *
 * @author xionghui
 * @date 2018/12/12
 * @param <T> Po
 * @param <U> Example
 * @param <K> 主键字段数据类型(Integer,Long等)
 */
public interface CrudRepository<T, U, K> extends InsertRepository<T>, DeleteRepository<T, U, K>,
    UpdateRepository<T, U>, SelectRepository<T, U, K> {

}
