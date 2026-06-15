# NumeriCalc — Scientific & Numerical Methods Calculator
### JavaFX Desktop Application

---

## 📁 Project Structure

```
NumericalCalc/
├── pom.xml
└── src/main/
    ├── java/
    │   ├── module-info.java
    │   └── com/numerical/
    │       ├── MainApp.java                    ← Entry point
    │       ├── controller/
    │       │   └── MainController.java         ← All UI logic
    │       └── model/
    │           └── NumericalEngine.java        ← All math algorithms
    └── resources/com/numerical/
        ├── view/
        │   └── MainView.fxml                   ← UI layout
        └── css/
            └── style.css                       ← Dark theme
```

---

## ✅ Prerequisites

| Tool | Version | Download |
|------|---------|----------|
| JDK  | 21+     | https://adoptium.net |
| Maven | 3.9+  | https://maven.apache.org |

> JavaFX is downloaded automatically by Maven — no manual install needed.

---

## 🚀 Build & Run

### Option 1 — Maven (recommended)
```bash
cd NumericalCalc
mvn clean javafx:run
```

### Option 2 — Build fat JAR then run
```bash
cd NumericalCalc
mvn clean package -DskipTests
java -jar target/NumericalCalc-1.0.0.jar
```

### Option 3 — IntelliJ IDEA
1. Open → Select the `NumericalCalc` folder
2. Maven auto-imports (trust project)
3. Run `MainApp.java`

### Option 4 — VS Code
1. Install "Extension Pack for Java"
2. Open `NumericalCalc` folder
3. Click ▶ on `MainApp.java`

---

## 🧮 Calculator Modes

### Standard Calculator
Full scientific calculator with:
- Basic arithmetic (+, −, ×, ÷)
- Scientific: sin, cos, tan, ln, √, x², xⁿ, 1/x, %
- Backspace, clear, negate

### Numerical Methods (switch via MODE dropdown)

| Tab | Method | Inputs |
|-----|--------|--------|
| **Direct Interp.** | Linear interpolation between 2 points | x₀, y₀, x₁, y₁, x |
| **Lagrange** | Lagrange polynomial interpolation | comma-separated x and y arrays, x |
| **Simp. 1/3** | Simpson's 1/3 rule integration | f(x), a, b, n |
| **Simp. 3/8** | Simpson's 3/8 rule integration | f(x), a, b, n |
| **Euler** | Euler's method for ODEs | f(x,y), x₀, y₀, h, steps |
| **Error** | Absolute/relative/% error analysis | exact value, approximate value |

---

## 📐 Function Syntax (for f(x) and f(x,y) fields)

```
x^2 + 3*x - 1       → x² + 3x − 1
sin(x) + cos(x)      → sin(x) + cos(x)
sqrt(x^2 + 1)        → √(x² + 1)
ln(x) + exp(x)       → ln(x) + eˣ
x*y + x^2            → xy + x²  (Euler mode)
```

Supported functions: `sin`, `cos`, `tan`, `asin`, `acos`, `atan`, `ln`, `log`, `sqrt`, `exp`, `abs`
Constants: `pi`, `e`

---

## 💡 Example Inputs

### Lagrange Interpolation
- x values: `1, 2, 4`
- y values: `1, 4, 16`
- Interpolate at: `3`
- Expected: ≈ 9.0

### Simpson's 1/3 Rule
- f(x): `x^2`
- a: `0`, b: `1`, n: `4`
- Expected: ≈ 0.333333

### Euler's Method
- f(x,y): `x + y`
- x₀: `0`, y₀: `1`, h: `0.1`, steps: `5`

### Error Analysis
- Exact: `3.14159265`
- Approx: `3.14`

---

## 🎨 Theme
Dark space-blue theme with red accent (`#e94560`) and cyan output text (`#64ffda`).
