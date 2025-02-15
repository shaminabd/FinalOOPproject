package Config.Interfaces;

import java.sql.Connection;


public interface IDB {
    Connection connect();
    void close();
}
