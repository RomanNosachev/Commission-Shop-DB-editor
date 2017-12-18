package service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import dao.Product;
import dao.ProductGroup;

public class ProductGenerator 
implements EntityGenerator<Product>
{    
    List<ProductGroup> productGroups;
    Map<String, List<String>> productMap = new HashMap<>();
    
    public ProductGenerator()
    {
        GenericService<ProductGroup, Serializable> pService = new GenericService<>(ProductGroup.class);
        productGroups = pService.findAll();
        pService.disconnect();
        
        for (ProductGroup productGroup : productGroups)
            productMap.put(productGroup.getName(), new ArrayList<>());
        
        List<String> autoMoto = productMap.get("Авто-мото");
        autoMoto.add("Nissan Qashqai I");
        autoMoto.add("Acura Integra II");
        autoMoto.add("Alfa Romeo 166");
        autoMoto.add("Audi A3");
        autoMoto.add("Audi A6 allroad");
        autoMoto.add("Audi S8");
        autoMoto.add("BMW X5");
        autoMoto.add("BMW Z4");
        autoMoto.add("BMW Alpina");
        autoMoto.add("Audi Coupe");
        autoMoto.add("Chevrolet Aveo");
        autoMoto.add("Chevrolet Blazer");
        autoMoto.add("Chevrolet Camaro");
        autoMoto.add("Chevrolet Captiva");
        autoMoto.add("Chevrolet Impala");
        autoMoto.add("Citroen C3");
        autoMoto.add("Dacia Logan");
        autoMoto.add("Daewoo Matiz");
        autoMoto.add("Dodge Charger");
        autoMoto.add("Fiat 500");
        autoMoto.add("Fiat Panda");
        autoMoto.add("Ford Escape");
        autoMoto.add("Ford Focus");
        autoMoto.add("Honda Civic Ferio");
        autoMoto.add("Honda Accord");
        autoMoto.add("Hyundai Solaris");
        autoMoto.add("Hyundai Sonata");
        autoMoto.add("Kia Magentis");
        autoMoto.add("Lexus GX");
        autoMoto.add("Lexus LX");
        autoMoto.add("Mazda 6");
        autoMoto.add("Mitsubishi Eclipse");
        
        List<String> tech = productMap.get("Бытовая техника");
        tech.add("Кофеварка Sony");
        tech.add("Кофеварка Vega");
        tech.add("Кофемолка Sony");
        tech.add("Компьютер Asus");
        tech.add("Ноутбук Asus");
        tech.add("Компьютер Acer");
        tech.add("Компьютер IBM");
        tech.add("Компьютер Lenovo");
        tech.add("Компьютер Apple");
        tech.add("Ноутбук Apple");
        tech.add("Ноутбук Lenovo");
        tech.add("Ноутбук Acer");
        tech.add("Ноутбук Samsung");
        tech.add("Кондиционер Samsung");
        tech.add("Смартфон Lenovo");
        tech.add("Смартфон Google");
        tech.add("Смартфон Samsung");
        tech.add("Смартфон Meizu");
        tech.add("Смартфон Xiomi");
        tech.add("Смартфон Apple");
        tech.add("Телевизор Panasonic");
        tech.add("Телевизор Soni");
        tech.add("Телевизор Fujitsu");
        tech.add("Телевизор Samsung");
        tech.add("Холодильник Samsung");
        tech.add("Мультиварка Samsung");
        tech.add("Мультиварка Xiomi");
        tech.add("Часы Samsung");
        tech.add("Часы Google");
        tech.add("Часы Xiomi");
        tech.add("Часы Meizu");
        
        List<String> chem = productMap.get("Бытовая химия");
        chem.add("Стиральный порошок");
        chem.add("Средство для очистки труб");
        chem.add("Средство для мытья стекол");
        chem.add("Средство от накипи");

        List<String> pen = productMap.get("Канцелярия");
        pen.add("Карандаш");
        pen.add("Клей");
        pen.add("Ручка");
        pen.add("Циркуль");
        pen.add("Ножницы");
        pen.add("Цветная бумага");
        pen.add("Скрепки, 200 шт.");
        pen.add("Маркер");
        pen.add("Фломастеры");
        pen.add("Тетрадь");
        
        List<String> home = productMap.get("Недвижимость");
        home.add("Дом, 1к.");
        home.add("Дом, 2к.");
        home.add("Дом, 3к.");
        home.add("Дом, 4к.");
        home.add("Дом, 5к.");
        home.add("Квартира, 1к.");
        home.add("Квартира, 2к.");
        home.add("Квартира, 3к.");
        home.add("Квартира, 4к.");
        home.add("Квартира, 5к.");
        home.add("Гараж");
        home.add("Складское помещение");

        List<String> shirt = productMap.get("Одежда");
        shirt.add("Куртка");
        shirt.add("Штаны");
        shirt.add("Платье");
        shirt.add("Ботинки");
        shirt.add("Кроссовки");
        shirt.add("Худи");
        shirt.add("Свитер");
        shirt.add("Футболка");
        shirt.add("Кепка");
        shirt.add("Шапка");
        shirt.add("Пальто");
        shirt.add("Шуба");

        List<String> build = productMap.get("Стройматериалы");
        build.add("Шифер");
        build.add("Доски");
        build.add("Гипсокартон");
        build.add("Профнастил");
        build.add("Кирпич");
        build.add("Фагот");
        build.add("Плитка");
        build.add("Кафель");
        build.add("Пластин");
        build.add("Кроноспан");

        List<String> forHome = productMap.get("Товары для дома");
        forHome.add("Светильник");
        forHome.add("Зубная паста");
        forHome.add("Полка");
        forHome.add("Гардины");
        forHome.add("Стол");
        forHome.add("Стул");
        forHome.add("Скатерть");
        forHome.add("Ковер");
        forHome.add("Кровать");
        forHome.add("Лосьон");
        forHome.add("Крем для бритья");
        forHome.add("Крем увлажняющий для рук");
        forHome.add("Крем для лица");
        forHome.add("Шампунь");
        forHome.add("Гель для душа");
        forHome.add("Зубной порошок");

        List<String> sport = productMap.get("Товары для спорта");
        sport.add("Мяч футбольный");
        sport.add("Ракетка для настольного тенниса");
        sport.add("Шахматы");
        sport.add("Гиря");
        sport.add("Штанга");
        sport.add("Гантель");
        sport.add("Мяч баскетбольный");
        sport.add("Коньки");
        sport.add("Бита бейсбольная");

        List<String> luxury = productMap.get("Ювелирные изделия");
        luxury.add("Кольцо золотое");
        luxury.add("Кольцо серебрянное");
        luxury.add("Колье");
        luxury.add("Цепочка");
        luxury.add("Перстень");
        luxury.add("Серьги");
        luxury.add("Браслет");
        luxury.add("Кулон");
    }

    @Override
    public Product generate()
    {
        Random random = new Random();
                
        ProductGroup productGroup = productGroups.get(Math.abs(random.nextInt(productGroups.size() - 1)));
        
        List<String> names = productMap.get(productGroup.getName());
        String name = names.get(Math.abs(random.nextInt(names.size() - 1)));
        
        BigDecimal price;
        
        if (productGroup.getName().equals("Авто-мото") ||
                productGroup.getName().equals("Недвижимость"))
            price = new BigDecimal(100000 + Math.abs(random.nextInt(1000000)) * 100);
        else if (productGroup.getName().equals("Ювелирные изделия"))
            price = new BigDecimal(1000 + Math.abs(random.nextInt(5000)) * 100);
                
        else
            price = new BigDecimal(Math.abs(random.nextInt(10000)) * 100);
                
        return new Product(productGroup, name, price);
    }   
}
