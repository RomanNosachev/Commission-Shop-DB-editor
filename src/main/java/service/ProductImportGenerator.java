package service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import dao.Product;
import dao.ProductImport;

public class ProductImportGenerator 
implements EntityGenerator<ProductImport>
{  
    List<Product> products;
    
    public ProductImportGenerator()
    {
        GenericService<Product, Serializable> service = new GenericService<>(Product.class);
        products = service.findAll();
        service.disconnect();
    }

    @Override
    public ProductImport generate()
    {
        Random random = new Random();
        
        Product product = products.get(Math.abs(random.nextInt(products.size() - 1)));
        long receiptNumber = Math.abs(random.nextInt(1000));
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, Math.abs(random.nextInt(12)));
        calendar.set(Calendar.DAY_OF_MONTH, Math.abs(random.nextInt(28)));
        
        Date date = calendar.getTime();
        int count;
        
        if (product.getProductGroup().getName().equals("Авто-мото") ||
            product.getProductGroup().getName().equals("Недвижимость"))
            count = 1;
        else
            count = Math.abs(random.nextInt(40) + 1);
        
        BigDecimal price;
        
        if (product.getProductGroup().getName().equals("Авто-мото") ||
                product.getProductGroup().getName().equals("Недвижимость"))
            price = new BigDecimal(100000 + Math.abs(random.nextInt(1000000)));
        else if (product.getProductGroup().getName().equals("Ювелирные изделия"))
            price = new BigDecimal(1000 + Math.abs(random.nextInt(5000)));
                
        else
            price = new BigDecimal(Math.abs(random.nextInt(10000)));
        
        return new ProductImport(product, receiptNumber, date, count, price);
    }   
}
