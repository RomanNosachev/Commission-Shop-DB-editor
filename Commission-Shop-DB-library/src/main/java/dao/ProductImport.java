package dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

@NamedEntityGraph(name = "ProductImport", includeAllAttributes = true)

@Entity
@Table(name = "ProductImport")
public class ProductImport 
implements DB_Entity
{
    private static final long serialVersionUID = 2440765104325301986L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productImport_gen")
    @SequenceGenerator(name = "productImport_gen", sequenceName = "productImport_seq")
    private long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    private Product product;
    
    @Column(name = "receiptNumber", nullable = false)
    private long receiptNumber;
    
    @Column(name = "date")
    private Date date;
    
    @Column(name = "count")
    private int count;
 
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    
    public ProductImport()
    {
    }
    
    public ProductImport(Product product, long receiptNumber, Date date, int count, BigDecimal price)
    {
        super();
        
        this.product = product;
        this.receiptNumber = receiptNumber;
        this.date = date;
        this.count = count;
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

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }
    
    public long getReceiptNumber()
    {
        return receiptNumber;
    }

    public void setReceiptNumber(long receiptNumber)
    {
        this.receiptNumber = receiptNumber;
    }
    
    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public void merge(DB_Entity object)
    {
        if (object == null || object.getClass() != this.getClass())
            return;
                
        ProductImport tempObject = (ProductImport) object;
        
        if (tempObject.count > 0)
            count = tempObject.count;
        
        if (tempObject.date != null)
            date = tempObject.date;
        
        if (tempObject.price != null)
            price = tempObject.price;
        
        if (tempObject.product != null)
            product = tempObject.product;
        
        if (tempObject.receiptNumber > 0)
            receiptNumber = tempObject.receiptNumber;
    }

    public Serializable getPK()
    {
        return id;
    }
}
