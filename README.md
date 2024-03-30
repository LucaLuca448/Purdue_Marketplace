# Purdue Boilermaker Bazaar 
(CS 180 - Project 5 Option 3)

###
By: Ansh, Nirmal, Lalitha, Ankita, and Justin, Lab 02

#### Code submitted on Vocareum by Nirmal \|  Project Report Submitted by Justin \| Video Submitted by Nirmal. \|

## Overview:
Boilermaker Bazaar is an application where Purdue students can buy and sell their Purdue merchandise (apparel, textbooks, game tickets, etc) to one another through their individual stores. Customers can view products to buy by store, description, or name, and sellers can create stores and list their products they would like to sell. The target market for this application is specifically for Purdue students who enjoy buying and selling, want to make some money, or find vintage or unique pieces of Purdue merchandise.

### 1. Running the Messenger

##### To run the messenger, follow these steps: 


### 2. Submission on Brightspace/Vocareum

### 3. The Classes

Project 5 uses Network IO, File IO, Exception Handling, Interfaces, Concurrency, and a Graphical User Interface (GUI).

## Getting Started
**Steps (Using Intellij):**
1. Press the green Code button on the top right.
2. Copy the HTTPS link and navigate to Intellij.
3. In Intellij, press get from Version Control and paste the link copied into the box.
4. Build the project and run the main method.

**Steps (Using Terminal)**
1. Press the green Code button on the top right.
2. Copy the HTTPS link and navigate to a terminal window.
3. Type ```git clone``` and paste the link after and press enter.
4. Use ```javac filename.java``` to compile each of the classes in the file.
5. Type  ```java main.java``` to run the program.

## Using the Program
Download and upload the 4 txt (shopping cart, market, purchases, users) files and the 5 java files to IntelliJ or another IDE. Go to the Server and click the run button.
Then go to the Client.java file and click the run button.
Log-in to retrieve details if you have already used the marketplace application, otherwise Sign-up to create either a Customer or Seller account.

# Functions of the Classes

**Classes Being Implemented:**
1. Client
2. Server
3. Customer
4. Seller
5. Client.Marketplace

## Client
#### Menu Arrays
3 Initiated Arrays: 
The beforeLoginOptions array contains options for a user who has not yet logged in. sellerMenuOptions and buyerMenuOptions contain options for a seller and a buyer, respectively, after logging in.

