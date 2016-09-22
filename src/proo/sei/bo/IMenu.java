package proo.sei.bo;

import java.sql.Connection;
import java.sql.SQLException;

public interface IMenu {
	void menu(Connection conexao) throws SQLException;
}
