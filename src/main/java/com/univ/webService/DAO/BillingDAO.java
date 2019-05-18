package com.univ.webService.DAO;

import com.univ.webService.dataConnection.DataConnection;
import com.univ.webService.dataModel.Billing;
import com.univ.webService.servlet.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {
    public List<Billing> getBillingFromDB(int idBilling, int balance, int chargeAmount, String connectionDate, int idTariff, String status) {

        final String sqlQuery =
                String.format(
                        "SELECT * FROM Billing WHERE idBilling %s AND Balance %s AND chargeAmount %s AND connectionDate %s " +
                                "AND idTariff %s AND Status %s",
                        (idBilling == Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + idBilling),
                        (balance == Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + balance),
                        (chargeAmount == Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + chargeAmount),
                        (connectionDate.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= '" + connectionDate + "'"),
                        (idTariff == Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + idTariff),
                        (status.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= '" + status + "'")
                );
        List<Billing> getBillingArr = new ArrayList<>();
        Connection connection = DataConnection.getDBConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Billing billing = new Billing(rs.getInt("idBilling"), rs.getInt("Balance"), rs.getInt("chargeAmount"),
                        rs.getString("connectionDate"), rs.getInt("idTariff"), rs.getString("Status"));
                getBillingArr.add(billing);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getBillingArr;
    }

    public void updateBillingStatusDB(int idBilling, String status) {
        status = status.equals("disabled") ? "enabled" : "disabled";
        String sqlQuery =
                String.format(
                        "UPDATE Billing SET Status = '%s' WHERE idBilling = '%s'",
                        status,
                        idBilling
                );
        DataConnection.updateDB(sqlQuery);
    }

    public void updateBonusBillingDB(int idBilling, int bonus) {
        String sqlQuery =
                String.format(
                        "UPDATE Billing SET chargeAmount = '%s' WHERE idBilling = '%s'",
                        bonus,
                        idBilling
                );
        DataConnection.updateDB(sqlQuery);
    }

    public void updateBalanceBillingDB(int idBilling, int balance) {
        String sqlQuery =
                String.format(
                        "UPDATE Billing SET Balance = '%s' WHERE idBilling = '%s'",
                        balance,
                        idBilling
                );
        DataConnection.updateDB(sqlQuery);
    }

    public void updateTariffIdBillingDB(int idBilling, int idTariff) {
        String sqlQuery =
                String.format(
                        "UPDATE Billing SET idTariff = '%s' WHERE idBilling = '%s'",
                        idTariff,
                        idBilling
                );
        DataConnection.updateDB(sqlQuery);
    }

    public void updateConnectionDatedBillingDB(int idBilling, String time) {
        String sqlQuery =
                String.format(
                        "UPDATE Billing SET connectionDate = '%s' WHERE idBilling = '%s'",
                        time,
                        idBilling
                );
        DataConnection.updateDB(sqlQuery);
    }
}
