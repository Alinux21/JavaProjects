package bonus;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public abstract class GenericDAO<T extends BaseEntity> {
    
    protected static Connection connection;

    public GenericDAO(){
        connection = Database.getConnection();
    }

    public abstract Optional<T> findByName(String name) throws SQLException;
    
    public abstract Optional<T> findById(Integer id) throws SQLException;

    public abstract List<T> findAll() throws SQLException;


}
