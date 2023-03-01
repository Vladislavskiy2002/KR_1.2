package org.example;

import java.io.IOException;

public interface Service {
    void addSouvenir(Souvenir souvenir) throws IOException;
    Boolean addProducer(Producer producer);
    void editProducersName(final String oldName, final String newName);
    void editProducersCountry(final String name, final String newCountry);
    void editSouvenirsPrice(final String souvenirsName, final String producerName, final Double newPrice);
    void showAllProducers();
    void showAllSouvenirs();
    void showSouvenirByProducersName(final String producerName);
    void showSouvenirsByPriceLess(final Double price);
    void showAllProducersAndTheirSouvenirs();
    void showProducerBySouvenirsYear(final Integer year);
    void showAllSouvenirsByYear(final Integer year);

    void showAllSouvenirsByCountry(final String country);
    void deleteProducer(final String name);
}
