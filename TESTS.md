# Boilermaker Bazaar Application Test Cases #

## Login/Sign-Up Tests: ##

**Test 1: User Log In**

Steps:
1. User launches application.
2. User selects the Login option in the dropdown menu
4. User enters username via the keyboard.
5. User enters their password in the password textbox.
6. User selects the "Ok" button.
7. OR User selects the "Cancel" button to return the application main menu

Expected result: Application verifies the user's username and password exist and match to a user and loads their corresponding menu dropdown automatically. If the user enters incorrect info or doesn't fill a field, they receive an error message that the username or password is incorrect.

Test Status: **Pass**

**Test 2: User Sign Up**

Steps:
1. User launches application.
2. User selects the "Sign-Up" option in the dropdown menu
4. User enters their Name, Purdue username, and New Password via the keyboard.
5. User specifies whether they would like to be a Buyer/Customer or a Seller through the dropdown menu
6. User is brought back to the Main Menu where they can choose to Login, Sign Up, or Exit the application.
7. User selects the "Ok" button.
8. If user chooses the Login option they are able to login as a registered user

Expected result: Application verifies the user's username and password doesn't exist and if it doesn't a new user is added to the Users.txt file on a new line.

Test Status: **Pass**


## Customer Tests: ##

**Test 1: View Dashboard (ascending and descending price order) and Return to Main Menu**

Steps:
1. Customer chooses the "View dashboard" option and clicks "OK"
2. Customer chooses to Sort Marketplace by "Ascending Price" or "Descending Price" using the dropdown menu
3. Customer clicks "OK"
4. Once Customer has viewed the marketplace in the sort they chose, they can click the "X" or "OK" to return to the main menu

Expected result: Customer views the overall Boilermaker Bazaar Marketplace sorted by Price (either ascending or descending price order) and returns to the Main Menu.

Test Status: **Pass**


**Test 2: View Dashboard (ascending and descending quantity order) and Return to Main Menu**

Steps:
1. Customer chooses the "View Dashboard" option and clicks "OK"
2. Customer chooses to Sort Marketplace by "Ascending Quantity" or "Descending Quantity" using the dropdown menu
3. Customer clicks "OK"
4. Once customer has viewed the marketplace in the sort they chose, they can click the "X" or "OK" to return to the main menu

Expected result: Customer views the overall Boilermaker Bazaar Marketplace sorted by Quantity (either ascending or descending order) and returns to the Main Menu.

Test Status: **Pass**


**Test 3: View Shopping Cart**

Steps:
1. Customer chooses the "View Shopping Cart" option from the dropdown menu
2. Customer clicks "OK"

Expected result: Customer can view their individual shopping cart. If they add or remove products from their cart, their shopping cart will reflect those changes. An empty shopping cart will have no products.

Test Status: **Pass**

**Test 4: Add Product to Cart**

Steps:
1. Customer chooses the "View Shopping Cart" option and clicks "OK"
2. Customer views their shopping cart and clicks "OK"
3. Customer chooses to "Add product to cart" through the dropdown menu
4. Customer clicks "OK"
5. Customer specifies the Product Name, Quantity Desired, and the Store Name
6. Customer clicks "OK" to add the product to their cart
7. Customer receives an error message if the product is unavailable or no product matches their inputs
8. OR customer receives a confirmation message that the product has been added to their shopping cart
9. Customer Clicks the "X" of the "Cancel" button to be redirected to the main menu

Expected result: Customer can add a product to their shopping cart if  the specified product exists in the specified store. If one or more of the fields don't match to the existing marketplace the product won't be added.

Test Status: **Pass**


**Test 5: Remove Product from Cart**

Steps:
1. Customer chooses the "View Shopping Cart" option and clicks "OK"
2. Customer views their shopping cart and clicks "OK"
3. Customer chooses to "Remove product from cart" through the dropdown menu
4. Customer clicks "OK"
5. Customer specifies the name of the product they want to remove and clicks "OK"
6. If customer wants to go back to the Main Menu they can click "Cancel" or "X"

Expected result: If the specified product name is in the Customer's shopping cart, then the product is removed from their shopping cart. Otherwise an error message is thrown that says that the product doesn't exist and can't be removed.

Test Status: **Pass**

**Test 6: Checkout Cart**

Steps:
1. Customer chooses the "View Shopping Cart" option and clicks "OK"
2. Customer views their shopping cart and clicks "OK"
3. Customer chooses to "Checkout cart" from the dropdown menu
4. Customer clicks "OK"
5. Customer can see the progress of their cart being purchased
6. Customer receives and error message if something went wrong or if their cart was empty
7. Customer receives a confirmation message if they were able to checkout the products successfully

