package org.mycontrib.generic.security.persistence.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* Organisation , ex: entreprise ou service ou ...
 * comportant des utilisateurs (quelquefois regroupés par sous groupes)
 * Ex : dans le cas d'une application Saas (louée par plusieurs entreprises)
 * 
 * Ex: Realm , Organisation (Company , Branch , Service , Application)
 */

@Entity
@DiscriminatorValue("domain")
@Getter @Setter  @NoArgsConstructor
@NamedQueries({
	  @NamedQuery(name="SecurityDomain.findAllRoots",
			      query="SELECT d FROM SecurityDomain d WHERE d.parent IS NULL"),
	  @NamedQuery(name="SecurityDomain.findAllSubDomains",
      			query="SELECT d FROM SecurityDomain d WHERE d.parent.id= :domainId")
	})
public class SecurityDomain extends SecurityCtx{
	//ex: super.name="WorldCompany"
	//isRoot if parent is null lorsque  domaine de type "racine" (ex: Org/company)
	
	private String infos; //infos/détails au format Json ou autre
	@Override
	public String toString() {
		return "SecurityDomain [ infos=" + infos + ", heritant de " + super.toString() + "]";
	}
	
	
}
