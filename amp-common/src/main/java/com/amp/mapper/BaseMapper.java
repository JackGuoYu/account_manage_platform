package com.amp.mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author guoyu
 * @since 2021-05-19
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

   /**
    * 查询数据是否存在
    * @param t
    * @return
    */
   int isExists(T t);

   /**
    * 根据ID查询记录
    * @param id
    * @return
    */
   T selectById(String id);

   /**
    * 根据条件查询记录
    * @param t
    * @return
    */
   List<T> selectList(T t);

    /**
     * 插入
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 修改
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 删除
     * @param t
     * @return
     */
    int delete(T t);

    /**
     * 根据多个Id查询
     * @param ids
     * @return
     */
    List<T> selectByIds(List<String> ids);
}
