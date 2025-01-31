package org.legalsight.speechAPI.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public abstract class CoreDTO implements Serializable {
    private String id;
    private Integer version;
    private Date creationDate;
    private Date lastModified;
}