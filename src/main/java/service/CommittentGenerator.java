package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import dao.Committent;
import dao.Company;
import dao.District;
import dao.SocialStatus;

public class CommittentGenerator 
implements EntityGenerator<Committent> 
{    
    List<String> nameLines = new ArrayList<>();
    List<String> surnameLines = new ArrayList<>();
    List<String> patronymicLines = new ArrayList<>();
    
    List<District> districtsList;
    List<SocialStatus> socialStatusList;
    List<Company> companyList;
    
    public CommittentGenerator()
    {
        readLines("/name.txt/", nameLines);
        readLines("/surname.txt/", surnameLines);
        readLines("/patronymic.txt/", patronymicLines);
        
        GenericService<District, Serializable> districtService = new GenericService<>(District.class);
        districtsList = districtService.findAll();
        districtService.disconnect();
        
        GenericService<SocialStatus, Serializable> socialStatusService = new GenericService<>(SocialStatus.class);
        socialStatusList = socialStatusService.findAll();
        socialStatusService.disconnect();
        
        GenericService<Company, Serializable> companyService = new GenericService<>(Company.class);
        companyList = companyService.findAll();
        companyService.disconnect();
    }
    
    private void readLines(String resourcePath, List<String> lines)
    {
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;    
        
        try
        {                        
            inputStream = getClass().getResourceAsStream(resourcePath);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            
            String line;
            
            while ((line = bufferedReader.readLine()) != null)
                lines.add(line);                
                        
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally 
        {
            try
            {
                bufferedReader.close();
                inputStream.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }            
        }
    }
    
    @Override
    public Committent generate()
    {
        Random random = new Random();
        
        String name = nameLines.get(Math.abs(random.nextInt(nameLines.size() - 1)));
        String surname = surnameLines.get(Math.abs(random.nextInt(surnameLines.size() - 1)));
        String patronymic = patronymicLines.get(Math.abs(random.nextInt(patronymicLines.size() - 1)));
        District district = districtsList.get(Math.abs(random.nextInt(districtsList.size() - 1)));        
        SocialStatus socialStatus = socialStatusList.get(Math.abs(random.nextInt(socialStatusList.size() - 1)));

        List<Company> companies = new ArrayList<>();
        
        for (int i = 0; i < 1 + Math.abs(random.nextInt(2)); i++)
            companies.add(companyList.get(Math.abs(random.nextInt(companyList.size() - 1))));
                
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1970 + Math.abs(random.nextInt(30)));
        calendar.set(Calendar.MONTH, Math.abs(random.nextInt(12)));
        calendar.set(Calendar.DAY_OF_MONTH, Math.abs(random.nextInt(28)));
        
        Date date = calendar.getTime();
        
        StringBuilder sb = new StringBuilder();
        sb.append("+380");
        
        for (int i = 0; i < 9; i++)
            sb.append(random.nextInt(9));
        
        String telephoneNumber = sb.toString();
        
        return new Committent(name, surname, patronymic, district, socialStatus, companies, date, telephoneNumber);        
    }
}
