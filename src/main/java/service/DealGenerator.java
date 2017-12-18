package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import dao.Committent;
import dao.Deal;
import dao.Product;

public class DealGenerator 
implements EntityGenerator<Deal>
{
    GenericService<Deal, Serializable> service = new GenericService<>(Deal.class);
    
    List<Product> products = new ArrayList<>();
    List<Committent> committents = new ArrayList<>();
    
    public DealGenerator()
    {
        GenericService<Product, Serializable> producService = new GenericService<>(Product.class);
        products = producService.findAll();
        producService.disconnect();
        
        GenericService<Committent, Serializable> committentService = new GenericService<>(Committent.class);
        committents = committentService.findAll();
        committentService.disconnect();
    }

    @Override
    public Deal generate()
    {
        Random random = new Random();
        
        Product product = products.get(Math.abs(random.nextInt(products.size() - 1)));
        Committent committent = committents.get(Math.abs(random.nextInt(committents.size() - 1)));
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2000 + Math.abs(random.nextInt(17)));
        calendar.set(Calendar.MONTH, Math.abs(random.nextInt(12)));
        calendar.set(Calendar.DAY_OF_MONTH, Math.abs(random.nextInt(28)));
        
        Date date = calendar.getTime();
        
        int count = Math.abs(random.nextInt(1000));
        
        return new Deal(product, committent, date, count);
    }
}
