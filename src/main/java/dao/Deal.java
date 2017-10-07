package dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Deal 
implements Serializable
{
    private static final long serialVersionUID = 3789977798874029973L;

    @Id
    @Column(name = "Id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "Product", nullable = false)
    private Product product;
    
    @Column(name = "Date", nullable = false)
    private Date date;
    
    @Column(name = "Count", nullable = false)
    private int count;
    
    public Deal()
    {
    }

    public Deal(Product product, Date date, int count)
    {
        super();
        this.product = product;
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
}
