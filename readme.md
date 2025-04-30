# 🛒 Java Servlet Shopping Catalog Project

This is a simple e-commerce-like shopping catalog website built using **Java Servlets**, deployed on **Tomcat 9**.

## 💡 Features
- User Registration and Login with session management
- Shopping Catalog page (view items, add to cart)
- Shopping Cart page (view selected items)
- Data persistence via serialization

## 🚀 Getting Started
1. Install Java 8 and Tomcat 9.
2. Download the project and place it into your Tomcat `webapps/` directory.
3. Compile Java source files into `WEB-INF/classes/`.
4. Start Tomcat and visit: http://localhost:8080/catalog/

## 📁 Project Structure
catalog/ 
├── catalog.html 
├── login.html 
├── register.html 
├── styles.css
├── WEB-INF/ │ 
├── web.xml │ 
├── context.xml │ 
├── src/ (Java source files) 
│ └── database/ (persistence for users and carts)


## ⚠️ Note
- Database files (`users.txt`, `carts.txt`) are **excluded** from version control for security reasons.
- Example sample data can be provided if needed.

---

## 🛠️ Technologies Used
- Java Servlets (Java 8)
- Tomcat 9
- HTML / CSS
- Java Serialization for persistence
