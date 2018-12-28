package com.github.xionghuicoder.microservice.common.service;

/**
 * 基本增删改查(CRUD)数据访问服务
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <T> Po
 * @param <U> Example
 * @param <K> 主键字段数据类型(Integer,Long等)
 */
public interface CrudService<T, U, K>
    extends AddService<T>, RemoveService<T, U, K>, EditService<T, U>, GetService<T, U, K> {

}
