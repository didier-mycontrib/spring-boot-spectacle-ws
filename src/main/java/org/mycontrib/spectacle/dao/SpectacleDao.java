package org.mycontrib.spectacle.dao;

import java.util.List;

import org.mycontrib.spectacle.entity.Spectacle;
import org.springframework.data.repository.CrudRepository;

/*
 * DAO = Data Access Object :
 * objet spécialisé dans l'acces aux données
 * (alias DataService ou Repository ou ...)
 * avec methodes "CRUD" (avec souvent du SQL)
 * 
 * avec throws RuntimeException implicites
 * 
 * JpaRepository herite de CrudRepository (encore plus abstrait)
 * 
 * NB: le code d'une méthode de recherche simple 
 * public List<E> findByXxxAndYyy(xxx,yyy) 
 * peut être généré automatiquement
 * 
 * NB: le code d'une méthode de recherche complexe peut (en JPA)
 * être codé comme un @NameQuery de nom "EntityClass.findXyz"
 */

public interface SpectacleDao extends CrudRepository<Spectacle,Long>{
   /* méthodes héritées:
  	   ... findById(...)
  	   .... findAll()
  	   ...save(...)
  	   ...deleteById(...)
    */
	List<Spectacle> findByCategoryId(Long categoryId);//via NamedQuery

}
