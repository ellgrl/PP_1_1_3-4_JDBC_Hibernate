package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, " +
                "lastName VARCHAR(50) NOT NULL, " +
                "age SMALLINT NOT NULL)";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException("Ошибка при удалении таблицы: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException("Ошибка при получении списка пользователей: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users RESTART IDENTITY";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException("Ошибка при очистке таблицы: " + e.getMessage());
        }
    }
}