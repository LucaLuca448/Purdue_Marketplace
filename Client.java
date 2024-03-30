import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.List;
import java.util.*;

/**
 * GUITest Class
 * <p>
 * Main class for getting customer input, interacts with customer through GUI Handles errors and mismatched input
 * Interacts with the Server to reflect changes
 *
 * @author Justin Kam, Ansh Tandon, Lalitha Chandolu
 * @version December 10, 2023
 */
public class Client {

    private static final String[] BEFORE_LOGIN_OPTIONS = {"Login", "Sign Up", "Exit"};
    private static final String[] SELLER_MENU_OPTIONS = {"View Marketplace", "View All Sales by Store",
                                                         "Add a" + " product", "Edit a product",
                                                         "Delete a " + "product", "View Store stats",
                                                         "Import/Export Products",
                                                         "View " + "customer " + "shopping carts for my " +
                                                             "stores",
                                                         "Log Out"};
    private static final String[] BUYER_MENU_OPTIONS = {"View dashboard", "View Shopping Cart",
                                                        "Search for product", "View Shopping history",
                                                        "Export shopping " + "cart history", "Log Out"};

    /**
     * Returns a String array containing the user-inputted login information in the following order: <p> arr[0];
     * username arr[1]: password
     *
     * @return a String array containing the user input login information,<p> Can also return null if the user
     * clicks on the "cancel" button
     *
     * @author Justin Kam
     */
    public static String[] displayLoginPage(ArrayList<String> data) {
        String[] arr = new String[3];
        JFrame frame = new JFrame();

        JPanel loginPanel = new JPanel(new BorderLayout(5, 5));
        JPanel loginLabels = new JPanel(new GridLayout(0, 1, 2, 2));
        loginLabels.add(new JLabel("Username", SwingConstants.RIGHT));
        loginLabels.add(new JLabel("Password", SwingConstants.RIGHT));
        loginPanel.add(loginLabels, BorderLayout.WEST);

        JPanel loginControls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField userNameField = new JTextField(null, 10);
        loginControls.add(userNameField);
        JPasswordField passwordField = new JPasswordField(null, 10);
        loginControls.add(passwordField);
        loginPanel.add(loginControls, BorderLayout.CENTER);

        String userName = "";
        String password = "";
        String userRole = "";
        do {
            int output = JOptionPane.showConfirmDialog(frame, loginPanel, "login", JOptionPane.OK_CANCEL_OPTION);
            if (output == JOptionPane.CANCEL_OPTION) {
                return null;
            }
            try {
                userName = userNameField.getText();
                password = new String(passwordField.getPassword());
                if (userName.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "One of more fields are empty", "NullPointerException",
                                                  JOptionPane.ERROR_MESSAGE);
                }
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "One of more fields are empty", "NullPointerException",
                                              JOptionPane.ERROR_MESSAGE);
            }
        } while (userName.isEmpty() || password.isEmpty());

        for (String datum : data) {
            String[] temp = datum.split(";");
            if (temp[0].equals(userName) && temp[1].equals(password)) {
                userRole = temp[3];
            }
        }

        arr[0] = userName;
        arr[1] = password;
        arr[2] = userRole;

        if (userRole.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username or Password is Incorrect", "authenticationError",
                                          JOptionPane.ERROR_MESSAGE);
            return null;
        } else {
            return arr;
        }
    }

    /**
     * Returns a String containing all the signup entered information in the following order:<p> arr[0]; name <p>
     * arr[1]; username <p> arr[2]; password <p> arr[3]; userRole (all lowercase, c for a client, s for seller)
     *
     * @return String of what is needed to be appended into users.txt
     *
     * @author Justin Kam
     */
    public static String displaySignupPage(ArrayList<String> data) {
        String[] temp = {"Customer", "Seller"};
        JFrame frame = new JFrame();

        JPanel signupPanel = new JPanel(new BorderLayout(5, 5));
        JPanel signupLabels = new JPanel(new GridLayout(0, 1, 2, 2));
        signupLabels.add(new JLabel("Name", SwingConstants.RIGHT));
        signupLabels.add(new JLabel("Purdue Username", SwingConstants.RIGHT));
        signupLabels.add(new JLabel("Password", SwingConstants.RIGHT));
        signupLabels.add(new JLabel("Customer or Seller?", SwingConstants.RIGHT));
        signupPanel.add(signupLabels, BorderLayout.WEST);

        JPanel signupControls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField nameField = new JTextField(null, 10);
        signupControls.add(nameField);
        JTextField usernameField = new JTextField(null, 10);
        signupControls.add(usernameField);
        JTextField passwordField = new JTextField(null, 10);
        signupControls.add(passwordField);
        JComboBox<String> userRoleBox = new JComboBox<>(temp);
        signupControls.add(userRoleBox);
        signupPanel.add(signupControls, BorderLayout.CENTER);

        String name = "";
        String username = "";
        String password = "";
        String userRole = "";
        do {
            int output = JOptionPane.showConfirmDialog(frame, signupPanel, "SignUp", JOptionPane.OK_CANCEL_OPTION);
            if (output == JOptionPane.CANCEL_OPTION) {
                return null;
            }
            try {
                name = nameField.getText();
                username = usernameField.getText();
                password = passwordField.getText();
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "One of more fields are empty, returning to previous screen",
                                              "NullPointerException", JOptionPane.ERROR_MESSAGE);
            }

            if (userRoleBox.getSelectedIndex() == 0) {
                userRole = "c";
            } else if (userRoleBox.getSelectedIndex() == 1) {
                userRole = "s";
            }
        } while (name.isEmpty() || username.isEmpty() || password.isEmpty() || userRole.isEmpty());

        //String accExists = accountExists(username, password);
        for (String datum : data) {
            String[] processData = datum.split(";");
            if (processData[0].equals(username)) {
                JOptionPane.showMessageDialog(null, "Username taken", "accExists", JOptionPane.ERROR_MESSAGE);
                return null;
            } else if (processData[1].equals(password)) {
                JOptionPane.showMessageDialog(null, "Password taken, must use unique password", "accExists",
                                              JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        //Account doesn't exist

        JOptionPane.showMessageDialog(null, "Success! New User Added", "Signup Success",
                                      JOptionPane.INFORMATION_MESSAGE);

        return username + ";" + password + ";" + name + ";" + userRole;

    }

    /**
     * Displays a GUI with a table that shows the information of products. When using, parse in an ArrayList with
     * the products that you want to display
     *
     * @param data An ArrayList containing Strings for the elements in the marketplace, formatted same way as
     *             market.txt would be formatted as follows seperated by semicolons<p>
     *             ProductName;UnitPrice;StoreName;Quantity;ProductDescription;MerchantName
     *
     * @author Justin Kam
     */
    public static void displayGUIMarketplace(ArrayList<String> data) {
        JFrame frame = new JFrame();
        JPanel marketPlace = new JPanel(new BorderLayout(5, 5));

        String[] columnNames = {"Product Name", "Unit Price", "Store Name", "Quantity", "Product Description",
                                "Seller Name"};

        // this method is not properly reading the market.txt file as it is being updated
        String[][] tempe = new String[data.size()][6];
        for (int i = 0; i < tempe.length; i++) {
            String[] a = data.get(i).split(";");
            System.arraycopy(a, 0, tempe[i], 0, tempe[0].length);
        }
        //implemented JTable
        JTable table = new JTable(tempe, columnNames);

        DefaultTableModel tableModel = new DefaultTableModel(tempe, columnNames) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(600);
        columnModel.getColumn(5).setPreferredWidth(100);

        table.setColumnModel(columnModel);

        marketPlace.add(scrollPane, BorderLayout.CENTER);

        UIManager.put("OptionPane.minimumSize", new Dimension(1200, 500));
        UIManager.put("OptionPane.setResizable", true);

        JOptionPane.showMessageDialog(frame, marketPlace, "Display Marketplace",
                                      JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Displays a GUI that prompts user to choose how they want the products sorted, then calls
     * displayGUIMarketplace(ArrayList<String> data) to show the gui<p> 1 Dependency on
     * displayGUIMarketplace(ArrayList<String> data) <p> Exits out of the method if "cancel" button is pressed
     *
     * @param data An ArrayList containing Strings for the elements in the marketplace, formatted same way as
     *             market.txt would be formatted as follows seperated by semicolons<p>
     *             ProductName;UnitPrice;StoreName;Quantity;ProductDescription;MerchantName
     *
     * @Author Justin Kam
     */
    public static void customerMarketplace(ArrayList<String> data, String type) {
        String[] sortChoices = {"None", "Ascending Price", "Descending Price", "Ascending Quantity",
                                "Descending " + "Quantity"};
        String sortChoice;
        if (type.equalsIgnoreCase("seller")) {
            sortChoice = (String) JOptionPane.showInputDialog(null, "Sort Marketplace by: ",
                                                              "SellerMarketplace", JOptionPane.INFORMATION_MESSAGE,
                                                              null, sortChoices, sortChoices[0]);
        } else {
            sortChoice = (String) JOptionPane.showInputDialog(null, "Sort Marketplace by: ",
                                                              "CustomerMarketplace",
                                                              JOptionPane.INFORMATION_MESSAGE, null, sortChoices,
                                                              sortChoices[0]);
        }

        if (sortChoice != null) {
            switch (sortChoice) {
                case "None" -> displayGUIMarketplace(data);
                case "Ascending Price" -> {
                    double[] productPrices = new double[data.size()];
                    HashMap<String, ArrayList<String>> productPriceMap = new HashMap<>();

                    for (int i = 0; i < productPrices.length; i++) {
                        String[] temp = data.get(i).split(";");
                        if (productPriceMap.get(temp[1]) != null) {
                            ArrayList<String> list = productPriceMap.get(temp[1]);
                            list.add(temp[0]);
                            productPriceMap.put(temp[1], list);
                        } else {
                            ArrayList<String> list = new ArrayList<>();
                            list.add(temp[0]);
                            productPriceMap.put(temp[1], list);
                        }
                        productPrices[i] = Double.parseDouble(temp[1]);
                    }

                    Arrays.sort(productPrices);

                    String[] productNames = new String[data.size()];

                    for (int i = 0; i < productPrices.length; i++) {
                        String productPriceData = String.format("%.2f", productPrices[i]);
                        ArrayList<String> list = productPriceMap.get(productPriceData);
                        if (list.size() == 1) {
                            productNames[i] = list.get(0);
                            productPriceMap.remove(productPriceData);
                        } else {
                            i--;
                            for (String s : list) {
                                i++;
                                productNames[i] = s;
                            }
                            productPriceMap.remove(productPriceData);
                        }
                    }

                    ArrayList<String> finalData = new ArrayList<>();

                    for (int i = 0; i < data.size(); i++) {
                        for (String datum : data) {
                            if (datum.contains(productNames[i]) && datum.contains(
                                Double.toString(productPrices[i]))) {
                                finalData.add(datum);
                            }
                        }
                    }

                    displayGUIMarketplace(finalData);

                }
                case "Descending Price" -> {
                    Double[] productPrices = new Double[data.size()];
                    HashMap<String, ArrayList<String>> productPriceMap = new HashMap<>();

                    for (int i = 0; i < productPrices.length; i++) {
                        String[] temp = data.get(i).split(";");
                        if (productPriceMap.get(temp[1]) != null) {
                            ArrayList<String> list = productPriceMap.get(temp[1]);
                            list.add(temp[0]);
                            productPriceMap.put(temp[1], list);
                        } else {
                            ArrayList<String> list = new ArrayList<>();
                            list.add(temp[0]);
                            productPriceMap.put(temp[1], list);
                        }
                        productPrices[i] = Double.valueOf(temp[1]);
                    }

                    Arrays.sort(productPrices, Collections.reverseOrder());

                    String[] productNames = new String[data.size()];

                    for (int i = 0; i < productPrices.length; i++) {
                        String productPriceData = String.format("%.2f", productPrices[i]);
                        ArrayList<String> list = productPriceMap.get(productPriceData);
                        if (list.size() == 1) {
                            productNames[i] = list.get(0);
                            productPriceMap.remove(productPriceData);
                        } else {
                            i--;
                            for (String s : list) {
                                i++;
                                productNames[i] = s;
                            }
                            productPriceMap.remove(productPriceData);
                        }
                    }

                    ArrayList<String> finalData = new ArrayList<>();

                    for (int i = 0; i < data.size(); i++) {
                        for (String datum : data) {
                            if (datum.contains(productNames[i]) && datum.contains(
                                Double.toString(productPrices[i]))) {
                                finalData.add(datum);
                            }
                        }
                    }

                    displayGUIMarketplace(finalData);

                }
                case "Ascending Quantity" -> {
                    int[] productQuantities = new int[data.size()];
                    HashMap<String, ArrayList<String>> productQuantityMap = new HashMap<>();

                    for (int i = 0; i < productQuantities.length; i++) {
                        String[] temp = data.get(i).split(";");
                        if (productQuantityMap.get(temp[3]) != null) {
                            ArrayList<String> list = productQuantityMap.get(temp[3]);
                            list.add(temp[0]);
                            productQuantityMap.put(temp[3], list);
                        } else {
                            ArrayList<String> list = new ArrayList<>();
                            list.add(temp[0]);
                            productQuantityMap.put(temp[3], list);
                        }
                        productQuantities[i] = Integer.parseInt(temp[3]);
                    }

                    Arrays.sort(productQuantities);

                    String[] productNames = new String[data.size()];

                    for (int i = 0; i < productQuantities.length; i++) {
                        String productQuantityData = Integer.toString(productQuantities[i]);
                        ArrayList<String> list = productQuantityMap.get(productQuantityData);
                        if (list.size() == 1) {
                            productNames[i] = list.get(0);
                            productQuantityMap.remove(productQuantityData);
                        } else {
                            i--;
                            for (String s : list) {
                                i++;
                                productNames[i] = s;
                            }
                            productQuantityMap.remove(productQuantityData);
                        }
                    }

                    ArrayList<String> finalData = new ArrayList<>();

                    for (int i = 0; i < data.size(); i++) {
                        for (String datum : data) {
                            if (datum.contains(productNames[i]) && datum.contains(
                                Integer.toString(productQuantities[i]))) {
                                finalData.add(datum);
                            }
                        }
                    }

                    displayGUIMarketplace(finalData);

                }
                case "Descending Quantity" -> {
                    Integer[] productQuantities = new Integer[data.size()];
                    HashMap<String, ArrayList<String>> productQuantityMap = new HashMap<>();

                    for (int i = 0; i < productQuantities.length; i++) {
                        String[] temp = data.get(i).split(";");
                        if (productQuantityMap.get(temp[3]) != null) {
                            ArrayList<String> list = productQuantityMap.get(temp[3]);
                            list.add(temp[0]);
                            productQuantityMap.put(temp[3], list);
                        } else {
                            ArrayList<String> list = new ArrayList<>();
                            list.add(temp[0]);
                            productQuantityMap.put(temp[3], list);
                        }
                        productQuantities[i] = Integer.valueOf(temp[3]);
                    }

                    Arrays.sort(productQuantities, Collections.reverseOrder());

                    String[] productNames = new String[data.size()];

                    for (int i = 0; i < productQuantities.length; i++) {
                        String productQuantityData = Integer.toString(productQuantities[i]);
                        ArrayList<String> list = productQuantityMap.get(productQuantityData);
                        if (list.size() == 1) {
                            productNames[i] = list.get(0);
                            productQuantityMap.remove(productQuantityData);
                        } else {
                            i--;
                            for (String s : list) {
                                i++;
                                productNames[i] = s;
                            }
                            productQuantityMap.remove(productQuantityData);
                        }
                    }

                    ArrayList<String> finalData = new ArrayList<>();

                    for (int i = 0; i < data.size(); i++) {
                        for (String datum : data) {
                            if (datum.contains(productNames[i]) && datum.contains(
                                Integer.toString(productQuantities[i]))) {
                                finalData.add(datum);
                            }
                        }
                    }

                    displayGUIMarketplace(finalData);
                }
            }
        }
    }

    /**
     * Displays a GUI that will sort through all the purchases that have been made and will display them by store
     * This method will automatically end if there are no sales and go back to previous JPanel Exits out of the
     * method if "cancel" button is pressed viewSalesByStore
     *
     * @param temp     all the data received from the server from the purchases.txt file
     * @param username Seller username
     *
     * @author Ansh Tandon
     */
    public static void viewAllSalesByStore(ArrayList<String> temp, String username) {

        ArrayList<String[]> list = new ArrayList<>();
        for (String s : temp) {
            String[] data = s.split(";");
            if (data[5].equals(username)) {
                list.add(data);
            }
        }
        list.sort(Comparator.comparing(o -> ((o[2]))));
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Recorded Sales!", "SellerError", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //add products that this seller owns
        //don't create str builder, instead put it in the JPanel
        StringBuilder str = new StringBuilder("List of Stores and all transactions\n");
        String store = list.get(0)[2];
        str.append("\nStore: ").append(store).append("\n");
        double total = 0;
        for (String[] productLine : list) {
            if (!productLine[2].equals(store)) {
                str.append(String.format("Store Total Revenue: $%.2f\n", total));
                total = 0;
                store = productLine[2];
                str.append("\nStore: ").append(store).append("\n");
            }
            str.append(productLine[4]).append(" purchased ").append(productLine[3]).append(" ").append(
                    productLine[0]).append("(s) from ").append(productLine[2]).append(" for $").append(productLine[1])
                .append(String.format(" for a " + "total of $%.2f.\n",
                                      Double.parseDouble(productLine[3]) * Double.parseDouble(productLine[1])));
            total += Double.parseDouble(productLine[3]) * Double.parseDouble(productLine[1]);
        }
        str.append(String.format("Store Total Revenue: $%.2f\n", total));

        JOptionPane.showMessageDialog(null, str, "SellerMarketplace", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method will add a product with the following parameters, and if seller can only add if product doesn't
     * exist
     *
     * @param temp     all the file information being sent from server
     * @param username sellerUserName field to be parsed in
     *
     * @return A string representation of the newly added product that can be put into the market.txt file
     *
     * @author Justin Kam, Lalitha Chandolu
     */

    public static String addProduct(ArrayList<String> temp, String username) {

        JFrame frame = new JFrame();
        JPanel addProductPanel = new JPanel(new BorderLayout(5, 5));

        JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
        labels.add(new JLabel("Product Name", SwingConstants.RIGHT));
        labels.add(new JLabel("Price", SwingConstants.RIGHT));
        labels.add(new JLabel("Store Name", SwingConstants.RIGHT));
        labels.add(new JLabel("Quantity", SwingConstants.RIGHT));
        labels.add(new JLabel("Description"), SwingConstants.RIGHT);
        addProductPanel.add(labels, BorderLayout.WEST);

        JPanel addProductControls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField productNameField = new JTextField(null, 10);
        addProductControls.add(productNameField);

        SpinnerModel model = new SpinnerNumberModel(0.00, 0, Integer.MAX_VALUE, 0.01);
        JSpinner productPriceSpinner = new JSpinner(model);
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) productPriceSpinner.getEditor();
        JFormattedTextField spinnerFormat = ((JSpinner.NumberEditor) productPriceSpinner.getEditor()).getTextField();
        ((NumberFormatter) spinnerFormat.getFormatter()).setAllowsInvalid(false);
        DecimalFormat format = editor.getFormat();
        format.setMinimumFractionDigits(2);
        editor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);
        addProductControls.add(productPriceSpinner);

        JTextField storeNameField = new JTextField(null, 10);
        addProductControls.add(storeNameField);

        SpinnerModel model2 = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner quantitySpinner = new JSpinner(model2);
        JFormattedTextField spinnerFormat2 = ((JSpinner.NumberEditor) quantitySpinner.getEditor()).getTextField();
        ((NumberFormatter) spinnerFormat2.getFormatter()).setAllowsInvalid(false);
        addProductControls.add(quantitySpinner);

        JTextField descriptionField = new JTextField(null, 10);
        addProductControls.add(descriptionField);

        addProductPanel.add(addProductControls);

        String prodName = "";
        double prodPrice = 0;
        String storeNames = "";
        int prodQuantity = 0;
        String prodDesc = "";
        do {
            int output = JOptionPane.showConfirmDialog(frame, addProductPanel, "add to cart",
                                                       JOptionPane.OK_CANCEL_OPTION);
            if (output == JOptionPane.CANCEL_OPTION) {
                return null;
            }
            try {
                prodName = productNameField.getText();
                prodPrice = (Double) productPriceSpinner.getValue();
                // gets the storeName that the seller wants to add the product to
                storeNames = storeNameField.getText();
                prodQuantity = (Integer) quantitySpinner.getValue();
                prodDesc = descriptionField.getText();
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "One of more fields are empty, try again",
                                              "NullPointerException", JOptionPane.ERROR_MESSAGE);
            }
        } while (prodName.isEmpty() || prodPrice == 0 || storeNames.isEmpty() || prodQuantity == 0 ||
            prodDesc.isEmpty());

        ArrayList<String[]> list = new ArrayList<>();
        for (String s : temp) {
            String[] data = s.split(";");
            list.add(data);
        }
        ArrayList<String[]> sellerLines = getSellerStores(username, list);
        boolean validStore = isValidStore(sellerLines, storeNames, list);

        if (!validStore) {
            JOptionPane.showMessageDialog(null, "Can't add a product to a store that's not yours!", "SellerError",
                                          JOptionPane.ERROR_MESSAGE);
            return null; //returns null as default
        } else {
            // parses through the current market list - verifying the product doesn't exist already
            for (String[] products : list) {
                if (products[0].equals(prodName) && (products[2].equals(storeNames))) {
                    JOptionPane.showMessageDialog(null, "Product Already Exists!", "SellerError",
                                                  JOptionPane.ERROR_MESSAGE);
                    return null; //returns null as default
                }
            }
        }
        String newProduct = String.format("%s;%.2f;%s;%d;%s;%s\n", prodName, prodPrice, storeNames, prodQuantity,
                                          prodDesc, username);
        JOptionPane.showMessageDialog(null, String.format("Product was added successfully to %s.", storeNames),
                                      "SellerMarketplace", JOptionPane.INFORMATION_MESSAGE);
        return newProduct;
    }

    private static boolean isValidStore(ArrayList<String[]> sellerLines, String storeNames,
                                        ArrayList<String[]> list) {
        ArrayList<String> sellerStoreNames = new ArrayList<>();
        for (String[] line : sellerLines) {
            sellerStoreNames.add(line[2]);
        }

        boolean validStore = sellerStoreNames.contains(storeNames);
        ArrayList<String> marketStoreNames = new ArrayList<>();
        for (String[] line : list) {
            marketStoreNames.add(line[2]);
        }

        if (!validStore) {
            // see if market.txt doesn't already have the store
            validStore = !marketStoreNames.contains(storeNames);
        }
        return validStore;
    }

    /**
     * Method returns an array list of the stores that a seller owns given their username
     *
     * @param list of lines in market.java
     *
     * @return Array list of lines in the market that pertain to the seller
     *
     * @author Lalitha Chandolu
     */
    public static ArrayList<String[]> getSellerStores(String sellerUsername, ArrayList<String[]> list) {
        ArrayList<String[]> storeList = new ArrayList<>();
        ArrayList<String> storeNames = new ArrayList<>();
        for (String[] line : list) {
            storeNames.add(line[2]);
        }
        for (String[] line : list) {
            if (line[5].equals(sellerUsername)) {
                if (storeNames.contains(line[2])) {
                    storeList.add(line);
                }
            }
        }
        return storeList;
    }

    /**
     * This method enables the user to remove a product from their shopping cart given a certain product name and
     * store name Utilizes GUI to ensure functionality for the application.
     *
     * @param temp        contents of the shoppingCart.txt file
     * @param productName name of product to be removed
     * @param storeName   name of store product to be removed from
     *
     * @author Ansh Tandon
     */

    public static ArrayList<String> removeProduct(ArrayList<String> temp, String username, String productName,
                                                  String storeName) {
        ArrayList<String[]> marketplaceList = new ArrayList<>();
        for (String s : temp) {
            String[] data = s.split(";");
            marketplaceList.add(data);
        }
        boolean flag = false;
        try {
            for (int i = 0; i < marketplaceList.size(); i++) {
                if ((marketplaceList.get(i)[0].equals(productName)) && (marketplaceList.get(i)[2].equals(
                    storeName)) && (marketplaceList.get(i)[5].equals(username))) {
                    marketplaceList.remove(i);
                    JOptionPane.showMessageDialog(null, "Product Removed.", "SellerMarketplace",
                                                  JOptionPane.INFORMATION_MESSAGE);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                JOptionPane.showMessageDialog(null, "Product doesn't exist in your store, cannot be removed.",
                                              "SellerMarketplace", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error occurred while processing, please try again!",
                                          "SellerError", JOptionPane.ERROR_MESSAGE);
        }
        ArrayList<String> returningList = new ArrayList<>();
        for (String[] strings : marketplaceList) {
            returningList.add(String.join(";", strings));
        }
        return returningList;
    }

    /**
     * This method goes through and displays all the store statistics Sellers can see through their dashboard
     *
     * @param temp       all the data that is passed from server from the purchases.txt file
     * @param username   seller username
     * @param sortChoice sort by which type
     *
     * @author Ansh Tandon, Lalitha Chandolu, Justin Kam
     */
    public static void viewStoreStatistics(ArrayList<String> temp, String username, int sortChoice) {
        ArrayList<String> purchasesInSellerStores = new ArrayList<>();
        for (String s : temp) {
            String[] data = s.split(";");
            if (data[5].equals(username)) {
                purchasesInSellerStores.add(s);
            }
        }
        StringBuilder storeStatistics = new StringBuilder();
        if (sortChoice == 1) {
            HashMap<String, Integer> customerPurchases = new HashMap<>();
            for (String purchase : purchasesInSellerStores) {
                String[] purchaseInfo = purchase.split(";");
                if (customerPurchases.containsKey(purchaseInfo[4])) {
                    int quantity = customerPurchases.get(purchaseInfo[4]);
                    customerPurchases.put(purchaseInfo[4], quantity + Integer.parseInt(purchaseInfo[3]));
                } else {
                    customerPurchases.put(purchaseInfo[4], Integer.parseInt(purchaseInfo[3]));
                }
            }
            storeStatistics.append("Sorted by List of Customers and their purchases:\n ");
            for (String customer : customerPurchases.keySet()) {
                storeStatistics.append(String.format("%s has purchased %d items from your stores.\n", customer,
                                                     customerPurchases.get(customer)));
            }
        } else { // will sort by products sold
            HashMap<String, Integer> productsPurchased = new HashMap<>();
            for (String purchase : purchasesInSellerStores) {
                String[] purchaseInfo = purchase.split(";");
                if (productsPurchased.containsKey(purchaseInfo[0])) {
                    int quantitySold = productsPurchased.get(purchaseInfo[0]);
                    productsPurchased.put(purchaseInfo[0], quantitySold + Integer.parseInt(purchaseInfo[3]));
                } else {
                    productsPurchased.put(purchaseInfo[0], Integer.parseInt(purchaseInfo[3]));
                }
            }

            List<Map.Entry<String, Integer>> list = new LinkedList<>(productsPurchased.entrySet());

            list.sort(Map.Entry.comparingByValue());

            Map<String, Integer> sortedMap = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : list) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }

            storeStatistics.append("Sorted by List of Products Purchased with number of Sales:\n ");
            for (String product : sortedMap.keySet()) {
                storeStatistics.append(
                    String.format("%s has been purchased a total of %d times from your stores.\n", product,
                                  productsPurchased.get(product)));
            }
        }
        JOptionPane.showMessageDialog(null, storeStatistics.toString(), "SellerMarketplace",
                                      JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Creates a GUI to display customer cart information in a table, also prompts the customer for the next action
     * (add, remove or buy cart items)
     *
     * @param data     an ArrayList containing Strings in the format as follows: <p>
     *                 buyerName;buyerPassword;productName;unitPrice;storeName;quantityInCart;productDescription;
     *                 sellerName
     * @param username username of the buyer
     * @param password password of the buyer
     *
     * @author Justin Kam
     */
    public static String displayCart(ArrayList<String> data, String username, String password) {
        ArrayList<String> parsedData = getStrings(data, username, password);

        JFrame frame = new JFrame();
        JPanel marketPlace = new JPanel(new BorderLayout(5, 5));

        String[] columnNames = {"Product Name", "Unit Price", "Store Name", "Quantity", "Product Description",
                                "Seller Name"};

        String[][] tempe = new String[parsedData.size()][6];
        for (int i = 0; i < tempe.length; i++) {
            String[] a = parsedData.get(i).split(";");
            System.arraycopy(a, 0, tempe[i], 0, tempe[0].length);
        }
        JTable table = new JTable(tempe, columnNames);

        DefaultTableModel tableModel = new DefaultTableModel(tempe, columnNames) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(600);
        columnModel.getColumn(5).setPreferredWidth(100);

        table.setColumnModel(columnModel);

        marketPlace.add(scrollPane, BorderLayout.CENTER);

        UIManager.put("OptionPane.minimumSize", new Dimension(1200, 500));
        UIManager.put("OptionPane.setResizable", true);

        JOptionPane.showMessageDialog(frame, marketPlace, "Display Customer Cart",
                                      JOptionPane.INFORMATION_MESSAGE);

        UIManager.put("OptionPane.minimumSize", new Dimension(200, 200));

        final String[] cartChoices = {"Add product to cart", "Remove product from cart", "Modify Cart",
                                      "Checkout " + "cart", "Cancel"};

        String cartChoice;
        boolean redirect = false;
        do {
            cartChoice = (String) JOptionPane.showInputDialog(null, "Select an Option to do with cart",
                                                              "cart options", JOptionPane.QUESTION_MESSAGE, null,
                                                              cartChoices, cartChoices[0]);
            if (cartChoice != null) {
                break;
            }
        } while (cartChoice == null || cartChoice.isEmpty() || !redirect);

        return cartChoice;
    }

    /**
     * This method will return an exported list that will represent the file needed to be sent back to the server
     *
     * @param marketList   ArrayList of things from market.txt
     * @param merchantName Seller username
     * @param storeName    name of the Store that the Seller wants to export info from
     *
     * @return False if user cancels the action, true if file was exported (file could be empty)
     *
     * @author Justin Kam
     */
    public static Boolean exportStoreInformation(ArrayList<String> marketList, String merchantName,
                                                 String storeName) {

        JFrame frame = new JFrame();

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        panel.add(fc);

        File file;

        do {
            int choice = JOptionPane.showConfirmDialog(frame, panel, "ChooseFile", JOptionPane.OK_CANCEL_OPTION);
            if (choice == JOptionPane.CANCEL_OPTION) {
                return false;
            }
            file = fc.getSelectedFile();
        } while (file == null);

        String dirPath = file.getAbsolutePath();

        //System.out.println(filePath);

        File f = new File(dirPath + "/StoreInformationExport.csv");
        try {
            f.createNewFile();
            FileWriter fw = new FileWriter(f, false);
            fw.write("Product Name, Product Price, Store Name, Quantity in Stock, Product Description \n");
            for (String s : marketList) {
                String[] data = s.split(";");
                if (data[2].equals(storeName) && data[5].equals(merchantName)) {
                    fw.write(data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "\n");
                }
            }
            fw.close();
        } catch (IOException ignored) {
            boolean error = true;
        }

        return true;
    }

    /**
     * Function to add a product to cart. Prompts user to enter product info, checks if it exists, then add to cart
     * if it exists
     *
     * @param market       ArrayList every element is a line from Market.txt
     * @param shoppingCart ArrayList every element is a line from shoppingCart.txt
     * @param userName     username of the user
     * @param password     password of the user
     *
     * @return ArrayList of lines to be replaced into shoppingCart.txt, null if product doesn't exist or adding
     * more than available stock of items
     *
     * @author Justin Kam, Lalitha Chandolu
     */
    private static ArrayList<String> addToCart(ArrayList<String> market, ArrayList<String> shoppingCart,
                                               String userName, String password) {
        ArrayList<String> toReturn = new ArrayList<>();

        JFrame frame = new JFrame();
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));

        JPanel inputLabels = new JPanel(new GridLayout(0, 1, 2, 2));
        inputLabels.add(new JLabel("Product Name", SwingConstants.RIGHT));
        inputLabels.add(new JLabel("Quantity Desired", SwingConstants.RIGHT));
        inputLabels.add(new JLabel("Store name", SwingConstants.RIGHT));
        inputPanel.add(inputLabels, BorderLayout.WEST);

        JPanel cartControls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField productNameField = new JTextField(null, 15);
        cartControls.add(productNameField);

        SpinnerModel model = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner productQuantitySpinner = new JSpinner(model);
        JFormattedTextField spinnerFormat = ((JSpinner.NumberEditor) productQuantitySpinner.getEditor())
            .getTextField();
        ((NumberFormatter) spinnerFormat.getFormatter()).setAllowsInvalid(false);
        cartControls.add(productQuantitySpinner);

        JTextField storeNameField = new JTextField(null, 15);
        cartControls.add(storeNameField);
        inputPanel.add(cartControls, BorderLayout.CENTER);

        String productName = "";
        int quantity = 0;
        String storeName = "";
        do {
            int output = JOptionPane.showConfirmDialog(frame, inputPanel, "add to cart",
                                                       JOptionPane.OK_CANCEL_OPTION);
            if (output == JOptionPane.CANCEL_OPTION) {
                return null;
            }
            try {
                productName = productNameField.getText();
                productQuantitySpinner.commitEdit();
                quantity = (Integer) productQuantitySpinner.getValue();
                storeName = storeNameField.getText();
            } catch (java.text.ParseException e) {
                //unlucky
                JOptionPane.showMessageDialog(null, "Error", "Parse Exception", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "One of more fields are empty", "NullPointerException",
                                              JOptionPane.ERROR_MESSAGE);
                productName = "";
                quantity = 0;
                storeName = "";
            }

            if (quantity == 0) {
                JOptionPane.showMessageDialog(null, "Cannot add 0 of a product", "0 exception",
                                              JOptionPane.ERROR_MESSAGE);
            }

        } while (productName.isEmpty() || quantity <= 0 || storeName.isEmpty());

        boolean exists = false;

        String[] productToAdd = null;

        for (String s : market) {
            String[] marketData = s.split(";");
            if (marketData[0].equals(productName) && marketData[2].equals(storeName)) {
                productToAdd = marketData;
            }
        }

        if (productToAdd == null) {
            JOptionPane.showMessageDialog(null, "Nonexistent product or store", "ProductNotFound",
                                          JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (quantity > Integer.parseInt(productToAdd[3])) {
            JOptionPane.showMessageDialog(null, "Not enough stock", "StoreUnderflow", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        for (String i : shoppingCart) {
            String[] cartData = i.split(";");
            if (cartData[2].equals(productName) && cartData[4].equals(storeName) && cartData[0].equals(userName)) {
                JOptionPane.showMessageDialog(null, "Product already in cart, please use modify cart function",
                                              "ProductExistsError", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        ArrayList<String[]> oldShoppingCart = new ArrayList<>();

        for (String s : shoppingCart) {
            String[] cardData = s.split(";");
            oldShoppingCart.add(cardData);
        }

        ArrayList<String[]> newShoppingCart = new ArrayList<>();

        for (String[] cart : oldShoppingCart) {
            if (cart[0].equals(userName) && cart[2].equals(productName) && cart[4].equals(storeName)) {
                cart[5] += quantity;
                exists = true;
            }
            newShoppingCart.add(cart);
        }

        if (!exists) {
            String[] newProduct = new String[8];
            newProduct[0] = userName;
            newProduct[1] = password;
            newProduct[2] = productToAdd[0];
            newProduct[3] = productToAdd[1];
            newProduct[4] = productToAdd[2];
            newProduct[5] = quantity + "";
            newProduct[6] = productToAdd[4];
            newProduct[7] = productToAdd[5];
            newShoppingCart.add(newProduct);
        }

        for (String[] product : newShoppingCart) {
            toReturn.add(String.join(";", product));
        }

        JOptionPane.showMessageDialog(null, "Product Successfully added to cart", "Success",
                                      JOptionPane.INFORMATION_MESSAGE);

        return toReturn;
    }

    /**
     * Method that displays a GUI JTable for the user to edit the cells to edit the products which are then put
     * into market.txt
     *
     * @param temp       ArrayList of stuff from the market.txt file
     * @param sellerName username of the seller
     *
     * @return ArrayList of things to replace into market.txt
     *
     * @author Ansh Tandon and Justin Kam
     */
    public static ArrayList<String> editProductJTable(ArrayList<String> temp, String sellerName) {
        JOptionPane.showMessageDialog(null, "Please just double click on each table cell to edit information",
                                      "SellerMarketplace", JOptionPane.WARNING_MESSAGE);

        ArrayList<String> toReturn = new ArrayList<>(); //adds things not to be edited
        for (String i : temp) {
            String[] marketData = i.split(";");
            if (!marketData[5].equals(sellerName)) {
                toReturn.add(i);
            }
        }

        ArrayList<String> b = new ArrayList<>(); //adds stuff to be edited

        for (String i : temp) {
            String[] marketData = i.split(";");
            if (marketData[5].equals(sellerName)) {
                b.add(i);
            }
        }

        JFrame frame = new JFrame();
        JPanel marketPlace = new JPanel(new BorderLayout(5, 5));

        String[] columnNames = {"Product Name", "Price", "Associated Store", "Quantity/Stock",
                                "Product Description", "Seller Name"};

        //displayGUIMarketplace(finalData);

        String[][] tempe = new String[b.size()][6];
        for (int i = 0; i < tempe.length; i++) {
            String[] a = b.get(i).split(";");
            System.arraycopy(a, 0, tempe[i], 0, tempe[0].length);
        }
        JTable table = new JTable(tempe, columnNames);

        DefaultTableModel tableModel = new DefaultTableModel(tempe, columnNames) {
            @Override public boolean isCellEditable(int row, int column) {
                return column != 2 && column != 5;
                //column 2 and 5 disable
            }
        };

        table.setModel(tableModel);
        table.putClientProperty("terminatedEditOnFocusLost", Boolean.TRUE);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(200);
        columnModel.getColumn(5).setPreferredWidth(400);

        table.setColumnModel(columnModel);

        marketPlace.add(scrollPane, BorderLayout.CENTER);

        UIManager.put("OptionPane.minimumSize", new Dimension(1200, 500));
        UIManager.put("OptionPane.setResizable", true);

        boolean repeat;
        do {
            repeat = false;
            JOptionPane.showMessageDialog(frame, marketPlace, "Seller Marketplace",
                                          JOptionPane.INFORMATION_MESSAGE);
            for (int i = 0; i < b.size(); i++) {
                try {
                    Integer.parseInt((String) table.getModel().getValueAt(i, 3));
                } catch (NumberFormatException e) {
                    repeat = true;
                    JOptionPane.showMessageDialog(null, "Input is not an integer, please only enter integers",
                                                  "SellerMarketplace", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                try {
                    Double.parseDouble((String) table.getModel().getValueAt(i, 1));
                } catch (NumberFormatException e) {
                    repeat = true;
                    JOptionPane.showMessageDialog(null, "Input is not an double, please only enter doubles",
                                                  "SellerMarketplace", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        } while (repeat);

        UIManager.put("OptionPane.minimumSize", new Dimension(200, 200));

        for (int i = 0; i < b.size(); i++) {
            String[] arr = new String[6];
            for (int j = 0; j < 6; j++) {
                arr[j] = (String) table.getModel().getValueAt(i, j);
            }
            toReturn.add(String.join(";", arr));
        }

        return toReturn;
    }

    /**
     * Displays a GUI with an editable table where the user can enter in updated values for the quantity they want
     * for a product that already exists in their cart
     * <p> There is no validation if the user enters a quantity that exceeds product stock, this validation will
     * be performed when the customer tries to check out, see checkoutCart()
     *
     * @param shoppingCart ArrayList where every element is a line from shoppingCart.txt
     * @param username     username of the user
     * @param password     password of the user
     *
     * @return An ArrayList of strings to be replaced into shoppingCart.txt
     *
     * @author Justin Kam
     */
    public static ArrayList<String> modifyCart(ArrayList<String> shoppingCart, String username, String password) {

        JOptionPane.showMessageDialog(null, "Disclaimer: You are only allowed to edit quantity as a customer",
                                      "ModifyCartDisclaimer", JOptionPane.WARNING_MESSAGE);

        ArrayList<String> toReturn = new ArrayList<>();

        for (String i : shoppingCart) {
            String[] cartData = i.split(";");
            if (!(cartData[0].equals(username)) && !(cartData[1].equals(password))) {
                toReturn.add(i);
            }
        }

        ArrayList<String> parsedData = getStrings(shoppingCart, username, password);

        JFrame frame = new JFrame();
        JPanel marketPlace = new JPanel(new BorderLayout(5, 5));

        String[] columnNames = {"Product Name", "Unit Price", "Store Name", "Quantity", "Product Description",
                                "Seller Name"};

        //displayGUIMarketplace(finalData);

        String[][] tempe = new String[parsedData.size()][6];
        for (int i = 0; i < tempe.length; i++) {
            String[] a = parsedData.get(i).split(";");
            System.arraycopy(a, 0, tempe[i], 0, tempe[0].length);
        }
        JTable table = new JTable(tempe, columnNames);

        DefaultTableModel tableModel = new DefaultTableModel(tempe, columnNames) {
            @Override public boolean isCellEditable(int row, int column) {
                return column == 3;
                //return true
            }
        };

        table.setModel(tableModel);

        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(600);
        columnModel.getColumn(5).setPreferredWidth(100);

        table.setColumnModel(columnModel);

        marketPlace.add(scrollPane, BorderLayout.CENTER);

        UIManager.put("OptionPane.minimumSize", new Dimension(1200, 500));
        UIManager.put("OptionPane.setResizable", true);

        boolean repeat;
        do {
            repeat = false;
            JOptionPane.showMessageDialog(frame, marketPlace, "Display Customer Cart",
                                          JOptionPane.INFORMATION_MESSAGE);
            for (int i = 0; i < parsedData.size(); i++) {
                try {
                    Integer.parseInt((String) table.getModel().getValueAt(i, 3));
                } catch (NumberFormatException e) {
                    repeat = true;
                    JOptionPane.showMessageDialog(null,
                                                  "One or more inputs are not integers, please only enter " +
                                                      "integers",
                                                  "NumberFormatException", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        } while (repeat);

        for (int i = 0; i < parsedData.size(); i++) {
            String[] arr = new String[6];
            for (int j = 0; j < 6; j++) {
                arr[j] = (String) table.getModel().getValueAt(i, j);
            }
            toReturn.add(username + ";" + password + ";" + String.join(";", arr));
        }

        UIManager.put("OptionPane.minimumSize", new Dimension(200, 200));

        return toReturn;
    }

    private static ArrayList<String> getStrings(ArrayList<String> shoppingCart, String username, String password) {
        ArrayList<String> finalData = new ArrayList<>();

        for (String preData : shoppingCart) {
            String[] listData = preData.split(";");
            if (listData[0].equals(username) && listData[1].equals(password)) {
                finalData.add(preData);
            }
        }

        ArrayList<String> parsedData = new ArrayList<>();

        for (String gotData : finalData) {
            int counter = 0;
            for (int j = 0; j < gotData.length(); j++) {
                if (gotData.charAt(j) == ';') {
                    counter++;
                }
                if (counter == 2) {
                    parsedData.add(gotData.substring(j + 1));
                    break;
                }
            }
        }
        return parsedData;
    }

    /**
     * Function to delete something from a customer's cart
     *
     * @param shoppingCart ArrayList where each element is a line from shoppingCart.txt
     * @param username     username of the user
     *
     * @return ArrayList of lines to replace into shoppingCart.txt, null if product cannot be deleted (e.g., cart
     * empty or product to be deleted is not in cart)
     *
     * @author Justin Kam
     */
    public static ArrayList<String> deleteFromCart(ArrayList<String> shoppingCart, String username) {
        ArrayList<String> toReturn = new ArrayList<>();

        String productName;
        do {
            productName = JOptionPane.showInputDialog(null, "Enter name of item to be removed", "remove Item",
                                                      JOptionPane.WARNING_MESSAGE);
            if (productName == null) {
                return null;
            }
        } while (productName.isEmpty());

        boolean exists = false;
        ArrayList<String[]> oldShoppingCart = new ArrayList<>();
        ArrayList<String[]> newShoppingCart = new ArrayList<>();

        for (String s : shoppingCart) {
            String[] cartData = s.split(";");
            oldShoppingCart.add(cartData);
        }

        for (String[] product : oldShoppingCart) {
            if (product[0].equals(username) && product[2].equals(productName)) {
                exists = true;
            } else {
                newShoppingCart.add(product);
            }
        }

        if (!exists) {
            JOptionPane.showMessageDialog(null, "Cannot remove a product that isn't in your cart",
                                          "ProductNotFound", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        for (String[] product : newShoppingCart) {
            toReturn.add(String.join(";", product));
        }

        JOptionPane.showMessageDialog(null, "Removed from cart", "Remove from Cart Success",
                                      JOptionPane.INFORMATION_MESSAGE);

        return toReturn;
    }

    /**
     * @param shoppingCart ArrayList containing lines in shoppingcart.txt
     * @param market       ArrayList containing lines in market.txt
     * @param purchases    ArrayList containing lines in purchases.txt
     * @param userName     username of the user
     *
     * @return An ArrayList of String Arraylists in the following format <p> ArrayList[1]: Strings to be changed
     * for shoppingCart.txt<p> ArrayList[2]: Strings to be changed for purchases.txt <p> ArrayList[3]: Strings to
     * be changed for market.txt <p> If null is returned, then something went wrong, an error message, integrated
     * in this method, is printed explaining the error
     *
     * @author Justin Kam
     */
    private static ArrayList<ArrayList<String>> checkoutCart(ArrayList<String> shoppingCart,
                                                             ArrayList<String> market, ArrayList<String> purchases,
                                                             String userName) {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();

        JFrame frame = new JFrame("Purchasing Cart");
        JPanel processingPanel = new JPanel();

        JProgressBar bar = new JProgressBar();
        bar.setValue(0);
        bar.setStringPainted(true);
        processingPanel.add(bar);
        frame.add(processingPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);

        int i = 0;
        try {
            while (i <= 100) {
                bar.setValue(i + 10);
                Thread.sleep(1000);
                i += 20;
            }
        } catch (Exception ignored) {
            boolean error = true;
        }

        frame.dispose();
        ArrayList<String[]> oldShoppingCart = new ArrayList<>();
        ArrayList<String[]> newShoppingCart = new ArrayList<>();
        ArrayList<String[]> marketList = new ArrayList<>();
        ArrayList<String[]> newMarketList = new ArrayList<>();
        ArrayList<String[]> checkout = new ArrayList<>();

        boolean exists = false;
        for (String j : shoppingCart) {
            String[] dataStr = j.split(";");
            oldShoppingCart.add(dataStr);
        }

        for (String[] product : oldShoppingCart) {
            if (product[0].equals(userName)) {
                exists = true;
                checkout.add(product);
            } else {
                newShoppingCart.add(product);
            }
        }

        if (!exists) {
            JOptionPane.showMessageDialog(null, "Cart is empty", "cartEmpty", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        for (String j : market) {
            String[] dataStr = j.split(";");
            marketList.add(dataStr);
        }

        for (String[] marketItem : marketList) {
            for (String[] checkoutItem : checkout) {
                if (marketItem[0].equals(checkoutItem[2]) && marketItem[2].equals(checkoutItem[4])) {
                    if ((Integer.parseInt(marketItem[3]) - Integer.parseInt(checkoutItem[5]) < 0)) {
                        JOptionPane.showMessageDialog(null, "Buying too many of a product", "productUnderflow",
                                                      JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                    marketItem[3] = "" + (Integer.parseInt(marketItem[3]) - Integer.parseInt(checkoutItem[5]));
                }
            }
            // adds the modified marketItem to the newMarketList
            newMarketList.add(marketItem);
        }

        ArrayList<String> newShoppingCartStrings = new ArrayList<>();
        for (String[] product : newShoppingCart) {
            newShoppingCartStrings.add(String.join(";", product));
        }
        toReturn.add(newShoppingCartStrings);

        for (String[] product : checkout) {
            purchases.add(
                product[2] + ";" + product[3] + ";" + product[4] + ";" + product[5] + ";" + product[0] + ";" +
                    product[7]);
        }
        toReturn.add(purchases);

        ArrayList<String> newMarketStrings = new ArrayList<>();
        for (String[] product : newMarketList) {
            newMarketStrings.add(String.join(";", product));
        }
        toReturn.add(newMarketStrings);

        JFrame frame2 = new JFrame();

        JPanel justBrought = new JPanel(new BorderLayout(5, 5));

        String[] columnNames = {"Product Name", "Unit Price", "Quantity"};

        String[][] tempe = new String[checkout.size()][3];
        for (int j = 0; j < tempe.length; j++) {
            String[] a = {checkout.get(j)[2], checkout.get(j)[3], checkout.get(j)[5]};
            System.arraycopy(a, 0, tempe[j], 0, tempe[0].length);
        }
        JTable table = new JTable(tempe, columnNames);

        DefaultTableModel tableModel = new DefaultTableModel(tempe, columnNames) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(300);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);

        table.setColumnModel(columnModel);

        justBrought.add(scrollPane, BorderLayout.CENTER);

        UIManager.put("OptionPane.minimumSize", new Dimension(1200, 500));
        UIManager.put("OptionPane.setResizable", true);

        JOptionPane.showMessageDialog(frame2, justBrought, "You have purchased: ...",
                                      JOptionPane.INFORMATION_MESSAGE);

        double total = 0.0;
        for (String[] product : checkout) {
            total += Double.parseDouble(product[3]) * Double.parseDouble(product[5]);
        }

        UIManager.put("OptionPane.minimumSize", new Dimension(200, 200));

        JOptionPane.showMessageDialog(null, "Total spent: " + String.format("%.2f", total), "Grand Total",
                                      JOptionPane.INFORMATION_MESSAGE);

        return toReturn;
    }

    /**
     * Searches for matches of search parameter (will be prompted within the function) with product names or
     * descriptions and displays a GUI table showing results <p>
     *
     * @param data an ArrayList containing the strings of each line from Market.txt
     *
     * @author Justin Kam, Lalitha Chandolu
     */
    public static void productSearching(ArrayList<String> data) {
        String searchParam;
        do {
            searchParam = JOptionPane.showInputDialog("Enter Search Parameter");
            if (searchParam == null) {
                return;
            }
        } while (searchParam == null || searchParam.isEmpty());

        ArrayList<String> searchResults = new ArrayList<>();
        for (String preData : data) {
            String[] processData = preData.split(";");
            if (processData[0].toLowerCase().contains(searchParam.toLowerCase()) || processData[4].toLowerCase()
                .contains(searchParam.toLowerCase())) {
                searchResults.add(preData);
            }
        }

        displayGUIMarketplace(searchResults);
    }

    /**
     * @param data     ArrayList where every element is one line of purchases.txt
     * @param username Username of the user
     *
     * @author Justin Kam
     */
    public static void viewCustomerShoppingHistory(ArrayList<String> data, String username) {
        ArrayList<String> finalData = new ArrayList<>();

        for (String preData : data) {
            String[] processData = preData.split(";");
            //index 4
            if (processData[4].equals(username)) {
                finalData.add(preData);
            }
        }

        JFrame frame = new JFrame();
        JPanel shoppingHistory = new JPanel(new BorderLayout(5, 5));

        String[] columnNames = {"Product Name", "Unit Price", "Store Name", "Quantity", "Buyer", "Seller Name"};
        String[][] tempe = new String[finalData.size()][6];
        for (int i = 0; i < tempe.length; i++) {
            String[] a = finalData.get(i).split(";");
            System.arraycopy(a, 0, tempe[i], 0, tempe[0].length);
        }
        JTable table = new JTable(tempe, columnNames);

        DefaultTableModel tableModel = new DefaultTableModel(tempe, columnNames) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(600);
        columnModel.getColumn(5).setPreferredWidth(100);

        table.setColumnModel(columnModel);

        shoppingHistory.add(scrollPane, BorderLayout.CENTER);

        UIManager.put("OptionPane.minimumSize", new Dimension(1200, 500));
        UIManager.put("OptionPane.setResizable", true);

        JOptionPane.showMessageDialog(frame, shoppingHistory, "Display Customer Cart",
                                      JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Function that shows a GUI for uploading/importing a file using file chooser GUI
     *
     * @return Absolute file path as a String
     *
     * @author Justin Kam
     */
    public static String chooseImportFile() {
        JFrame frame = new JFrame();

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv");
        fc.setFileFilter(filter);
        panel.add(fc);

        File file;

        do {
            int choice = JOptionPane.showConfirmDialog(frame, panel, "ChooseFile", JOptionPane.OK_CANCEL_OPTION);
            if (choice == JOptionPane.CANCEL_OPTION) {
                return null;
            }
            file = fc.getSelectedFile();
        } while (file == null);
        return file.getAbsolutePath();
    }

    /**
     * Function that allows the user to choose a directory to export to
     *
     * @param temp     ArrayList of Strings from purchases.txt
     * @param userName username of customer
     *
     * @author Justin Kam
     */
    private static void exportShoppingHistory(ArrayList<String> temp, String userName) {
        JFrame frame = new JFrame();

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        panel.add(fc);

        File file;

        JOptionPane.showMessageDialog(null, "Please select a directory to export to", "dirQuery",
                                      JOptionPane.QUESTION_MESSAGE);

        do {
            int choice = JOptionPane.showConfirmDialog(frame, panel, "ChooseFile", JOptionPane.OK_CANCEL_OPTION);
            if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }
            file = fc.getSelectedFile();
        } while (file == null);

        String dirPath = file.getAbsolutePath();

        File f = new File(dirPath + "/purchaseHistoryExport.csv");
        try {
            f.createNewFile();
            FileWriter fw = new FileWriter(f, false);
            fw.write("Product Name, Product Price, Store Name, Quantity Purchased \n");
            for (String s : temp) {
                String[] data = s.split(";");
                if (data[4].equals(userName)) {
                    fw.write(data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "\n");
                }
            }
            fw.close();
        } catch (IOException ignored) {
            boolean error = true;
        }

        JOptionPane.showMessageDialog(null, "File Exported, if file empty, then your shopping cart is empty",
                                      "FileExported", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void main(String[] args) {
        UIManager.put("OptionPane.minimumSize", new Dimension(200, 200));
        JOptionPane.showMessageDialog(null, "Welcome to Boilermaker Bazaar", "Welcome Msg",
                                      JOptionPane.INFORMATION_MESSAGE);

        Socket sock;
        try {
            sock = new Socket("localhost", 6969);
            //InetAddress host = InetAddress.getLocalHost(); (get internet address of local host, use this later
            // for
            // network io
            BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

            boolean redirected = false;
            boolean exitOnFirst = false;
            String userRole = "";
            String userName = "";
            String password = "";
            do {
                String beforeInputOption = (String) JOptionPane.showInputDialog(null,
                                                                                "Select an option (Cancel " +
                                                                                    "button" + " " + "exits program)",
                                                                                "beforeLogin",
                                                                                JOptionPane.INFORMATION_MESSAGE,
                                                                                null, BEFORE_LOGIN_OPTIONS,
                                                                                BEFORE_LOGIN_OPTIONS[0]);
                if (beforeInputOption == null) {
                    redirected = true;
                    exitOnFirst = true;
                } else if (beforeInputOption.equals("Login")) {
                    ArrayList<String> temp = new ArrayList<>(); //users.txt

                    writer.write("users.txt\n");
                    writer.flush();
                    String s = reader.readLine();
                    while (!s.equals("STOP")) {
                        temp.add(s);
                        s = reader.readLine();
                    }
                    writer.write("STOP\n");
                    writer.flush();

                    String[] loginInfo = displayLoginPage(temp);
                    if (loginInfo != null) {
                        userName = loginInfo[0];
                        password = loginInfo[1];
                        userRole = loginInfo[2];
                        redirected = true;
                    }

                } else if (beforeInputOption.equals("Sign Up")) {
                    ArrayList<String> temp = new ArrayList<>();

                    writer.write("users.txt\n");
                    writer.flush();
                    String s = reader.readLine();
                    while (!s.equals("STOP")) {
                        temp.add(s);
                        s = reader.readLine();
                    }

                    String signupInfo = displaySignupPage(temp);
                    if (signupInfo != null) {
                        temp.add(signupInfo);
                        writer.write("users.txt\n");
                        for (String i : temp) {
                            writer.write(i + "\n");
                        }
                    }
                    writer.write("STOP\n");
                    writer.flush();

                } else if (beforeInputOption.equals("Exit")) {
                    redirected = true;
                    exitOnFirst = true;
                } else if (beforeInputOption.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Select an Option", "Error",
                                                  JOptionPane.ERROR_MESSAGE);
                }
            } while (!redirected);

            boolean loggedOut = exitOnFirst;

            while (!loggedOut) {
                if (userRole.equalsIgnoreCase("s")) {
                    String mainMenuChoice = (String) JOptionPane.showInputDialog(null, "Please select an Option",
                                                                                 "sellerMenu",
                                                                                 JOptionPane.INFORMATION_MESSAGE,
                                                                                 null, SELLER_MENU_OPTIONS,
                                                                                 SELLER_MENU_OPTIONS[0]);
                    if (mainMenuChoice == null) {
                        loggedOut = true;
                    } else if (mainMenuChoice.equals("View Marketplace")) {
                        ArrayList<String> temp = new ArrayList<>();

                        writer.write("market.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            temp.add(s);
                            s = reader.readLine();
                        }
                        writer.write("STOP\n");
                        writer.flush();
                        customerMarketplace(temp, "seller");
                        UIManager.put("OptionPane.minimumSize", new Dimension(200, 200));
                    } else if (mainMenuChoice.equals("View All Sales by Store")) { //to be done
                        //will go through purchases .txt file so that method can sort through the past purchases by
                        ArrayList<String> temp = new ArrayList<>();

                        writer.write("purchases.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            temp.add(s);
                            s = reader.readLine();
                        }
                        viewAllSalesByStore(temp, userName);
                        writer.write("STOP\n");
                        writer.flush();

                    } else if (mainMenuChoice.equals("Add a product")) {
                        ArrayList<String> temp = new ArrayList<>();
                        //market.txt
                        writer.write("market.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            temp.add(s);
                            s = reader.readLine();
                        }
                        String toAdd = addProduct(temp, userName);
                        if (toAdd == null) {
                            writer.write("STOP\n");
                        } else {
                            writer.write("market.txt\n");
                            for (String product : temp) {
                                writer.write(product + "\n");
                            }
                            writer.write(toAdd + "\n");
                            writer.write("STOP\n");
                        }
                        writer.flush();

                        UIManager.put("OptionPane.minimumSize", new Dimension(200, 200));
                    } else if (mainMenuChoice.equals("Edit a product")) {

                        ArrayList<String> temp = new ArrayList<>();
                        writer.write("market.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            temp.add(s);
                            s = reader.readLine();
                        }
                        ArrayList<String> one = editProductJTable(temp, userName);
                        if (!one.equals(temp)) JOptionPane.showMessageDialog(null, "Product was edited " +
                                                                                "successfully",
                                                                          "sellerMarketplace",
                                                                          JOptionPane.INFORMATION_MESSAGE);
                        writer.write("market.txt\n");
                        for (String editedProducts : one) {
                            writer.write(editedProducts + "\n");
                        }
                        writer.write("STOP\n");
                        writer.flush();

                    } else if (mainMenuChoice.equals("Delete a product")) {
                        ArrayList<String> temp = new ArrayList<>();
                        writer.write("market.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            temp.add(s);
                            s = reader.readLine();
                        }

                        String product = JOptionPane.showInputDialog(null,
                                                                     "What product would you like to remove?",
                                                                     "SellerMarketplace",
                                                                     JOptionPane.QUESTION_MESSAGE);
                        String store = JOptionPane.showInputDialog(null,
                                                                   "What store should the product be removed " +
                                                                       "from?",
                                                                   "Seller", JOptionPane.QUESTION_MESSAGE);
                        ArrayList<String> list = removeProduct(temp, userName, product, store);
                        writer.write("market.txt\n");
                        for (String string : list) {
                            writer.write(string + "\n");
                        }
                        writer.write("STOP\n");
                        writer.flush();
                    } else if (mainMenuChoice.equals("View Store stats")) {
                        ArrayList<String> temp = new ArrayList<>();

                        //purchases.txt
                        writer.write("purchases.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            temp.add(s);
                            s = reader.readLine();
                        }
                        writer.write("STOP\n");
                        writer.flush();

                        String item;
                        int option;
                        String[] choices = {"Sort By List of Customers", "Sort by Products Bought"};
                        item = (String) JOptionPane.showInputDialog(null,
                                                                    "Select following option to sort: " +
                                                                        "Statistics" + " Dashboard ",
                                                                    "SellerMarketplace", JOptionPane.PLAIN_MESSAGE,
                                                                    null, choices, null);
                        if (item.equals("Sort By List of Customers")) {
                            option = 1;
                        } else {
                            option = 2;
                        }
                        viewStoreStatistics(temp, userName, option);
                    } else if (mainMenuChoice.equals("Import/Export Products")) {
                        ArrayList<String> marketList = new ArrayList<>();
                        writer.write("market.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            marketList.add(s);
                            s = reader.readLine();
                        }

                        String item;
                        String filePathString;
                        String[] choices = {"Import to be added to my stores",
                                            "Export the products from my stores"};
                        item = (String) JOptionPane.showInputDialog(null,
                                                                    "Would you like to import or export a CSv: ",
                                                                    "SellerMarketplace", JOptionPane.PLAIN_MESSAGE,
                                                                    null, choices, null);
                        if (item == null) {
                            writer.write("STOP\n");
                            writer.flush();
                            continue;
                        }
                        if (item.equals("Import to be added to my stores")) {
                            filePathString = chooseImportFile();

                            if (filePathString != null) {
                                try {
                                    File importFile = new File(filePathString);
                                    FileReader fr = new FileReader(importFile);
                                    BufferedReader bfr = new BufferedReader(fr);
                                    String line = bfr.readLine();
                                    if (line != null) {
                                        line = line.replace("\uFEFF", "");
                                    }
                                    while (line != null) {
                                        if (line.contains(",") && line.length() > 4) {
                                            marketList.add(
                                                String.join(";", line.split(",")).trim() + ";" + userName);
                                        }
                                        line = bfr.readLine();
                                        if (line != null) {
                                            line = line.replace("\uFEFF", "");
                                        }
                                    }
                                } catch (IOException ie) {
                                    JOptionPane.showMessageDialog(null,
                                                                  "Information in the file given could not be " +
                                                                      "imported.",
                                                                  "SellerError", JOptionPane.ERROR_MESSAGE);
                                }

                                writer.write("market.txt\n");
                                for (String str : marketList) {
                                    writer.write(str + "\n");
                                }
                                JOptionPane.showMessageDialog(null,
                                                              "Information from file has been imported to " +
                                                                  "Client" + ".Marketplace.",
                                                              "SellerMarketplace",
                                                              JOptionPane.INFORMATION_MESSAGE);
                            }
                            writer.write("STOP\n");
                            writer.flush();
                        } else {

                            writer.write("STOP\n");
                            writer.flush();

                            String val = JOptionPane.showInputDialog(null, "Enter store name to export",
                                                                     "ExportStoreInfo",
                                                                     JOptionPane.QUESTION_MESSAGE);

                            boolean returned = exportStoreInformation(marketList, userName, val);

                            if (returned) {
                                JOptionPane.showMessageDialog(null,
                                                              "File Exported, if file is empty, store is empty",
                                                              "ExportedFile", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }

                    } else if (mainMenuChoice.equals("View customer shopping carts for my stores")) {
                        ArrayList<String> temp = new ArrayList<>();
                        writer.write("shoppingCart.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            temp.add(s);
                            s = reader.readLine();
                        }
                        writer.write("STOP\n");
                        writer.flush();
                        JFrame frame = new JFrame();
                        JPanel marketPlace = new JPanel(new BorderLayout(5, 5));

                        String[] columnNames = {"Store Name", "Customer Name", "Product Name", "Price", "Quantity",
                                                "Description"};

                        //displayGUIMarketplace(finalData);

                        ArrayList<String> temp2 = new ArrayList<>();

                        for (String i : temp) {
                            String[] tempData = i.split(";");
                            if (tempData[7].equals(userName)) {
                                temp2.add(i);
                            }
                        }

                        String[][] tempe = new String[temp2.size()][6];
                        for (int i = 0; i < tempe.length; i++) {
                            String[] a = temp2.get(i).split(";");
                            if (a[7].equals(userName)) {
                                String[] arr = new String[6];
                                arr[0] = a[4];
                                arr[1] = a[0];
                                arr[2] = a[2];
                                arr[3] = a[3];
                                arr[4] = a[5];
                                arr[5] = a[6];
                                System.arraycopy(arr, 0, tempe[i], 0, tempe[0].length);
                            }
                        }
                        JTable table = new JTable(tempe, columnNames);

                        DefaultTableModel tableModel = new DefaultTableModel(tempe, columnNames) {
                            @Override public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };

                        table.setModel(tableModel);

                        JScrollPane scrollPane = new JScrollPane(table);
                        table.setFillsViewportHeight(true);

                        TableColumnModel columnModel = table.getColumnModel();
                        columnModel.getColumn(0).setPreferredWidth(200);
                        columnModel.getColumn(1).setPreferredWidth(100);
                        columnModel.getColumn(2).setPreferredWidth(150);
                        columnModel.getColumn(3).setPreferredWidth(100);
                        columnModel.getColumn(4).setPreferredWidth(200);
                        columnModel.getColumn(5).setPreferredWidth(400);

                        table.setColumnModel(columnModel);

                        marketPlace.add(scrollPane, BorderLayout.CENTER);

                        UIManager.put("OptionPane.minimumSize", new Dimension(1200, 500));
                        UIManager.put("OptionPane.setResizable", true);

                        JOptionPane.showMessageDialog(frame, marketPlace, "Seller Marketplace",
                                                      JOptionPane.INFORMATION_MESSAGE);
                        UIManager.put("OptionPane.minimumSize", new Dimension(200, 200));

                    } else if (mainMenuChoice.equals("Log Out")) {
                        loggedOut = true;
                    }
                } else { //customer Menus
                    String mainMenuChoice = (String) JOptionPane.showInputDialog(null, "Please select an Option",
                                                                                 "customerMenu",
                                                                                 JOptionPane.INFORMATION_MESSAGE,
                                                                                 null, BUYER_MENU_OPTIONS,
                                                                                 BUYER_MENU_OPTIONS[0]);
                    if (mainMenuChoice == null) {
                        loggedOut = true;
                    } else if (mainMenuChoice.equals("View dashboard")) {
                        ArrayList<String> temp = new ArrayList<>();

                        writer.write("market.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            temp.add(s);
                            s = reader.readLine();
                        }
                        writer.write("STOP\n");
                        writer.flush();
                        customerMarketplace(temp, "customer");
                        UIManager.put("OptionPane.minimumSize", new Dimension(200, 200));
                    } else if (mainMenuChoice.equals("View Shopping Cart")) {
                        ArrayList<String> shoppingCart = new ArrayList<>();
                        ArrayList<String> market = new ArrayList<>();
                        writer.write("shoppingCart.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            shoppingCart.add(s);
                            s = reader.readLine();
                        }
                        writer.write("STOP\n");
                        writer.flush();
                        writer.write("market.txt\n");
                        writer.flush();
                        s = reader.readLine();
                        while (!s.equals("STOP")) {
                            market.add(s);
                            s = reader.readLine();
                        }
                        String cartChoice = displayCart(shoppingCart, userName, password);
                        UIManager.put("OptionPane.minimumSize", new Dimension(200, 200));
                        if (cartChoice == null) {
                            writer.write("STOP\n");
                            writer.flush();
                        } else if (cartChoice.equals("Add product to cart")) {
                            ArrayList<String> temp = addToCart(market, shoppingCart, userName, password);
                            if (temp != null) {
                                writer.write("shoppingCart.txt\n");
                                writer.flush();
                                for (String i : temp) {
                                    writer.write(i + "\n");
                                }
                            }
                            writer.write("STOP\n");
                            writer.flush();
                        } else if (cartChoice.equals("Remove product from cart")) {
                            ArrayList<String> temp = deleteFromCart(shoppingCart, userName);

                            if (temp != null) {
                                writer.write("shoppingCart.txt\n");
                                writer.flush();
                                for (String i : temp) {
                                    writer.write(i + "\n");
                                }
                            }
                            writer.write("STOP\n");
                            writer.flush();
                        } else if (cartChoice.equals("Checkout cart")) {
                            writer.write("STOP\n");
                            writer.flush();
                            ArrayList<String> purchases = new ArrayList<>();
                            writer.write("purchases.txt\n");
                            writer.flush();
                            String a = reader.readLine();
                            while (!a.equals("STOP")) {
                                purchases.add(a);
                                a = reader.readLine();
                            }
                            ArrayList<ArrayList<String>> temp = checkoutCart(shoppingCart, market, purchases,
                                                                             userName);
                            if (temp == null) {
                                writer.write("STOP\n");
                                writer.flush();
                                JOptionPane.showMessageDialog(null,
                                                              "Something went wrong, your cart couldn't be " +
                                                                  "purchased" + " " + "from",
                                                              "CartError", JOptionPane.ERROR_MESSAGE);
                            } else {
                                writer.write("shoppingCart.txt\n");
                                writer.flush();
                                ArrayList<String> toShoppingCart = temp.get(0);
                                for (String i : toShoppingCart) {
                                    writer.write(i + "\n");
                                }
                                writer.write("STOP\n");
                                writer.flush();
                                writer.write("purchases.txt\n");
                                writer.flush();
                                String b = reader.readLine();
                                while (!b.equals("STOP")) {
                                    b = reader.readLine();
                                }
                                writer.write("purchases.txt\n");
                                writer.flush();
                                ArrayList<String> toPurchases = temp.get(1);
                                for (String i : toPurchases) {
                                    writer.write(i + "\n");
                                }
                                writer.write("STOP\n");
                                writer.flush();

                                writer.write("purchases.txt\n");
                                writer.flush();
                                String c = reader.readLine();
                                while (!c.equals("STOP")) {
                                    c = reader.readLine();
                                }
                                writer.write("market.txt\n");
                                writer.flush();
                                ArrayList<String> toMarket = temp.get(2);
                                for (String i : toMarket) {
                                    writer.write(i + "\n");
                                }
                                writer.write("STOP\n");
                                writer.flush();
                            }

                        } else if (cartChoice.equals("Modify Cart")) {
                            ArrayList<String> temp = modifyCart(shoppingCart, userName, password);
                            writer.write("shoppingCart.txt\n");
                            writer.flush();
                            for (String i : temp) {
                                writer.write(i + "\n");
                            }
                            writer.write("STOP\n");
                            writer.flush();
                            JOptionPane.showMessageDialog(null, "Cart modified", "modify cart",
                                                          JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            writer.write("STOP\n");
                            writer.flush();
                        }
                    } else if (mainMenuChoice.equals("Search for product")) {
                        ArrayList<String> temp = new ArrayList<>();

                        writer.write("market.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            temp.add(s);
                            s = reader.readLine();
                        }
                        writer.write("STOP\n");
                        writer.flush();
                        productSearching(temp);
                        UIManager.put("OptionPane.minimumSize", new Dimension(200, 200));
                    } else if (mainMenuChoice.equals("View Shopping history")) {
                        ArrayList<String> temp = new ArrayList<>();

                        writer.write("purchases.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            temp.add(s);
                            s = reader.readLine();
                        }
                        writer.write("STOP\n");
                        writer.flush();
                        viewCustomerShoppingHistory(temp, userName);
                        UIManager.put("OptionPane.minimumSize", new Dimension(200, 200));
                    } else if (mainMenuChoice.equals("Export shopping cart history")) {
                        ArrayList<String> temp = new ArrayList<>();

                        writer.write("purchases.txt\n");
                        writer.flush();
                        String s = reader.readLine();
                        while (!s.equals("STOP")) {
                            temp.add(s);
                            s = reader.readLine();
                        }
                        writer.write("STOP\n");
                        writer.flush();

                        exportShoppingHistory(temp, userName);

                    } else if (mainMenuChoice == null || mainMenuChoice.equals("Log Out")) {
                        loggedOut = true;
                    }
                }
            }

            JFrame frame = new JFrame();
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            JTextArea ta = new JTextArea("Thank you for using Boiler Bazaar!");
            ta.setEditable(false);
            panel.add(ta, BorderLayout.CENTER);
            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection Refused", "Connection Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
}