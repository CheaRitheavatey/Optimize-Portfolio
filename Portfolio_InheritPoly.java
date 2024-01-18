import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Portfolio_InheritPoly {
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.setVisible(true);
    }
}

// Base class for Portfolio
class portfolio {
    String name;
    double risk;
    double experience;

    // base constructor

    public portfolio(String newName, double newExperience, double newRisk) {
        name = newName;
        experience = newExperience;
        risk = newRisk;
    }

    public String getName() {
        return name;
    }

    public double getExperience() {
        return experience;
    }

    public double getRisk() {
        return risk;
    }

    public double calculateScore() {
        return experience / risk;
    }
}

// Subclass for optimizing Portfolio
class optimizePortfolio extends portfolio {

    // constructor
    public optimizePortfolio(String newName, double newExperience, double newRisk) {
        super(newName, newExperience, newRisk);
    }
}

// inherit from Jframe
class Ui extends JFrame {
    JTextField nameField;
    JTextField experienceField;
    JTextField riskField;
    JTextField result;
    JLabel nameLabel;
    JLabel experienceLabel;
    JLabel riskLabel;
    JLabel resultLabel;
    JTable table;
    DefaultTableModel tableModel;
    JButton addButton;
    JButton resetButton;
    JButton resultButton;

    Ui() {
        setTitle("Find The Best Portfolio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // give out instruction to user & set bounds & add
        JLabel instructionLabel = new JLabel("Experience & Risk Range: 0-100 ");
        instructionLabel.setBounds(10, 10, 200, 20);
        add(instructionLabel);

        // for the left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(10, 40, 250, 150);

        // setbound for name field
        nameField = new JTextField();
        nameField.setBounds(90, 20, 100, 20);

        // setbound for experience field
        experienceField = new JTextField();
        experienceField.setBounds(90, 50, 100, 20);

        // setbound for riskfield
        riskField = new JTextField();
        riskField.setBounds(90, 80, 100, 20);

        // setbound for name label
        nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(10, 20, 60, 20);

        // setbound for experience label
        experienceLabel = new JLabel("Experience: ");
        experienceLabel.setBounds(10, 50, 80, 20);

        // setbound for risk label
        riskLabel = new JLabel("Risk: ");
        riskLabel.setBounds(10, 80, 60, 20);

        // setbound for addbutton
        addButton = new JButton("Add Portfolio");
        addButton.setBounds(10, 120, 110, 20);
        addButton.addActionListener(new AddButtonListener());

        // setbound for reset button
        resetButton = new JButton("Reset");
        resetButton.setBounds(130, 120, 110, 20);
        resetButton.addActionListener(new ResetButtonListener());

        // add everything into the left panel
        leftPanel.add(nameLabel);
        leftPanel.add(nameField);

        leftPanel.add(experienceLabel);
        leftPanel.add(experienceField);

        leftPanel.add(riskLabel);
        leftPanel.add(riskField);

        leftPanel.add(addButton);
        leftPanel.add(resetButton);

        // for right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(260, 20, 250, 180);

        // create a table to store the portfolios
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Experience");
        tableModel.addColumn("Risk");

        table = new JTable(tableModel);
        table.setEnabled(false);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(10, 10, 200, 120);

        // add table to panel
        rightPanel.add(tableScrollPane);

        // for bottom panel
        JPanel bottomPanel = new JPanel(null);
        bottomPanel.setBounds(10, 200, 410, 60);

        // setbound for result label
        resultLabel = new JLabel("Best Portfolio:");
        resultLabel.setBounds(10, 10, 100, 20);

        // setbound for result button
        resultButton = new JButton("Result");
        resultButton.setBounds(320, 10, 90, 30);
        resultButton.addActionListener(new ResultButtonListener());

        result = new JTextField();
        result.setBounds(110, 10, 200, 30);
        result.setEditable(false);

        bottomPanel.add(result);
        bottomPanel.add(resultButton);
        bottomPanel.add(resultLabel);
        // add everything together
        add(leftPanel);
        add(rightPanel);
        add(bottomPanel);

        setSize(530, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to add a portfolio to the table
    public void addPortfolioToTable(portfolio portfolio) {
        tableModel.addRow(new Object[]{portfolio.getName(), portfolio.getExperience(), portfolio.getRisk()});
    }

    // ActionListener for Add button
    public class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = nameField.getText();
                double experience = Double.parseDouble(experienceField.getText());
                double risk = Double.parseDouble(riskField.getText());

                if (experience < 0 || experience > 100 || risk < 0 || risk > 100) {
                    JOptionPane.showMessageDialog(Ui.this, "Please Enter Valid values for experience and risk (0-100)");
                    return;
                }

                optimizePortfolio portfolio = new optimizePortfolio(name, experience, risk);
                addPortfolioToTable(portfolio);

                // clear input so we can receive new input;
                nameField.setText("");
                experienceField.setText("");
                riskField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Ui.this, "Please enter valid numeric values for experience and risk");
            }
        }
    }

    // ActionListener for Reset button
    public class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tableModel.setRowCount(0);
        }
    }

    // ActionListener for Result button
    public class ResultButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int rowCount = tableModel.getRowCount();
            if (rowCount > 0) {
                double maxScore = 0;
                String bestPortfolioName = "";

                for (int i = 0; i < rowCount; i++) {
                    String name = (String) tableModel.getValueAt(i, 0);
                    double experience = (double) tableModel.getValueAt(i, 1);
                    double risk = (double) tableModel.getValueAt(i, 2);

                    portfolio portfolio = new portfolio(name, experience, risk);
                    double score = portfolio.calculateScore();

                    if (score > maxScore) {
                        maxScore = score;
                        bestPortfolioName = name;
                    }
                }
                result.setText(bestPortfolioName);
            }
        }
    }

}
