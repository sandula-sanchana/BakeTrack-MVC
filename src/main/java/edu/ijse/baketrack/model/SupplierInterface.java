package edu.ijse.baketrack.model;
import edu.ijse.baketrack.dto.SupplierDto;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierInterface {
    String addSupplier(SupplierDto supplierDto) throws SQLException;

    String deleteSupplier(int supplierId) throws SQLException;

    String updateSupplier( SupplierDto supplierDto) throws SQLException;

    void getSupplierById(int supplierId) throws SQLException;

    ArrayList<SupplierDto> getAllSuppliers() throws SQLException;

    ArrayList<SupplierDto> getAllSuppliersWIthEmail() throws SQLException;
}
