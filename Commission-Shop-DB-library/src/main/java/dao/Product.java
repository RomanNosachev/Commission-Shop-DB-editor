package dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedEntityGraph;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedEntityGraph(name = "Product", includeAllAttributes = true)

@Entity
@Table(name = "Product")
public class Product 
implements DB_Entity
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
    
    public Product()
    {
    }

    public Product(ProductGroup productGroup, String name)
    {
        super();
        this.productGroup = productGroup;
        this.name = name;
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

    public void merge(DB_Entity object)
    {
        if (object == null || object.getClass() != this.getClass())
            return;
        
        Product tempObject = (Product) object;
        
        if (tempObject.name != null && !tempObject.name.isEmpty())
            name = tempObject.name;
        
        if (tempObject.productGroup != null)
            productGroup = tempObject.productGroup;
    }

    public Serializable getPK()
    {
        return id;
    } 
}
