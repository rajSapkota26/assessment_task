# assessment_task

<br/> 
# Built With
  -Kotlin
  -Coroutines
  -jetpack compose
  -Android Architecture Components
  -Stateflow
  -Flow
  -ViewModel
  -Room
  -Jetpack Navigation
  -SharedPreference
  -Material Components for Android
<br/>

## Package Structure  
    
    com.example.assessment        # Root Package
    ├── utils                     # Utility classes
    |    ├── generic              # Base Response class for error, loading and success handle
    |    ├── navigation           # nav graph and all screen route name
    |    ├── session              # user login session
    |    ├── dateTime             # date Time utility classes
    ├── service_laayer            # For data handling.
    │   ├── database              # Database Instance
    |   ├── repository            # Implementation of dao  
    |   |── service  
    |       |── dao               # Data Access Object for Room 
    |
    ├── chart                     # Graph model
    |
    ├── uiComponent               # Reuseable composable funtions
    |
    ├── ui  
    │   ├── theme                # theme releted class like color, shape, typography
    ├── feature                  # All feature inside app
    │   ├── splash               # Splash root folder
    |   │   ├── screen           # Splash view
    |   │   └── vm               # 
    |   │   ├── event            #
    |   │   ├── dto              #      
    │   ├── Dashboard            # Dashboard root folder
    |   │   ├── screen           # ui part of dashboard
    |   │   └── vm               # viewmodel
    |   │   ├── event            # all event triggred on dashboard
    |   │   ├── state            # state of dashboard 
    |   │   ├── dto              # entity or dto      
    │   ├── login                # Login  root folder
    |   │   ├── screen           # 
    |   │   └── vm               # 
    |   │   ├── event            # 
    |   │   ├── state            # 
    |   │   ├── dto              #     
    │   ├── register             # Register/Create Account  root folder
    |   │   ├── screen           # 
    |   │   │   ├── Otp          #     
    |   │   └── vm               #
    |   │   ├── event            # 
    |   │   ├── state            #
    |   │   ├── dto              #    
    <br/>
