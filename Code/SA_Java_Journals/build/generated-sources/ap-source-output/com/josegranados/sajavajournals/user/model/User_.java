package com.josegranados.sajavajournals.user.model;

import com.josegranados.sajavajournals.user.model.Profile;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-14T13:27:19")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> userPassword;
    public static volatile SingularAttribute<User, Integer> idUser;
    public static volatile SingularAttribute<User, String> userName;
    public static volatile SingularAttribute<User, String> userRole;
    public static volatile SingularAttribute<User, Profile> profile;

}