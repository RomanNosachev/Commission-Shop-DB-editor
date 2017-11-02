package dao;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product 
implements Serializable
{
    private static final long serialVersionUID = 842537533612694916L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
    @SequenceGenerator(name = "product_gen", sequenceName = "product_seq")
    private long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productGroup")
    private ProductGroup productGroup;
    
    @Column(name = "name", length = 40, nullable = false)
    private String name;
    
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    
    public Product()
    {
    }

    public Product(ProductGroup productGroup, String name, BigDecimal price)
    {
        super();
        this.productGroup = productGroup;
        this.name = name;
        this.price = price;
    }
    
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public ProductGroup getProductGroup()
    {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup)
    {
        this.productGroup = productGroup;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }
}
