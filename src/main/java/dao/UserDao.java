package dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.User;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class UserDao implements IBaseDao{
	Logger log=Logger.getLogger(UserDao.class.getName());
	
	@Inject
	Provider<EntityManager> entityManagerProvider;
	
	@Override
	@UnitOfWork
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll() {
		EntityManager em=entityManagerProvider.get();
		List<User> userList=(List<User>) em.createQuery("SELECT x FROM User x").getResultList();
		return (List<T>) userList;
	}

	@Override
	@Transactional
	public <T> boolean delete(T object) {
		EntityManager em=entityManagerProvider.get();
		User user=(User)object;
		user=em.getReference(User.class, user.getId());
		em.remove(user);
		return true;
	}

	@Override
	@Transactional
	public <T> int save(T object) {
		EntityManager em=entityManagerProvider.get();
		em.persist(object);
		return 0;
	}

	@Override
	@Transactional
	public <T> boolean saveOrUpdate(T object) {
		EntityManager em=entityManagerProvider.get();
		em.merge(object);
		return true;
	}

	@UnitOfWork
	public User findUserById(int userId) {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT x FROM User x WHERE x.id = :idParam");
		User user=null;
		try{
			user=(User) q.setParameter("idParam", userId).getSingleResult();
		}catch(Exception e){
			log.warning("User doesn't exists for id : "+userId);
		}
		return user;
	}

}