Expected result: If the Customer's shopping cart has items that can be check out (i.e quantity desired does not exceed the quantity available in the seller's store) and purchased, checkout succeeds and the new items checked out are added to the Customer's purchase history. The quantity of the specified products are decremented from the "market.txt" file in the corresponding stores. If there is something wrong in the processing or the cart is empty the Customer is given a descriptive error message.

Test Status: **Pass**


**Test 7: Search for Product**

Steps:
1. Customer chooses the "Search for Product" option from the dropdown menu and clicks "OK"
3. Customer specifies their search paramater
4. Customer clicks "OK"
5. Customer can see the products that match their search
6. Customer can click the "OK" or "X" button to return to the main menu

Expected result: If the Customer's search parameter matches any of the terms in a product's name or description in the market.txt file then the products will appear in a table format.

Test Status: **Pass**


**Test 8: View Customer Shopping History**

Steps:
1. Customer chooses the "View Shopping History" option from the dropdown menu and clicks "OK"
2. Customer looks at their previous shopping history on Boilemakerr Bazaar
3. Customer can click the "OK" or "X" button to return to the main menu

Expected result: If the Customer's had made purchases in the past, it should display the product name, unit price, store name, quantity, customer username, and seller username

Test Status: **Pass**


**Test 9: Export Customer Shopping History**

Steps:
1. Customer chooses the "Export Shopping History" option from the dropdown menu and clicks "OK"
2. Customer specifies the directory they want the CSV file to be sent to
4. Customer can click the "OK" button to choose the specified directory
5. If an error occurs while generating the file or processing an error message is given to the user

Expected result: The Customer's purchase history is exported to the specified directory (locally) 

Test Status: **Pass**

**Test 10: Log Out**

Steps:
1. Customer chooses the "Log Out" option from the dropdown menu and clicks "OK"
2. Customer is given a Thank you Message for using Boilermaker Bazaar!
3. Customer can click the "X" button to exit the GUI

Expected result: The Customer's data from this interaction with the marketplace is stored so that when they revisit the changes persist.

Test Status: **Pass**



## Seller Tests: ##

**Test 1: View Marketplace (ascending and descending price order) and Return to Main Menu**

Steps:
1. Seller chooses the "View Marketplace" option and clicks "OK"
2. Seller chooses to Sort Marketplace by "Ascending Price" or "Descending Price" using the dropdown menu
3. Seller clicks "OK"
4. Once seller has viewed the marketplace in the sort they chose, they can click the "X" or "OK" to return to the main menu

Expected result: User views the overall Boilermaker Bazaar Marketplace sorted by Unit Price (either ascending or descending price order) and returns to the Main Menu

Test Status: **Pass**

**Test 2: View Marketplace (ascending and descending quantity order) and Return to Main Menu**

Steps:
1. Seller chooses the "View Marketplace" option and clicks "OK"
2. Seller chooses to Sort Marketplace by "Ascending Quantity" or "Descending Quantity" using the dropdown menu
3. Seller clicks "OK"
4. Once seller has viewed the marketplace in the sort they chose, they can click the "X" or "OK" to return to the main menu

Expected result: User views the overall Boilermaker Bazaar Marketplace sorted by Quantity (either ascending or descending order) and returns to the Main Menu

Test Status: **Pass**

**Test 3: View All Sales By Store**

Steps:
1. Seller selects the dropdown menu
2. Seller chooses the "View All Sales by Store" option and clicks "OK"
4. Seller clicks "OK"
5. Once seller has viewed their sales, they can click the "X" or "OK" to return to the main menu

Expected result: Seller can view the List of their seperate stores and all of the transactions that occured at each store. At the bottom of each store the Seller can view the Total Revenue generated by each store. This uses the purchases.txt file.

Test Status: **Pass**

**Test 4: Add a Product**

Steps:
1. Seller selects "Add a Product" from the dropdown menu
2. Seller inputs the Product Name, Price, Store Name, Quantity and enteres a Description for the new product
4. Seller clicks "OK"
5. Application verifies that the product specified doesn't currently exist in the specified store
6. If the product exists, Seller will receive an Error Message which specifies that the product already exists in their store
7. If the product doesn't exist, Seller will receive a confirmation message that the product has been added
8. Seller clicks the "X" or "OK" buttons to return to the main Seller menu

Expected result: Seller can add a new product to their store if the product doesn't currently exist in their product line. Seller doesn't get a message and verification is halted untill all of the required information is given.

Test Status: **Pass**

**Test 5: Edit a Product**