#### Methods
| Name | Parameters | Description |
| --- | --- | --- | 
| displayLoginPage() | ArrayList<String> data | The method creates a JFrame, JPanel, JLabels, JTextFields, and a JPasswordField to construct the login interface. It uses a loop to keep prompting the user for input until valid username and password are provided or the user cancels the operation. It catches exceptions related to null values to handle potential errors. It compares the entered username and password with the data in the ArrayList<String> data. If a match is found, it retrieves the user's role. It returns an array containing the username, password, and user role if authentication is successful. If the user cancels or the authentication fails, it returns null. |
| displaySignupPage() | ArrayList<String> data | Similar to the login method, this method creates a JFrame, JPanel, JLabels, JTextFields, and a JComboBox to construct the signup interface. It uses a loop to keep prompting the user for input until valid information is provided or the user cancels the operation. It catches exceptions related to null values to handle potential errors. It uses a JComboBox to allow the user to choose between "Customer" and "Seller" for their role. It checks that all required fields (name, username, password, and user role) are filled before proceeding. If any field is empty, it displays an error message and returns to the signup screen. It checks whether the entered username or password already exists in the data provided. If a match is found, it displays an error message and returns to the signup screen. If the entered information is valid and does not match any existing accounts, it displays a success message and returns a formatted string containing the new user's information. |
| displayGUIMarketplace() | ArrayList<String> data | It parses the data provided (presumably containing information about products in the marketplace) and populates a 2D array (tempe) to be used as data for the JTable. It configures the JTable with column names and sets up a DefaultTableModel to control the data displayed. The table is set to be non-editable. It uses a JScrollPane to allow scrolling through the table, which is useful when there are many products. Then, it sets preferred widths for each column in the table to ensure proper display and configures the UIManager to set a minimum size for the JOptionPane and makes it resizable. Finally, it displays the marketplace information in a JOptionPane dialog with the created GUI components. |
| customerMarketplace() | ArrayList<String> data | The method takes an ArrayList<String> data containing information about products in the marketplace and a String type indicating the type of user (customer or seller). It provides the user with a set of sorting choices through a dialog. The selected choice is stored in the sortChoice variable. There are 3 sorting options. "None" which calls displayGUIMarketplace to display the marketplace without any sorting. "Ascending/Descending Price" sorts the products based on their prices in ascending or descending order. "Ascending/Descending Quantity" sorts the products based on their quantities in ascending or descending order. After sorting the data, it calls the displayGUIMarketplace method to show the sorted marketplace. |
| viewAllSalesByStore() | ArrayList<String> temp, String username | It iterates over the provided sales data (temp) and filters out the sales records where the seller's username (data[5]) matches the provided username. The filtered records are stored in the list ArrayList. Then, it sorts the filtered sales records based on the third element (o[2]) in each record. This assumes that the third element represents a value that can be compared, such as a store name or another sortable attribute. It constructs a StringBuilder (str) to build a formatted string containing information about sales by store. It iterates through the sorted sales records, calculating and appending details about each transaction to the string. Finally, it displays the information using a JOptionPane dialog with an information message type. It calls the updateMarketplace method from the Client.Marketplace class. The purpose of this method is not provided in the code snippet, but it seems to be related to updating the marketplace in some way after displaying the sales data. |
| addProduct() | ArrayList<String> temp, String username | It creates a graphical user interface (GUI) using Java Swing components to collect information about the new product, such as product name, price, store name, quantity, and description. It checks whether the seller owns the specified store (storeNames). If the store is not owned by the seller, it checks if the store doesn't already exist in the marketplace (marketStoreNames). If neither condition is met, it displays an error message and returns null. If all validations pass, it constructs a new product entry in the specified format and returns it as a string. It also displays a success message using JOptionPane. |

## Server

| Name | Parameters | Return Type | Modifier | Description |
| --- | --- | --- | --- | --- |
| main() | String[] args | void | public static | Waits for a client (GUI user) to request to connect, and then listens for a file name to send. Once it receives it from a client, it sends the details line by line and ends with the "STOP" delimiter at the end of the file. It then either waits for the client to send "STOP" to allow the server to wait for a new file to send, or a text file name for data to then be relayed line by line to be written into the Server's text files, succeeded by the "STOP" delimiter to liberate the server to listen for the next file. |


## GUI Testing

Testing consisted of creating 4 files: market.txt, purchases.txt, shoppingCart.txt, testImportFile.txt, users.txt. Each one contained test cases which consisted of data on users who were either sellers and customers. The market file had to mostly incorporate product details available on the market. During the testing phase of our code design, we went through a comprehensive and repetitive process to make sure everything worked smoothly. We made nearly 30 test cases in the TESTS.md file, where we took a close look at every function related to login and sign-up, using preset user info from our test cases. For customer functions, we used mainly "lchandol" as the username and "hackIt" as the password, while "davidkj" and "ignOps" were the go-to credentials for testing seller functions. Each member on the team used their own test cases for local testing purposes and to find edge-cases. Logging in as different types of users revealed different dashboards, so we carefully checked each function available. We cross-checked functions with our test files to ensure the changes were right and verified everything in the terminal or user interface. Our testing wasn't just about doing things the right way; we intentionally threw in some errors, like putting in the wrong format or calling for stuff that didn't exist. If the system didn't spit out a clear error message, we had debug until things were running smoothly, making sure our system was solid and reliable.
