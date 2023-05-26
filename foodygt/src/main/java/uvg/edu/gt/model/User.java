
package uvg.edu.gt.model;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NodeEntity(label = "User")
public class User{

    @Id
    @Property(name = "name")
    String name;
    @Property(name = "foodType")
    String foodType;
    @Property(name = "score")
    String Score;
    @Property(name = "ambient")
    String ambient;
    @Property(name = "price")
    String price;


    public User(){

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (name == null) {
            if (other.getName() != null)
                return false;
        } else if (!name.equals(other.getName()))
            return false;
        return true;
    }
}