Steps:
1. Seller selects "Edit a Product" from the dropdown menu
2. Seller views a JTable and they can edit a field by double clicking on the cell (among Product Name, Product Price, Store Name, Quantity, Description)
5. Seller inputs the new value for the field they want to change
6. Seller clicks "OK"
7. Application verifies that the product specified exists within the product line of the specified store by the seller
9. If the product exists, Seller will receive an confirmation message which specifies that the product was modified successfully.
10. If the product doesn't exist, Seller will receive a an error message that the product can't be edited since it doesn't exist.
11. Seller can click "OK" or "X" to return to the Main Menu

Expected result: Seller can edit a field of any of their product in one of their stores if the product exists currently. The new information is reflected in the market.txt file once the product is edited.

Test Status: **Pass**

**Test 6: Delete a Product**

Steps:
1. Seller selects "Delete a Product" from the dropdown menu
2. Seller inputs the name of the product they would like to delete
3. Seller inputs the store name that the product belongs to
4. Application verifies that the product specified exists within the product line of the specified store by the seller
5. If the product exists, Seller will receive an confirmation message which specifies that the product deleted.
6. If the product doesn't exist, Seller will receive a an error message that the product can't be removed since it doesn't exist.
7. Seller can click "OK" or "X" to return to their Main Menu.

Expected result: Seller can remove a product from their product line in one of their stores if the product exists already. If the product doesn't exist or is a part of another seller's store, the user will receive an appropriate error message.

Test Status: **Pass**

**Test 7: View Store Statistics (Sort by List of Customers)**

Steps:
1. Seller selects "View Store Stats" from the dropdown main menu
2. Seller chooses the option "Sort By List of Customers" through the dropdown menu
3. Seller clicks "OK" to return to the main menu

Expected result: Seller can view their store statistics in terms of how many products each customer has bought from their stores (cumulatively)  This will read the purchases from the purchases.txt file.

Test Status: **Pass**

**Test 8: View Store Statistics (Sort by Products Bought)**

Steps:
1. Seller selects "View Store Stats" from the dropdown main menu
2. Seller chooses the option "Sort By Products Bought" through the dropdown menu
3. Seller clicks "OK" to return to the main menu

Expected result: Seller can view their store statistics by in terms of how many times each product has been bought from their stores. This will read the purchases from the purchases.txt file. (cumulatively). 

Test Status: **Pass**


**Test 9: Import a CSV file**

Steps:
1. Seller selects "Import/Export" from the dropdown main menu
2. Seller chooses the option "Import to be added to my stores" through the dropdown menu
3. Seller is redirected to their local directories and can choose a CSV file to upload (default for file type is CSV)
4. Seller chooses a CSV file they want to upload (assumed has the approrpriate information) and clicks "OK"
5. Seller receives a message that the information from the CSV has successfully been uploaded to Marketplace or receives an error message that something went wrong
7. Seller clicks "OK"

Expected result:
Seller can import a CSV file with new product information that they want to add to the Marketplace of the application.
If the appropriate file is given processing should happen; otherwise, file is not imported to the application and marketplace is not modified.
For example, if a PDF document is attempted to be imported, the program terminates the processing and informs the seller about the error.

Test Status: **Fails - Doesn't add the items to the CSV file**

**Test 10: Export a CSV file**

Steps:
1. Seller selects "Import/Export Products" from the dropdown main menu
2. Seller chooses the option "Export the products from my stores" through the dropdown menu
3. Seller selects the "OK" button
4. Seller inputs the store name that they want their product information to be exported from
5. Seller specifies the name of the CSV file they want the information to export to
6. Application processes the information and exports the information to the specified CSV file in the Seller's local file folder
7. Seller receives a message that the "File was exported successfully" 
8. Seller clicks "OK" to return to the main menu

Expected result: Seller can export all of the product information from the store they specified to a CSV file that they specified.

Test Status: **Pass**


**Test 11: View Customer Shopping Carts for my Stores**

Steps:
1. Seller selects "View Customer Shopping Carts " from the dropdown main menu
2. Seller selects the "OK" button
8. Seller clicks "OK" or "X" to return to the main menu
Expected result: Seller can view a list of the customer shopping carts that contain products from their stores. The information provided includes Store Name, Customer Name, Product Name, Product Unit Price, Quantity, and Product Description

Test Status: **Pass**

**Test 12: Log Out**

Steps:
1. Seller selects "Log Out" to exit from the marketplace application from the dropdown main menu
2. Seller selects the "OK" button
3. Seller receives a Thank You message
4. Seller clicks the "OK" button or the "X" 
5. The GUITest.java will terminate. Server.java continues to run unless manually terminated
Expected result: Seller can log out of Boilermaker Bazaar successfully.

Test Status: **Pass**










