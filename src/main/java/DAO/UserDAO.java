package DAO;

import DTO.UserDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class UserDAO {
    public int add(UserDTO user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user.getUserID(); // Lấy ID của user vừa thêm
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Lỗi khi thêm
        }
    }

    public int update(UserDTO user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            return 1; // Thành công
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Lỗi khi cập nhật
        }
    }

    public int delete(int userID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            UserDTO user = session.get(UserDTO.class, userID);
            if (user == null) {
                return 0;
            }
            user.setStatus(0);
            session.merge(user);
            transaction.commit();
            return 1; // Thành công
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Lỗi khi xóa
        }
    }

    public UserDTO getUserById(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(UserDTO.class, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserDTO getUserByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM UserDTO u WHERE u.email = :email";
            return session.createQuery(hql, UserDTO.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isUsernameExists(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(u) FROM UserDTO u WHERE u.email = :email";
            Long count = (Long) session.createQuery(hql)
                    .setParameter("email", email)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Mặc định là không tồn tại nếu có lỗi
        }
    }
}
