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
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product 
implements Serializable
{
    private static final long serialVersionUID = 842537533612694916L;

    @Id
    @Column(name = "Id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductGroup")
    private ProductGroup productGroup;
    
    @Column(name = "Name", length = 40, nullable = false)
    private String name;
    
    @Column(name = "Price", nullable = false)
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
