package org.spring.springboot.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;
}
