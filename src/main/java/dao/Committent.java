package dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Committent")
public class Committent 
implements Serializable
{
    private static final long serialVersionUID = 6358304485892511952L;

    @Id
    @Column(name = "Id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    @Column(name = "Name", length = 20, nullable = false)
    private String name;
    
    @Column(name = "Surname", length = 40, nullable = false)
    private String surname;
    
    @Column(name = "Patronymic", length = 25)
    private String patronymic;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "District")
    private District district;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SocialStatus")
    private SocialStatus socialStatus;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Company")
    private List<Company> companies;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Date", columnDefinition = "DATE")
    private Date date;
    
    @Column(name = "TelephoneNumber", length = 20)
    private String telephoneNumber;
  
    public Committent(String name, String surname, String patronymic, District district, SocialStatus socialStatus,
            List<Company> companies, Date date, String telephoneNumber)
    {
        super();
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.district = district;
        this.socialStatus = socialStatus;
        this.companies = companies;
        this.date = date;
        this.telephoneNumber = telephoneNumber;
    }

    public Committent()
    {
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getPatronymic()
    {
        return patronymic;
    }

    public void setPatronymic(String patronymic)
    {
        this.patronymic = patronymic;
    }

    public District getDistrict()
    {
        return district;
    }

    public void setDistrict(District district)
    {
        this.district = district;
    }

    public SocialStatus getSocialStatus()
    {
        return socialStatus;
    }

    public void setSocialStatus(SocialStatus socialStatus)
    {
        this.socialStatus = socialStatus;
    }

    public List<Company> getCompanies()
    {
        return companies;
    }

    public void setCompanies(List<Company> companies)
    {
        this.companies = companies;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getTelephoneNumber()
    {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber)
    {
        this.telephoneNumber = telephoneNumber;
    }
}
