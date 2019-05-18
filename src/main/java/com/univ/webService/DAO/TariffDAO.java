package com.univ.webService.DAO;

import com.univ.webService.dataConnection.DataConnection;
import com.univ.webService.dataModel.Tariff;
import com.univ.webService.servlet.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TariffDAO {
    public List<Tariff> getTariffFromDB(int idTariff, String nameTariff, int price, int idArea) {

        final String sqlQuery = "SELECT * FROM Tariff WHERE idTariff " + (idTariff == Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + idTariff) +
                " AND nameTariff " + (nameTariff.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= '" + nameTariff + "'") +
                " AND Price " + (price == Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + price) +
                " AND idArea " + (idArea == Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + idArea);

        List<Tariff> getTariffArr = new ArrayList<>();
        Connection connection = DataConnection.getDBConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Tariff tariff = new Tariff(rs.getInt("idTariff"), rs.getString("nameTariff"),
                        rs.getInt("Price"), rs.getInt("idArea"));
                getTariffArr.add(tariff);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getTariffArr;
    }
}
