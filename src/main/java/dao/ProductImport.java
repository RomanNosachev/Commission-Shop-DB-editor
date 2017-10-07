package dao;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "ProductImport")
public class ProductImport 
implements Serializable
{
    private static final long serialVersionUID = 2440765104325301986L;

    @Id
    @Column(name = "Id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Product")
    private Product product;
    
    @Column(name = "ReceiptNumber", nullable = false)
    private long receiptNumber;
    
    @Column(name = "Date")
    private Date date;
    
    @Column(name = "Count")
    private int count;
 
    public ProductImport()
    {
    }
    
    public ProductImport(Product product, long receiptNumber, Date date, int count)
    {
        super();
        this.product = product;
        this.receiptNumber = receiptNumber;
        this.date = date;
        this.count = count;
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
}
