package org.example;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Service service = new ServiceImpl();
//        try {                                                       // створюємо сувенір та його виробника
//            service.addSouvenir(new Souvenir("GGG", new Producer("ABRAMS", "USA"), 4_000_000.6));
//            service.addSouvenir(new Souvenir("tarilka", new Producer("ABRAMS", "UA"), 32567.6));
//            service.addSouvenir(new Souvenir("RRR", new Producer("GEV", "UK"), 123.6));
//            service.addSouvenir(new Souvenir("QQQQ", new Producer("TER", "OK"), 4567.6));
//            service.addSouvenir(new Souvenir("SSS", new Producer("FAS", "SA"), 123.6));
//            service.addSouvenir(new Souvenir("TTT", new Producer("VER", "LA"), 2345.6));
//            service.addSouvenir(new Souvenir("tarilka", new Producer("BREADLY", "UA"), 2134.6));
//
//              service.addSouvenir(new Souvenir("tarilka", new Producer("GEPARD", "USA"), 1245.23));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        //service.editSouvenirsPrice("tarilka","GEPARD", 346467.45);      // змінити ціну сувеніра за його ім'ям та іменем його виробника
        //service.editProducersCountry("GEPARD", "UK");                   // змінити країну виробника за його ім'ям
        //service.editProducersName("BREADLY", "LEOPARD");                // змінити ім'я виробника
        //service.showAllSouvenirs();                                     // вивести усі сувеніри
        //service.showSouvenirByProducersName("ABRAMS");                  // вивести усі сувеніри за ім'ям виробника
        //service.showSouvenirsByPriceLess(3456.1);                       // вивести усі сувеніри у яких ціна меньше ніж
        //service.showAllProducersAndTheirSouvenirs();                    // вивести усіх виробників та їхні сувеніри
        //service.showProducerBySouvenirsYear(2023);                      // вивести виробників сувенірів за їхнім роком
        //service.showAllSouvenirsByYear(2023);                           // вивести усі сувеніри за роком
        //service.showAllSouvenirsByCountry("UA");                        // вивести усі сувеніри за країною виробника
        //service.deleteProducer("VER");                                  // видалити виробника та усі його сувеніри
    }
}
