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


