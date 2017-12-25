package service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import dao.Committent;
import dao.Deal;
import dao.Product;
import dao.ProductImport;

public class DealGenerator 
implements EntityGenerator<Deal>
{
    GenericService<Deal, Serializable> service = new GenericService<>(Deal.class);
    
    List<ProductImport> products = new ArrayList<>();
    List<Committent> committents = new ArrayList<>();
    
    BigDecimal multiplicand = new BigDecimal(1.50);
    
    public DealGenerator()
    {
        GenericService<ProductImport, Serializable> producService = new GenericService<>(ProductImport.class);
        
        products = producService.findAll();
        
        for (ProductImport productImport : products)
        {
            productImport.getProduct().getProductGroup();
        }
        
        producService.disconnect();
        
        GenericService<Committent, Serializable> committentService = new GenericService<>(Committent.class);
        committents = committentService.findAll();
        committentService.disconnect();
    }

    @Override
    public Deal generate()
    {
        Random random = new Random();
        
        int productIndex = Math.abs(random.nextInt(products.size() - 1));
        
        Product product = products.get(productIndex).getProduct();
        int count = (products.get(productIndex).getCount() - Math.abs(random.nextInt(10)));
        
        if (count <= 0)
            count = 1;
        
        BigDecimal price = products.get(productIndex).getPrice().multiply(multiplicand);
        
        Committent committent = committents.get(Math.abs(random.nextInt(committents.size() - 1)));
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(products.get(productIndex).getDate());            
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date date = calendar.getTime();
        
        return new Deal(product, committent, date, count, price);
    }
}
