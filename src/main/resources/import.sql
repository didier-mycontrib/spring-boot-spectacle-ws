// ************************** Security Tables ***************

// security_roles (generic/logical):
INSERT INTO security_role(name,description) VALUES ('GUEST','guest with account (not anonymous)');
INSERT INTO security_role(name,description) VALUES ('USER','ordinary user of application (ex: employee)');
INSERT INTO security_role(name,description) VALUES ('MEMBER','member/customer with valid account');
INSERT INTO security_role(name,description) VALUES ('ADMIN','Administrator with lot of privileges');
INSERT INTO security_role(name,description) VALUES ('MANAGER','Manager of part of company / ...');

// domain / OwnerOrg / company :
INSERT INTO security_context(id,context_type,name,parent,infos) VALUES (1,'domain','mycompany',null,null);

// application sub-domain  exemple:(name="myapp" , computed fullName="mycompany/myapp")
INSERT INTO security_context(id,context_type,name,parent,infos) VALUES (2,'domain','myapp',1,null);
INSERT INTO security_context(id,context_type,name,parent,infos) VALUES (3,'domain','otherapp',1,null);

// user_Groups :
// exemple : name="members" , computed fullName="mycompany/myapp/members" pour groupe 12
//                            computed fullName="mycompany/otherapp/members" pour groupe 13
INSERT INTO security_context(id,context_type,name,parent) VALUES (10,'group','employees',2);
INSERT INTO security_context(id,context_type,name,parent) VALUES (11,'group','administrators',2);
INSERT INTO security_context(id,context_type,name,parent) VALUES (12,'group','members',2);
INSERT INTO security_context(id,context_type,name,parent) VALUES (13,'group','members',3);

// join_table security_groups_roles(group_id,role):
INSERT INTO security_groups_roles(group_id,role) VALUES (10,'USER');
INSERT INTO security_groups_roles(group_id,role) VALUES (11,'USER');
INSERT INTO security_groups_roles(group_id,role) VALUES (11,'ADMIN');
INSERT INTO security_groups_roles(group_id,role) VALUES (12,'MEMBER');

// login/accounts:
// exemple : (username="user1" , computed fullName="mycompany/myapp/employees/user1")
//NB: bcrypted_pwd for user1,member1 are crypted from 'pwd1'
//NB: bcrypted_pwd for user2,member2 are crypted from 'pwd2'
//NB: bcrypted_pwd for admin is crypted from 'admin'
//NB: bcrypted_pwd for guest is crypted from 'guest'
INSERT INTO login_account(login_id,username,password,detail) VALUES (1,'user1','$2a$10$ejfNTf/fI8cRXGZpgyRnVeTy1pfS07QLJiTWCtZuzQXQiCWFOF5lG','{"firstName":"alex","lastName":"Therieur","phoneNumber":"0102030405","email":"alex.therieur@gmail.com"}');
INSERT INTO login_account(login_id,username,password,detail) VALUES (2,'user2','$2a$10$k.4yW7.VWEM5EL3ZlLH6Vesvf9ZLNtzCncc2fWtssxJWQ.zWfbE46','{"firstName":"jean","lastName":"Bon","phoneNumber":"0504030201","email":"jean.bon@gmail.com"}');
INSERT INTO login_account(login_id,username,password,detail) VALUES (3,'admin','$2a$10$kQAzRKv/tfenGKOp6ubCr.yoDiHwAqPYQfmiOI6QOEt.GGJC.KTEi','{"firstName":"guy","lastName":"Bol","phoneNumber":"0504030203","email":"guy.bol@gmail.com"}');
INSERT INTO login_account(login_id,username,password,generic,detail) VALUES (4,'guest','$2a$10$YCsItpJExWi3vBsVGbTqmO1nlwBffkMcBKSTxnUkMgbRA7drcQDQm',true,null);
INSERT INTO login_account(login_id,username,password,detail) VALUES (5,'member1','$2a$10$ejfNTf/fI8cRXGZpgyRnVeTy1pfS07QLJiTWCtZuzQXQiCWFOF5lG','{"firstName":"olie","lastName":"Condor","phoneNumber":"0102030405","email":"olie.condor@gmail.com"}');
INSERT INTO login_account(login_id,username,password,detail) VALUES (6,'member2','$2a$10$k.4yW7.VWEM5EL3ZlLH6Vesvf9ZLNtzCncc2fWtssxJWQ.zWfbE46','{"firstName":"axelle","lastName":"Aire","phoneNumber":"0504030201","email":"axelle.aire@gmail.com"}');

