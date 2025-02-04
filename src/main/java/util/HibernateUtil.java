package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Tạo SessionFactory từ cấu hình Hibernate
            sessionFactory = new Configuration().configure().addAnnotatedClass(DTO.UserDTO.class).buildSessionFactory();
        } catch (Throwable ex) {
            // Nếu có lỗi trong quá trình khởi tạo, in thông báo lỗi
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
