package org.example;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ServiceImpl implements Service {

    final String urlProducer = "D:\\IDEA2022\\JAVA_PROJECTS\\GeekForLess\\Liked\\KR_1.2\\producers\\";      // шлях до папки де будуть зберігатися виробникі
    final String urlSouvenir = "D:\\IDEA2022\\JAVA_PROJECTS\\GeekForLess\\Liked\\KR_1.2\\souvenirs\\";      // шлях до папки де будуть зберігатися сувеніри

    @Override
    public void addSouvenir(Souvenir souvenir) throws IOException {

        try (BufferedWriter writer1 = new BufferedWriter(new FileWriter(urlSouvenir + souvenir.getName() + ".txt", true))) {
            boolean isContains = Files.lines(Paths.get(urlSouvenir
                    + souvenir.getName() + ".txt")).anyMatch(e -> e.contains(souvenir.getDetails().getName()));
            if (addProducer(souvenir.getDetails()) || !isContains) {                                    // якщо виробника з таким іменем неіснує, то ми сворюємо нового, а якщо існує, то метод addProducer повертає false.
                                                                                                        // змінна isContains містить у собі булеве значення яке означає чи існує вже сувенір з таким виробником
                if (!Files.lines(Paths.get(urlSouvenir + souvenir.getName() + ".txt")).findAny().isPresent()) {   // якщо файл пустий, то ми записуємо у нього шаблон JSON форми
                    writer1.write("[\n" + souvenir + "\n]");
                }else                                                                                                  // якщо файл не пустий то ми замінюємо замикаючу JSON скобку ']' на кому ','
                                                                                                                        // а потім просто записуємо новий json обьєкт і додаємо до нього в кінець замикаючу JSON скобку
                {
                    StringBuilder sb = new StringBuilder();
                    writer1.close();
                    try (BufferedReader reader1 = new BufferedReader(new FileReader(urlSouvenir + souvenir.getName() + ".txt"))) {
                        String strLine;
                        while ((strLine = (reader1.readLine())) != null) {
                            if(!strLine.equals("]"))
                            {
                                strLine+="\n";
                            }
                            sb.append(strLine.replace("]", ","));
                    }
                }
                    try (BufferedWriter writer2 = new BufferedWriter(new FileWriter(urlSouvenir + souvenir.getName() + ".txt"))) {
                        writer2.write(sb.toString());
                        writer2.write(souvenir + "\n]");
                    }
            }
        }

        }
    }
        @Override
    public Boolean addProducer(Producer producer) {
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(urlProducer + producer.getName() + ".txt", true))) {
            boolean isContains = Files.lines(Paths.get(urlProducer + producer.getName() + ".txt")).anyMatch(e -> e.contains(producer.getName()));
            if (!isContains) {
                writter.write(producer.toString());
                writter.newLine();
                return true;
            } else {
                System.out.println("producer with name: " + producer.getName() + " has already exist");
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editProducersName(String oldName, String newName) {
        try {
            List<Path> filesInFolder = Files.walk(Paths.get(urlSouvenir))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            for (Path p : filesInFolder) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(p.toFile()))) {
                    Gson gson = new Gson();
                    Souvenir[] souvenirs = gson.fromJson(bufferedReader, Souvenir[].class);
                    Arrays.stream(souvenirs)
                            .filter(s -> s.getDetails().getName().equals(oldName))
                            .forEach(s-> {
                                try {
                                    addSouvenir(new Souvenir(s.getName(),new Producer(newName,s.getDetails().getCountry()),s.getPrice()));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                }
            }
            deleteProducer(oldName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editProducersCountry(String name, String newCountry) {
        try {
            List<Path> filesInFolder = Files.walk(Paths.get(urlSouvenir))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            List<Souvenir> newSouvenirs = new ArrayList<>();
            for (Path p : filesInFolder) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(p.toFile()))) {
                    Gson gson = new Gson();
                    Souvenir[] souvenirs = gson.fromJson(bufferedReader, Souvenir[].class);
                    Arrays.stream(souvenirs)
                            .filter(s -> s.getDetails().getName().equals(name))
                            .forEach(s-> {
                                newSouvenirs.add(new Souvenir(s.getName(),new Producer(name,newCountry),s.getPrice()));
                            });
                }
            }
            deleteProducer(name);
            for(Souvenir s : newSouvenirs)
            {
                addSouvenir(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editSouvenirsPrice(String souvenirsName, String producerName, Double newPrice) {
        try {
            List<Path> filesInFolder = Files.walk(Paths.get(urlSouvenir))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            List<Souvenir> newSouvenirs = new ArrayList<>();
            for (Path p : filesInFolder) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(p.toFile()))) {
                    Gson gson = new Gson();
                    Souvenir[] souvenirs = gson.fromJson(bufferedReader, Souvenir[].class);
                    Arrays.stream(souvenirs)
                            .filter(s -> s.getDetails().getName().equals(producerName) && s.getName().equals(souvenirsName))
                            .forEach(s-> {
                                newSouvenirs.add(new Souvenir(s.getName(),s.getDetails(), newPrice));
                            });
                }
            }
            deleteSouvenir(souvenirsName,producerName);
            for(Souvenir s : newSouvenirs)
            {
                addSouvenir(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAllContentInFile(final String path) {
        try {
            List<Path> filesInFolder = Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            for (Path p : filesInFolder) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(p.toFile()))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showAllProducers() {
        showAllContentInFile(urlProducer);
    }

    @Override
    public void showAllSouvenirs() {
        showAllContentInFile(urlSouvenir);
    }

    @Override
    public void showSouvenirByProducersName(final String producerName) {
        try {
            List<Path> filesInFolder = Files.walk(Paths.get(urlSouvenir))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            for (Path p : filesInFolder) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(p.toFile()))) {
                    Gson gson = new Gson();
                    Souvenir[] souvenirs = gson.fromJson(bufferedReader, Souvenir[].class);
                    Arrays.stream(souvenirs)
                            .filter(s -> s.getDetails().getName().equals(producerName)).map(s -> s)
                            .forEach(System.out::println);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showSouvenirsByPriceLess(final Double price) {
        try {
            List<Path> filesInFolder = Files.walk(Paths.get(urlSouvenir))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            for (Path p : filesInFolder) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(p.toFile()))) {
                    Gson gson = new Gson();
                    Souvenir[] souvenirs = gson.fromJson(bufferedReader, Souvenir[].class);
                    Arrays.stream(souvenirs)
                            .filter(s -> s.getPrice() < price)
                            .forEach(System.out::println);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showAllProducersAndTheirSouvenirs() {
        try {
            List<Path> filesInSouvenirFolder = Files.walk(Paths.get(urlSouvenir))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            List<Path> filesInProducerFolder = Files.walk(Paths.get(urlProducer))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            for (Path p1 : filesInProducerFolder) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(p1.toFile()))) {
                    Gson gson = new Gson();
                    Producer producer = gson.fromJson(bufferedReader, Producer.class);
                    System.out.println("PRODUCER :" + producer);
                    System.out.println("[");

                    for (Path p2 : filesInSouvenirFolder) {
                        try (BufferedReader bufferedReader2 = new BufferedReader(new FileReader(p2.toFile()))) {
                            Gson gson2 = new Gson();
                            Souvenir[] souvenir = gson2.fromJson(bufferedReader2, Souvenir[].class);
                            Arrays.stream(souvenir).filter(s -> s.getDetails().getName()
                                            .equals(producer.getName()))
                                    .forEach(System.out::println);
                        }
                    }
                    System.out.println("]");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showProducerBySouvenirsYear(Integer year) { // next
        try {
            List<Path> filesInFolder = Files.walk(Paths.get(urlSouvenir))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            for (Path p : filesInFolder) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(p.toFile()))) {
                    Gson gson = new Gson();
                    Souvenir[] souvenirs = gson.fromJson(bufferedReader, Souvenir[].class);
                    Arrays.stream(souvenirs)
                            .filter(s -> s.getDateYear().equals(year)).map(s -> s.getDetails().toString())
                            .forEach(System.out::println);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showAllSouvenirsByYear(Integer year) {
        try {
            List<Path> filesInFolder = Files.walk(Paths.get(urlSouvenir))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            for (Path p : filesInFolder) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(p.toFile()))) {
                    Gson gson = new Gson();
                    Souvenir[] souvenirs = gson.fromJson(bufferedReader, Souvenir[].class);
                    Arrays.stream(souvenirs)
                            .filter(s -> s.getDateYear().equals(year))
                            .forEach(System.out::println);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void showAllSouvenirsByCountry(final String country)
    {
        try {
            List<Path> filesInFolder = Files.walk(Paths.get(urlSouvenir))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            for (Path p : filesInFolder) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(p.toFile()))) {
                    Gson gson = new Gson();
                    Souvenir[] souvenirs = gson.fromJson(bufferedReader, Souvenir[].class);
                    Arrays.stream(souvenirs)
                            .filter(s -> s.getDetails().getCountry().equals(country))
                            .forEach(System.out::println);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void refactorFile(Path input, String nameProducer) throws IOException {            // Ми зчитуємо кожну строку з файлу і записуємо у тимчасово створений файл.
        Path temp = Files.createTempFile("temp", ".txt");                         // якщо у ній співпадає ім'я з заданим в параметрі, то ми просто її не записуємо в тимчасовий файл.
        Stream<String> lines = Files.lines(input);                                           // Також створений лічильник counter для того, щоб контролювати чи ми не "видалили" першу строку,
        AtomicReference<Integer> counter = new AtomicReference<>(0);               // і якщо це так, то ми видаляємо кОму яка є частиною JSON наступного сувеніру, і після цього наш сувенір стає першим в массиві JSON
        try (BufferedWriter writer = Files.newBufferedWriter(temp)) {                       // наприкінці ми за допомогою метода Files.move копіюємо з нашого тимчасового файлу в наш головний файл вже зміненні данні сувенірів
            lines                                                                           // і в самому кінці ми видаляємо файл нашого виробника
                    .filter(line -> !line.contains(nameProducer))
                    .forEach(line -> {
                        try {
                            counter.getAndSet(counter.get() + 1);
                            if(counter.get() ==  2)
                            {
                                if(line.charAt(0) == ',') {
                                    line = line.replaceFirst(",", "");
                                }
                            }
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        if(counter.get() == 2)              // якщо файл містить лише два символа то він перезаписується в пустий файл
        {
            PrintWriter pw = new PrintWriter(temp.toFile());
            pw.close();
        }
        Files.move(temp, input, StandardCopyOption.REPLACE_EXISTING);
    }
        private void deleteSouvenir(String nameSouvenir, String nameProducer) {
        try {
            Path input = Paths.get(urlSouvenir + nameSouvenir + ".txt");
            refactorFile(input,nameProducer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void deleteProducer(String name) {
        try {
            List<Path> filesInFolder = Files.walk(Paths.get(urlSouvenir))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            for (Path input : filesInFolder) {
                refactorFile(input, name);
            }
            File file = new File(urlProducer + name + ".txt");
            file.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}