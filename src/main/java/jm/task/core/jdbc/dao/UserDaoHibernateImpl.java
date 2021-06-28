package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;
    private Transaction tx;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS Users " +
                "(id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name text NOT NULL, " +
                "lastName text NOT NULL, " +
                "age int not null)";
        try {
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.createSQLQuery(sqlCreate).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Ошибка при создании таблицы");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            tx = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users");
            tx.commit();
            System.out.println("Таблица удалена");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Ошибка при удалении таблицы");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.saveOrUpdate(user);
            tx.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Ошибка при добавлении данных в таблицу");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            tx = session.beginTransaction();
            User user = (User) session.load(User.class, id);
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Ошибка при удалении User по id");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            listUser = (List<User>) session.createQuery("From Users").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("При попытке достать всех пользователей из базы данных произошло исключение");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listUser;
    }

    @Override
    public void cleanUsersTable() {
        try {
            tx = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE Users");
            tx.commit();
            System.out.println("Данные из таблицы были удалены");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Ошибка при уданелении данных из таблицы");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
