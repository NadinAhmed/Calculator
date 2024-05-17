# Calculator Application

## Overview
This project is a calculator application developed for Android using Kotlin and XML. The application supports multi-operator calculations and the use of parentheses, implemented using the Shunting-Yard Algorithm. The architecture of the application follows the MVVM (Model-View-ViewModel) pattern.

## Features
- **Basic Arithmetic Operations**: Addition, Subtraction, Multiplication, and Division.
- **Multi-Operator Calculations**: Supports calculations involving multiple operators.
- **Parentheses Support**: Users can use parentheses to dictate the order of operations.
- **Clean UI Design:** Intuitive layout with a clear display and easy-to-use buttons using grid layout.
- **History Feature**: Keeps track of previous calculations.

## Technologies Used
- **Programming Language**: Kotlin
- **Layout Design**: XML
- **Architecture**: MVVM
- **Algorithm**: Shunting-Yard Algorithm for expression evaluation

## Application Structure

### MVVM Architecture
- **Model**: Represents the data and business logic of the application. In this calculator app, the model handles the computation logic using the Shunting-Yard Algorithm.
- **View**: The UI layer of the application, defined in XML layouts. The view observes the ViewModel for data updates.
- **ViewModel**: Acts as a bridge between the Model and the View. It holds the UI-related data that the view will use and observes changes in the model to update the UI accordingly.

## Acknowledgements
- [Shunting-Yard Algorithm](https://tylerpexton-70687.medium.com/the-shunting-yard-algorithm-b840844141b2)
- [Android MVVM Architecture](https://developer.android.com/topic/libraries/architecture/viewmodel)

## Contact
For any inquiries or feedback, please contact (nadinahmed316@gmail.com).

Happy coding! 🚀
