package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.LinkedList;
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
                "name TEXT NOT NULL, " +
                "lastName TEXT NOT NULL, " +
                "age INT NOT NULL)";
        try {
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.createSQLQuery(sqlCreate);
            tx.commit();
            System.out.println("Таблица создана");
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
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.createSQLQuery("DROP TABLE Users");
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
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.saveOrUpdate(user);
            session.flush();
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
            session = Util.getSessionFactory().openSession();
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
            session = Util.getSessionFactory().openSession();
  //          listUser =(List<User>) session.createQuery("FROM Users").list();
      //      tx = session.beginTransaction();
            listUser = session.createCriteria(User.class).list();
   //         tx.commit();
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
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.createSQLQuery("TRUNCATE Users").executeUpdate();
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
