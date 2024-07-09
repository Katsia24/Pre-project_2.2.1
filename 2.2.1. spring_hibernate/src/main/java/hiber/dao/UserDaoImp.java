package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public void addUserWithCar(User user, Car car) {
      user.setCar(car);
      car.setUser(user);
      sessionFactory.getCurrentSession().save(user);
   }


   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsersWithCars() {
      String hql="FROM User user LEFT JOIN FETCH user.car WHERE user.id = user.car.id ";
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery(hql, User.class);
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getUserByCarParam(String model, int series) {
      String hql = "FROM Car car LEFT JOIN FETCH car.user WHERE car.model = :model AND car.series = :series";
      return sessionFactory.getCurrentSession()
              .createQuery(hql, Car.class)
              .setParameter("model", model).setParameter("series", series)
              .setMaxResults(1)
              .getSingleResult().getUser();

//
//         String HQL="FROM Address addr LEFT OUTER JOIN FETCH addr.employee WHERE addr.addressId=:addrId";
//
//
//
//
//         Address address = session.createQuery(HQL, Address.class).setParameter("addrId", 1).uniqueResult();
//         System.out.println(address);
//         System.out.println(address.getEmployee());
//

   }

}
