package org.example.JPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.JPA.JPAentities.BaseEntity;
import org.example.JPA.JPAentities.Book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ArrayList;
import java.util.logging.*;

public abstract class AbstractRepository<T extends BaseEntity> implements CrudRepository<T,Integer> {

    private static final Logger LOGGER = Logger.getLogger(AbstractRepository.class.getName());
    protected final EntityManager em = Manager.getInstance().getFactory().createEntityManager();
    private final Class<T> entityClass;

    public AbstractRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public <S extends T> @NonNull S save(@NonNull S entity) {
        em.getTransaction().begin();
        long startTime = System.currentTimeMillis();
        S savedEntity = null;
        try {
            savedEntity = em.merge(entity);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occur", e);
        }
        em.getTransaction().commit();
        long endTime = System.currentTimeMillis();
        LOGGER.log(Level.INFO, "Execution time: " + (endTime - startTime) + "ms");

        return Objects.requireNonNullElse(savedEntity, entity);

    }

    @Override
   public <S extends T> @NonNull Iterable<S> saveAll(@NonNull Iterable<S> entities){
        em.getTransaction().begin();
        long startTime = System.currentTimeMillis();

        for(S entity:entities){
            try {
                em.merge(entity);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Exception occur", e);
            }
        }
        long endTime = System.currentTimeMillis();
        LOGGER.log(Level.INFO, "Execution time: " + (endTime - startTime) + "ms");
        em.getTransaction().commit();
        return entities;
    }

    @Override
   public boolean existsById(@NonNull Integer id) {
        return findById(id).isPresent();
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NonNull Optional<T> findById(@NonNull Integer integer) {
        String className = entityClass.getSimpleName();
        Query query = em.createNamedQuery(className+".findById").setParameter("id",integer);
        return Optional.of((T) query.getSingleResult());
    }

    @SuppressWarnings("unchecked")
    public Optional<T> findByName(String name){
        String className = entityClass.getSimpleName();
        Query query = em.createNamedQuery(className+".findByName").setParameter("name",name);
        return Optional.of((T) query.getResultList().get(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll(){
        String className = entityClass.getSimpleName();
        Query query = em.createNamedQuery(className+".findAll");
        return query.getResultList();
    }

    public @NonNull Iterable<T> findAllById(@NonNull Iterable<Integer> ids){
        return new ArrayList<>();
    }

    public long count() {
        return 0;
    }

    public void deleteById(@NonNull Integer id) {
        em.getTransaction().begin();
        if(findById(id).isEmpty()){
            em.getTransaction().commit();
            return;
        }
        em.remove(findById(id).get());
        em.getTransaction().commit();
    }

    public void delete(@NonNull T entity){
        em.getTransaction().begin();
        T managedEntity = em.merge(entity);

        if (managedEntity instanceof Book book) {
            book.getAuthors().clear(); // Clear the authors list
        }

        em.remove(managedEntity);
        em.getTransaction().commit();
    }
    public void deleteAllById(Iterable<? extends Integer> ids){
        for(Integer id:ids){
            deleteById(id);
        }
    }

    public void deleteAll(Iterable<? extends T> entities){
        for(T entity:entities){
            delete(entity);
        }
    }

    public void deleteAll(){
        em.getTransaction().begin();
        String className = entityClass.getSimpleName();
        Query query = em.createNamedQuery(className+".deleteAll");
        query.executeUpdate();
        em.getTransaction().commit();
    }

}
