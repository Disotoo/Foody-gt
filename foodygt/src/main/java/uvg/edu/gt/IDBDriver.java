package uvg.edu.gt;

import uvg.edu.gt.model.Restaurant;
import uvg.edu.gt.model.User;

public interface IDBDriver extends AutoCloseable{

        public User getUser(String name);

        public Restaurant getRestaurant(String name);

}