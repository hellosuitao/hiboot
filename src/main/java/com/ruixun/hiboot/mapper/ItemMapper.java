package com.ruixun.hiboot.mapper;

import com.ruixun.hiboot.bean.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemMapper {

    @Insert("insert into item(NAME,title,price,image) values(#{name},#{title},#{price},#{image})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public void insert(Item item);

    @Select("select * from item")
    public List<Item> findAll();

    @Select("select * from item limit #{from},#{size}")
    List<Item> findByPageSize(int from ,int size );

    @Select("select * from item where id=#{id}")
    Item findById(Integer id);

    @Update("update item set name = #{name},title=#{title},price=#{price},image=#{image} where id=#{id}")
    void update(Item item);

    @Delete("delete from item where id = #{id}")
    void deleteItemById(Integer id);

}
