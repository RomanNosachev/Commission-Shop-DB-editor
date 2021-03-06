package dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedEntityGraph;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedEntityGraph(name = "Deal", includeAllAttributes = true)

@Entity
@Table(name = "Deal")
public class Deal 
implements DB_Entity
{
    private static final long serialVersionUID = 3789977798874029973L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deal_gen")
    @SequenceGenerator(name = "deal_gen", sequenceName = "deal_seq")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "product", nullable = false)
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "committent", nullable = false)
    private Committent comittent;
    
    @Column(name = "date", nullable = false)
    private Date date;
    
    @Column(name = "count", nullable = false)
    private int count;
    
    public Deal()
    {
    }

    public Deal(Product product, Committent committent, Date date, int count)
    {
        super();
        this.product = product;
        this.comittent = committent;
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

    public void setCommittent(Committent committent)
    {
        this.comittent = committent;
    }
    
    public Committent getCommittent()
    {
        return comittent;
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
