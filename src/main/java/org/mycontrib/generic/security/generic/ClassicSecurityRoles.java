package org.mycontrib.generic.security.generic;

public interface ClassicSecurityRoles {
		//liste des rôles génériques prédéfinis (stokés en base):
		//WITH NO ROLE : "ANONYMOUS" !!!!
		public static String GUEST="GUEST";//invité (souvent associé à compte générique)
		public static String USER="USER"; //utilisateur lambda (ex: employé)
		public static String MEMBER="MEMBER"; //membre inscrit / abonne
		public static String ADMIN="ADMIN"; //administrateur (d'une appli, d'un système)
		public static String MANAGER="MANAGER"; //manager (d'un service , de ...)
}

//dans le cas de spring-security ces roles seront par defaut (en interne) préfixés
//par "ROLE_" de façon à pouvoir être testés via hasRole("MEMBER") ou hasRole("ADMIN")
