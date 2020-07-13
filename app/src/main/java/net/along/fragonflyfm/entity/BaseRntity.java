package net.along.fragonflyfm.entity;

import java.util.UUID;

public class BaseRntity {
    UUID id;
    public BaseRntity(){
        id=UUID.randomUUID();
    }
    public UUID getId(){
        return id;
    }

    /**
     * 获取数据库内映射表的主键的值，该方法仅为备用，ORM获取主键值时优先获取注解为@AsPrimaryKey的列的值
     * 如果所有列都找不到该注解才使用本方法获取
     * @return id
     */
    public Object getIdentityValue() {
        return id;
    }
}