// join_table security_contexts_accounts(context_id,account_id):
INSERT INTO security_contexts_accounts(context_id,account_id) VALUES (10,1);
INSERT INTO security_contexts_accounts(context_id,account_id) VALUES (10,2);
INSERT INTO security_contexts_accounts(context_id,account_id) VALUES (11,3);
INSERT INTO security_contexts_accounts(context_id,account_id) VALUES (12,5);
INSERT INTO security_contexts_accounts(context_id,account_id) VALUES (12,6);


// join_table security_accounts_roles(account_id,role)
// FOR_SPECIFIC_ROLES_OF_USER (already roles attached to groups):
INSERT INTO security_accounts_roles(account_id,role) VALUES (2,'MANAGER');
INSERT INTO security_accounts_roles(account_id,role) VALUES (4,'GUEST');

// ************************** Application Tables ***************

INSERT INTO person(id,person_type,first_name , last_name  ,email , phone_number , username , login_id_ref)  VALUES (1,'Customer','alex' , 'Therieur'  , 'alex-therieur@iciOulaBas.fr' , '0102030405' , 'member1' , 5)
INSERT INTO person(id,person_type,first_name , last_name  ,email , phone_number , username , login_id_ref)  VALUES  (2,'Customer','alain' , 'Therieur' , 'alain-therieur@xyz.fr' , '0123456789' , 'member2' , 6)

INSERT INTO person(id,person_type,first_name , last_name  ,email , phone_number , birthday )  VALUES  (3,'Person','p1' , 'NomQuiVaBien' , 'p1@xyz.fr' , '0123456789' , '1995-04-23')
   
INSERT INTO address(id_address_of_person,number, street , zip , town , country ) VALUES (1,'12','rue elle','75001','Paris' , 'France')
INSERT INTO address(id_address_of_person,number, street , zip , town , country ) VALUES (2,'14bis','rue serpentine','69000','Lyon' , 'France')

INSERT INTO category(id,title) VALUES(1,'theatre');
INSERT INTO category(id,title) VALUES(2,'concert');

INSERT INTO spectacle(id,category_id,title,description,duration,nb_places,price) VALUES (1,1,'theatre 1',null,120,200,20.0);
INSERT INTO spectacle(id,category_id,title,description,duration,nb_places,price) VALUES (2,1,'theatre 2',null,90,180,23.0);
INSERT INTO spectacle(id,category_id,title,description,duration,nb_places,price) VALUES (3,2,'concert 1','classique',120,300,22.0);
INSERT INTO spectacle(id,category_id,title,description,duration,nb_places,price) VALUES (4,2,'concert 2','rock',120,500,25.0);

INSERT INTO session(id,spectacle_id,session_date,start_time,nb_remaining_places) VALUES(1,1,'2018-09-26','21:00:00',200);
INSERT INTO session(id,spectacle_id,session_date,start_time,nb_remaining_places) VALUES(2,1,'2018-10-03','21:00:00',200);

INSERT INTO payment(number,details) VALUES(1,'ok via Paypal');
INSERT INTO payment(number,details) VALUES(2,'ok via Bank Xy');

INSERT INTO reservation(id,reservation_date,customer_id,session_id,payment_ref,global_amount) VALUES(1,'2018-09-12',1,1,1,40.0);
INSERT INTO reservation(id,reservation_date,customer_id,session_id,payment_ref,global_amount) VALUES(2,'2018-09-16',2,2,2,25.0);

INSERT INTO reservation_participants(reservation_id,person_id) VALUES(1,2);
INSERT INTO reservation_participants(reservation_id,person_id) VALUES(1,3);
INSERT INTO reservation_participants(reservation_id,person_id) VALUES(2,2);