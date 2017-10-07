package core;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dao.Committent;
import dao.Company;
import dao.District;
import dao.SocialStatus;
import servise.CommittentServise;
import servise.CompanyServise;
import servise.DistrictService;
import servise.Service;
import servise.SocialStatusServise;

public class App 
{    
    public static void main(String[] args)
    {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Service<Company> companyServise = new CompanyServise(factory);
        Service<Committent> committentServise = new CommittentServise(factory);
        Service<District> districtService = new DistrictService(factory);
        Service<SocialStatus> socialStatusServise = new SocialStatusServise(factory);
        
        District district = new District("Petrovsky");
        SocialStatus socialStatus = new SocialStatus("Bomj");
        
        Company company = new Company();
        company.setName("IBM");

        List<Company> companies = new LinkedList<Company>();
        companies.add(company);
        
        List<Committent> committents = new LinkedList<Committent>();
        
        Committent committent = new Committent();

        committent.setName("Vasya");
        committent.setSurname("Pupkin");
        committent.setPatronymic("Antonovich");
        committent.setDistrict(district);
        committent.setSocialStatus(socialStatus);
        committent.setDate(new Date());
        committent.setTelephoneNumber("+380502728020");
        committent.setCompanies(companies);

        committents.add(committent);        
        
        districtService.create(district);
        socialStatusServise.create(socialStatus);
        companyServise.create(company);
        committentServise.create(committent);

        for (Committent c : committentServise.findAll())
        {
            System.out.println(c.getName());
            System.out.println(c.getSurname());
            
            System.out.println(c.getCompanies().get(0).getName());
        }
        
        companyServise.disconnect();
        districtService.disconnect();
        socialStatusServise.disconnect();
        committentServise.disconnect();
    } 
}
