
## Project Structure

```
Automation/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── automation/
│                   └── FlipkartAutomation.java
├── firefox-profile/              # Firefox profile directory
├── target/                       # Compiled classes and dependencies
├── pom.xml                      # Maven configuration
└── README.md                    # Project documentation
```

## Configuration

### Firefox Profile Settings

The automation uses a custom Firefox profile located in `firefox-profile/` directory with optimized settings for:
- Cache management
- Session storage
- Cookie persistence
- Security configurations
- Performance optimization


1. **Clean and compile the project**
   ```bash
   mvn clean compile
   ```

2. **Compile with dependencies**
   ```bash
   mvn clean package
   ```

3. **Verify compilation**
   ```bash
   ls target/classes/com/automation/
   ```

### Using Command Line (Alternative)

1. **Set up classpath with Maven dependencies**
   ```bash
   mvn dependency:build-classpath -Dmdep.outputFile=classpath.txt
   ```

2. **Compile manually**
   ```bash
   javac -cp "$(cat classpath.txt)" src/main/java/com/automation/FlipkartAutomation.java -d target/classes
   ```

## ▶️ How to Run

### Method 1: Using Maven Exec Plugin (Recommended)

```bash
mvn clean compile exec:java -Dexec.mainClass="com.automation.FlipkartAutomation"
```

### Method 2: Using Maven with Compiled Classes

```bash
# First compile
mvn clean compile

# Then run
mvn exec:java -Dexec.mainClass="com.automation.FlipkartAutomation"
```

### Method 3: Using Java Command Directly

```bash
# Compile first
mvn clean package

# Get classpath
mvn dependency:build-classpath -Dmdep.outputFile=classpath.txt

# Run with full classpath
java -cp "target/classes:$(cat classpath.txt)" com.automation.FlipkartAutomation
```

### Method 4: IDE Execution

1. Import the project into your IDE (IntelliJ IDEA, Eclipse, etc.)
2. Ensure Maven dependencies are resolved
3. Right-click on `FlipkartAutomation.java`
4. Select "Run main()"

## 🔄 Workflow

The automation follows this sequence:

1. **Browser Setup**
   - Initialize Firefox with custom profile
   - Configure browser preferences
   - Maximize browser window

2. **Navigation**
   - Navigate to Flipkart.com
   - Handle login popup (close if appears)

3. **Product Search**
   - Search for specified product ("trimmer men")
   - Wait for search results to load

4. **Product Selection**
   - Select the first product from search results
   - Navigate to product details page

5. **Checkout Process**
   - Click "Buy Now" or "Add to Cart"
   - Proceed to checkout/delivery page

6. **Address & Delivery**
   - Click "Deliver Here" button
   - Continue to next step

7. **Payment Information**
   - Select Credit/Debit card option
   - Fill in demo card details:
     - Card Number: 4591560066181405
     - Expiry: 04/26
     - CVV: 405

8. **Completion**
   - Wait for user input before closing browser

## 🛠️ Technologies Used

- **Java 8+** - Core programming language
- **Selenium WebDriver 4.x** - Browser automation
- **WebDriverManager** - Automatic driver management
- **Firefox/Geckodriver** - Browser and driver
- **Maven** - Build and dependency management
- **JUnit** (optional) - Testing framework

### Key Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.11.0</version>
    </dependency>
    <dependency>
        <groupId>io.github.bonigarcia</groupId>
        <artifactId>webdrivermanager</artifactId>
        <version>5.4.1</version>
    </dependency>
</dependencies>
```

## 🔧 Troubleshooting

### Common Issues and Solutions

1. **Firefox not found**
   ```bash
   # Install Firefox or update PATH
   # On macOS: brew install firefox
   # On Ubuntu: sudo apt install firefox
   ```

2. **Maven dependencies not resolved**
   ```bash
   mvn clean install -U
   ```

3. **Compilation errors**
   ```bash
   # Check Java version
   java -version
   
   # Ensure JAVA_HOME is set
   echo $JAVA_HOME
   ```

4. **Element not found errors**
   - Flipkart UI may have changed
   - Check browser console for element selectors
   - Update selectors in the code if necessary

5. **Firefox profile issues**
   ```bash
   # Delete and recreate profile directory
   rm -rf firefox-profile/
   mkdir firefox-profile
   ```

### Debug Mode

To run with verbose output, modify the code to add logging or use:

```bash
mvn exec:java -Dexec.mainClass="com.automation.FlipkartAutomation" -X
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request
