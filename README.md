# AiReceipt for Android-Study-Jams

Receipts using Machine Learning

<b> Problem Statement: </b>

Over a period of time, we are using traditional bills and receipts either handwritten or printed and it results into use a lots of paper, raw material and man power. This is a serious concern because more than 3 Million and 34 Billion water is used to make paper receipts in the world and as you know paper receipts are outdated and hard to track. This is a very big problem for the upcoming generation as we should switch to sustainable development and manage our resources very well.
Technology plays a vital role in day-to-day life activities which in turn made great changes in many work fields and out of them Mobile Application is one of the major developments. Mobile Application can be used effectively for this job as they are widely used and are known for easy access.

<b> Proposed Solution : </b>

This project proposes a “AI Receipt” to make bill in the mobile either manually or by recognising some text by using ML Kit provided by Firebase and it will also keep track of the receipt made by the shopkeeper and it can be shared in the form of image to the customer. It can also be used in malls as this will reduce the manpower and usage of numerours computers which scan(using barcode) and make bills.

This project is very beneficial for small business owners as they can make bill fast and track it easily. The features of this application is that the application stores the details of inventory, it recognises the text from the grocery item packet and autofill the bill maker tab, so it reduces the time. If some item is not present in the inventory or if the item is not recognized by ML kit, then the user can make bill manually also. The bill made can be shared with the customer, so it will serve as an e-bill and the bill will also be stored in the application itself for the shopkeeper to track the bills and manage the inventory properly. Currently it is in the testing with the develper as more imporovements and bugs need to be solved and prepare it for production.


    
### SPLASH SCREEN
    
![0](https://user-images.githubusercontent.com/73891260/148679514-be945927-7e68-461c-b07b-520dd6add795.jpeg)
    
### HOME SCREEN
    
![1](https://user-images.githubusercontent.com/73891260/148679528-a9820d50-2b76-465c-8c71-a456a51f4359.jpeg)
    
### PREVIOUS BILLS SCREEN
    
![2](https://user-images.githubusercontent.com/73891260/148679533-5d3b42d4-77cc-44ff-b2d0-028fa176b538.jpeg) 
    
### INVENTORY SCREEN
    
![3](https://user-images.githubusercontent.com/73891260/148679540-c553495e-1d64-44b7-85d8-e2c9eb394ead.jpeg) 
    
### HOME SCREEN (BILL CREATION- Item 1)
        
![4](https://user-images.githubusercontent.com/73891260/148679554-f9c0cd25-a111-4aaa-810f-61154e6a7c16.jpeg) 
     
### HOME SCREEN (BILL CREATION- Item 2)
       
![5](https://user-images.githubusercontent.com/73891260/148679544-a2162654-bb30-4aed-aaa8-4ff342ac867b.jpeg) 
    
### HOME SCREEN (AUTO FILL from ML Text)
    
![6](https://user-images.githubusercontent.com/73891260/148679557-df559492-81bd-41c4-86e4-455d2ececf63.jpeg) 
  
### SHARE BILL (Tapped on share icon)
     
![7](https://user-images.githubusercontent.com/73891260/148679563-d7780812-f7a5-4c9d-8a3b-f535ffb7f5ac.jpeg) 
   
### PREVIOUS BILL (Tapped on the bill no.)
    
![8](https://user-images.githubusercontent.com/73891260/148679567-071094e0-3b4c-48f9-ab8e-c711c853063c.jpeg)


    	  	
**Functionality & Concepts used :**

- The App has a very simple and interactive interface which helps the user to create, share and track bills easily. Following are few android concepts used to achieve the functionalities in app : 

- **Constraint Layout** : Most of the activities in the app uses a flexible constraint layout, which is easy to handle for different screen sizes.

- **Simple & Easy Views Design** : Use of familiar audience EditText with hints and interactive buttons made it easier for users to make bills as they don't need to go through any documentation to use this application. Apps also uses App Navigation to switch between different screens.

- **RecyclerView** : To present the list of previous bills, we used the efficient recyclerview.

- **Firebase ML Kit** : The app has used the firebase ML kit to recognise the text and auto fill the text box of item name, when user tap on the "NEW ITEM" button.

- **Coroutines** : They are used in Inventory View Model so that it can do the background threading for the internal storage request for Room persistance library.

- **LiveData & Room Database** : We are also using LiveData to update & observe any changes while creating the bill, as the user may have to see the inventory to check items, so we used LiveData while bill creation so that it stores the vakue and when it is resumed it show the already added item in the home screen. Room Database is used to store the inventory details as well as the previous bill details. We can do all the CRED operations in the Room Database.

<b> Application Link & Future Scope : </b>

The app is currently in the Alpha testing phase with the developer and the team, You can access the app : [AiReceipt](https://drive.google.com/drive/folders/1ABZEsP0auHVeNxjVTHSJmtTz0I-BMAg7?usp=sharing).

Once the app is fully tested and functional, we will go for beta testing with local business owners and test with near shops, we plan to talk to neighboring shopkeepers and grocery sellers also to propose this app idea and collaborate with them for their convinence. We aim to develop more features in the application so instead of manual bill sharing the consumer and retailer both present at the same platform, the bills can be made using blockchain or web 3.0 which will be in very secure network and data loss won't be there and tracking of bill will be easy. After this feature is done we willextend our collaboration with shopping malls to contribute in large scale. The collaboration flow will go from neighbours to wards then to district then to state and after that national and internations collaborations. We will also look for government support as this is solution for sustainable development.
