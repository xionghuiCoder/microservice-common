package com.github.xionghuicoder.microservice.common.dao.repository.sharding;

/**
 * 分表基本增删改查(CRUD)数据访问接口
 *
 * @author xionghui
 * @date 2018/12/12
 * @param <T> Po
 * @param <U> Example
 * @param <K> 主键字段数据类型(Integer,Long等)
 */
public interface CrudShardingRepository<T, U, K>
    extends InsertShardingRepository<T>, DeleteShardingRepository<T, U, K>,
    UpdateShardingRepository<T, U>, SelectShardingRepository<T, U, K> {
}
