package com.josegranados.sajavajournals.user.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-14T13:27:19")
@StaticMetamodel(Profile.class)
public class Profile_ { 

    public static volatile SingularAttribute<Profile, String> lastName;
    public static volatile SingularAttribute<Profile, Integer> idProfile;
    public static volatile SingularAttribute<Profile, byte[]> profileImage;
    public static volatile SingularAttribute<Profile, Date> birthdate;
    public static volatile SingularAttribute<Profile, String> about;
    public static volatile SingularAttribute<Profile, String> firstName;

}