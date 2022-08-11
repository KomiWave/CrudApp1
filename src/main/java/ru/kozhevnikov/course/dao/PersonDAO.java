package ru.kozhevnikov.course.dao;

import org.springframework.stereotype.Component;
import ru.kozhevnikov.course.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Alex"));
        people.add(new Person(++PEOPLE_COUNT, "Tom"));
        people.add(new Person(++PEOPLE_COUNT, "Harry"));
        people.add(new Person(++PEOPLE_COUNT, "Harry2"));
        people.add(new Person(++PEOPLE_COUNT, "Harry3"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person person) {
//        people.get(id).setName(person.getName());
        Person personToBeUpdate = show(id);
        personToBeUpdate.setName(person.getName());
    }

    public void delete(int id) {
        people.removeIf(p -> p.getId() == id);
    }
}

// Бд либо в постоянном хранилище либо in Memory
// Entity (Model) & Repository pattern
// Lambok генерация шаблонного кода
// Service Layer & Reader Layer

// Explanation:
// 1) Annotations (convention, difference example: Autowired to fields/constructor (Spring don't recommend to use it))
// 2) AppServer
// 3) Model (Needs to use to Thymeleaf)

// relevant platform to client part
