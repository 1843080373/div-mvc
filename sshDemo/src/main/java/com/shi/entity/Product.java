package com.shi.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="product", catalog = "ssh")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class Product {

    @Id
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "native") //自定义主键生成策略 generator = name
    private int id;// 商品ID
    @Column(length = 100)
    private String name;// 商品名称
    private double price;// 商品价格
}