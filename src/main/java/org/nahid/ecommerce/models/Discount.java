package org.nahid.ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Discount {

    @Id
    @GeneratedValue
    private Long id;
    private String percentage;

    @OneToOne(mappedBy = "discount")
    private Product product;

    public Discount(Long id, String percentage) {
        this.id = id;
        this.percentage = percentage;
    }
}
