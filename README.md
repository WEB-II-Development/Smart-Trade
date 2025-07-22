# 💼 Smart Trade
Smart Trade is a clean and responsive user interface for a **user authentication system**, designed to be integrated into a Java EE web application. This project contains the frontend build along with deployment configuration using Apache Ant.

---

## 🔍 Project Overview

This project appears to be a **web-based authentication module** with the following key components:

- ✅ **Sign In, Sign Up, and Account Verification** HTML pages
- ✅ **Responsive and modern design** using Bootstrap 4
- ✅ **Custom and vendor CSS** for styling and layout
- 🔧 `build.xml` suggests this is meant to be deployed in a **Java EE environment** (e.g., GlassFish)

---

## 📜 Technologies Used

- **HTML5 / CSS3**
- **Bootstrap 4**
- **Font Awesome**
- **Java EE Deployment (via Apache Ant)**

---
# 2025-07-14

### Create A Database with ER Diagram

<img width="1280" height="828" alt="image" src="https://github.com/user-attachments/assets/adce8317-e581-4693-9de4-0fed2f301ff0" />


# 2025-07-15

## 🔐 **Sign-Up Process Overview**

### 🧠 Expected Behavior (Frontend)

The **Sign-Up Form** in this project is designed to collect user credentials for account creation. Here's how it typically functions:

### ✅ On Form Submission
- Validates input fields (e.g., email format, password match)
- Sends data to a **Java EE backend** (e.g., via JSP/Servlet or REST API)
- Displays error or success notifications (possibly using `notification.css`)
- Redirects the user to the **verify-account.html** page upon successful registration

### 🔒 Security & UX Enhancements (Assumed)
- Password fields are **masked** for privacy
- Fully **responsive design** powered by Bootstrap
- May include the following:
  - Password strength indicators
  - Email uniqueness validation
  - Friendly prompts for user feedback

### 🔄 Next Steps After Sign-Up
After completing the sign-up process, users are:
1. Redirected to the **verify-account.html** page  
2. Prompted to confirm their email via an OTP or verification link  
3. Allowed to **log in** using `sign-in.html` only after successful verification

* Sign-Up.html
<img width="1268" height="863" alt="image" src="https://github.com/user-attachments/assets/1f8a467e-83c3-416b-a4c1-12e6c0eed4dd" />

* verify-account.html
<img width="1235" height="857" alt="image" src="https://github.com/user-attachments/assets/f0bbb873-d0dd-49b9-a417-a6cc8f2ffec8" />

# 2025-07-16

## 🔐 Sign In Process
**File:** `SignIn.java`

### How it works:
- The user enters **email/username and password**.
- The system uses **Hibernate** to connect to the database and check if a matching user exists.

#### If the user is found:
- A **session** is created for the user.
- A **success response** is sent.

#### If no match:
- A response is sent saying:  
  ❌ **"Invalid email or password."**

---
* sign-in.html

<img width="1219" height="854" alt="image" src="https://github.com/user-attachments/assets/c6a1a07f-c6aa-4ef3-bd38-e6fbeba2a9d9" />

## ✅ Verification Process
**File:** `VerifyAccount.java`

### How it works:
- After signing up, a **verification code** is sent to the user's email.
- The user enters this code on the `verify-account.html` page.

#### The system checks the code:
- ✅ If it **matches**: The account is marked as **verified** in the database.
- ❌ If it **doesn't match**: An **error message** is shown.

---

## 🧰 Session Filter
**File:** `SessionFilter.java`

### How it works:
- This filter checks if a user is **logged in** before accessing protected pages.

#### If not logged in:
- The user is **redirected** to the `sign-in.html` page.
- 

# 2025-07-17

## 🔐 Sign Out

- Triggered by a button or link calling the `signOut()` function.
- Functionality:
  - Clears stored user data from `localStorage`.
  - Redirects the user to `sign-in.html`.

## 👤 Profile Details

- Page: my-account.html

- On page load, the loadData() function is executed.

- This function fetches and displays stored user data from localStorage into input fields.

## 💾 Save Changes

- When the Save button is clicked, the `saveChanges()` function is triggered.

- Functionality:

  - Reads updated values from input fields.

  - (Optionally) Validates the data.

  - Updates the user data in localStorage.

* my-account.html

<img width="1593" height="895" alt="image" src="https://github.com/user-attachments/assets/e3803b94-e2d6-49d9-bf99-05f3e73a769a" />

# 2025-07-18

## 🛒 Product Adding 

  - Data Parsing Only (form + logic)
    
* my-account.html
  
<img width="1252" height="750" alt="image" src="https://github.com/user-attachments/assets/74f37169-a034-4e81-8008-076a0f829941" />

## 👤 Saving Account Details 

- Complete Process (Insert / Update form + backend if any)

## 🗺️ Viewing Address 

- Complete Process (Viewing Shipping Address UI + data loading)
  
* my-account.html
  
<img width="1607" height="727" alt="image" src="https://github.com/user-attachments/assets/819131fa-8dd2-4ee9-b6fb-176d0d2bca22" />

# 2025-07-21

## 🛍️ Single Product View

This module allows users to view detailed information about a specific product in the store, including dynamic interaction like image preview, color display, and related product suggestions.

### 🔧 How It Works

#### Page Initialization
- JavaScript reads the product ID from the URL (e.g., `?id=2`).

#### Data Fetching
- Sends an asynchronous request to `LoadSingleProduct` backend API to retrieve product details and related items.

#### UI Rendering
- Main product images and metadata (title, model, brand, price, color, storage, description) are displayed.
- Color is visually represented using inline CSS for borders and backgrounds.

#### Cart Functionality
- Users can specify quantity and click "Add to Cart", triggering `addToCart(id, qty)`.

#### Similar Products Carousel
- A list of related products (`productList`) is dynamically generated and shown in a responsive slick slider.
- Each similar product card includes title, price, storage, color, and quick actions.

### 📁 File(s) Involved
- `single-product.html`
- `single-product.js`
- `LoadSingleProduct` (backend API endpoint)
- `/product-images/{id}/image{1,2,3}.png`

* single-product.html
<img width="1654" height="834" alt="image" src="https://github.com/user-attachments/assets/42c174a8-225c-4d09-b964-5e3301656746" />

<img width="1558" height="860" alt="image" src="https://github.com/user-attachments/assets/14076e41-831f-41e4-9fca-c880e6865a56" />

# 2025-07-22

## Product Adding process

### ✔ Functionality
- Receives form data from a product submission form.

- Validates and converts data (brandId, modelId, price, etc.).

- Loads related Hibernate entities (Model, Storage, Color, etc.).

- Creates and saves a Product entity using Hibernate.

- Handles file uploads: saves `image1.png`, `image2.png`, `image3.png` in `product-images/{id}/`.

- Responds with JSON status using Gson.

### 🔧 Suggested Improvements:
- Currently, validation logic is commented out. Enable it for proper data integrity.

- Return a success status (responJsonObject.addProperty("status", true);) after committing the transaction.

- Return a productId in response for frontend navigation or redirection. 

## 🔍 Product Filtering / Search (Java - Hibernate)

This feature enables dynamic product search using Hibernate criteria queries.

### 🔧 How It Works

#### ➕ New Servlet: `/SearchProducts`
- Accepts `GET` requests with `q` parameter (search keyword).
- Uses Hibernate to find products with title matching keyword.
- Excludes sensitive user data from response.
- Returns JSON object with matching products.

* search.html
  
<img width="508" height="857" alt="image" src="https://github.com/user-attachments/assets/863f1d05-3ba0-4be5-86bc-df1acc2c3bd9" />

