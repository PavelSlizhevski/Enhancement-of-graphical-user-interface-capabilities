import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    private List<Boolean> isPrimeList;
    private List<Boolean> isPositiveList;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
        calculateIsPrimeList();
        calculateIsPositiveList();
    }

    private void calculateIsPrimeList() {
        isPrimeList = new ArrayList<>();
        for (int row = 0; row < getRowCount(); row++) {
            double x = from + step*row;
            double value = calculateGornerResult(x);
            isPrimeList.add(isCloseToPrime(value));
        }
    }

    private void calculateIsPositiveList() {
        isPositiveList = new ArrayList<>();
        for (int row = 0; row < getRowCount(); row++) {
            double x = from + step*row;
            double value = calculateGornerResult(x);
            isPositiveList.add(value > 0);
        }
    }

    private double calculateGornerResult(double x) {
        Double gornerResult = 0.0;
        for (int i = 0; i < coefficients.length; i++) {
            gornerResult = gornerResult * x + coefficients[i];
        }
        return gornerResult;
    }

    private boolean isCloseToPrime(double value) {
        int intValue = (int) value;
        if (intValue < 0) {
            intValue = -intValue;
        }
        for (int i = 2; i <= intValue / 2; i++) {
            if (intValue % i == 0) {
                return false;
            }
        }
        return true;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return (int)(Math.ceil((to-from)/step))+1;
    }

    public Object getValueAt(int row, int col) {
        double x = from + step*row;
        switch (col) {
            case 0:
                return x;
            case 1:
                return calculateGornerResult(x);
            case 2:
                return isPositiveList.get(row);
            default:
                return null;
        }
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "Значение X";
            case 1:
                return "Значение многочлена";
            case 2:
                return "Значение больше нуля?";
            default:
                return "";
        }
    }

    public Class<?> getColumnClass(int col) {
        return col == 2 ? Boolean.class : Double.class;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}