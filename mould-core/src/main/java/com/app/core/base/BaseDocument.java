package com.app.core.base;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Spring-MongoDB 持久化实体基类
 */
@Document
public class BaseDocument implements Serializable {
    private static final long serialVersionUID = 4414656466257410243L;

    @NotNull
    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}