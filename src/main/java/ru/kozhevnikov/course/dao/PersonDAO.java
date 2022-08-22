package ru.kozhevnikov.course.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kozhevnikov.course.models.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null); // needs to change to orElseThrow 'UnitNotFoundException(description)
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person VALUES(1, ?, ?, ?)", person.getName(), person.getAge(),
                person.getEmail());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id = ?", person.getName(), person.getAge(),
                person.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    // Test batch update

    public void testMultipleUpdate() {
        List<Person> personList = create1000People();

        long before = System.currentTimeMillis();

        for (Person person: personList) {
            jdbcTemplate.update("INSERT INTO Person VALUES(?,?,?,?)", person.getId(), person.getName(),
                    person.getAge(), person.getEmail());
        }

        long after = System.currentTimeMillis();

        System.out.println("TimeWithoutBatchUpdate: " + (after - before));
    }

    public void testBatchUpdate() {
        List<Person> personList = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO Person VALUES(?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, personList.get(i).getId());
                ps.setString(2, personList.get(i).getName());
                ps.setInt(3, personList.get(i).getAge());
                ps.setString(4, personList.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return personList.size();
            }
        });

        long after = System.currentTimeMillis();

        System.out.println("TimeWithBatchUpdate: " + (after - before));
    }

//    public void testBatchDelete() {
//        List<Integer> idList = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            idList.add(i);
//        }
//        jdbcTemplate.batchUpdate("DELETE FROM Person WHERE id=?", new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                ps.setInt(1, idList.get(i));
//            }
//
//            @Override
//            public int getBatchSize() {
//                return idList.size();
//            }
//        });
//    }

    private List<Person> create1000People() {
        List<Person> personList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            personList.add(new Person(i, "Name" + i, 30, "test" + i + "google.com"));
        }
        return personList;
    }
}

/// --------------------------------------------------------------------------------------------------------------- ///

// Бд либо в постоянном хранилище либо in Memory
// Entity (Model) & Repository pattern
// Lambok генерация шаблонного кода
// Service Layer & Reader Layer

// Explanation:
// 1) Annotations (convention, difference example: Autowired to fields/constructor (Spring don't recommend to use it))
// 2) AppServer
// 3) Model (Needs to use to Thymeleaf)

// relevant platform to client part
