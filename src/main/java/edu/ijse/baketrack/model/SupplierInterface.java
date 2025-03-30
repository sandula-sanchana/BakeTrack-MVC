package edu.ijse.baketrack.model;
import edu.ijse.baketrack.dto.SupplierDto;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierInterface {
    void addSupplier(SupplierDto supplierDto) throws SQLException;

    void deleteSupplier(int supplierId) throws SQLException;

    void updateSupplier(int supplierId, SupplierDto supplierDto) throws SQLException;

    void getSupplierById(int supplierId) throws SQLException;

    ArrayList<SupplierDto> getAllSuppliers() throws SQLException;
}
