# SC2002 Assignment Class Diagram
```mermaid
---
title: Fastfood Ordering Managment System
---
classDiagram
    class Item {
        -String name
        -double price
        -String description
        -boolean available
        -String[] categories

        +name() String
        +price() double 
        +description() String 
        +categories() String[]
        +isAvailable() boolean
    }

    class Order {
        -List~Item~ items
        -DiningOption diningOption
        -OrderStatus status
    
        +getItems() List~Item~
        +getDiningOption() DiningOption 
        +getStatus() OrderStatus 
        +process()
    }
    Order "1" o-- "*" Item: contains
    Order "1" *-- "1" DiningOption: selects
    Order "1" *-- "1" OrderStatus: status
    
    class DiningOption {
        <<enumeration>>
        DINE_IN
        TAKEOUT
    }

    %% Order Statuses
    class OrderStatus {
        <<interface>>
        +step(timestamp) OrderStatus* 
        +process() OrderStatus 
        +collect() OrderStatus 
    }

    class NewStatus {
        +process() OrderStatus 
    }
    NewStatus ..|> OrderStatus: implements
    
    class PickupStatus {
        -LocalDateTime expiresOn
        +step(timestamp) OrderStatus 
        +collect() OrderStatus 
    }
    PickupStatus ..|> OrderStatus: implements
    
    class CanceledStatus {
    }
    CanceledStatus ..|> OrderStatus: implements
    
    class CompletedStatus {
    }
    CompletedStatus ..|> OrderStatus: implements

    %% Payment methods
    class PaymentMethod {
        <<interface>>
        +pay(int amountCents) boolean*
    }

    class PaypalMethod {
        -String email
        +pay(int amountCents) boolean 
    }
    PaypalMethod ..|> PaymentMethod: implements

    class BankCardMethod {
        -String number
        -Date expiry
        -String cvc
    }
    BankCardMethod ..|> PaymentMethod: implements

    %% Actions
    class Action {
        <<interface>>
        +title() String 
        +execute(Scanner in, Chain chain) Chain 
    }
    Action --> Chain: applied on

    class ViewMenuAction {
        +title() String 
        +execute(Scanner in, Chain chain) Chain 
    }
    ViewMenuAction ..|> Action: implements
    
    class CustomerMethod {
        <<enumeration>>
        ORDER,
        GET_STATUS,
        PICKUP
    }
    
    class CustomerAction {
        -CustomerMethod method
        +title() String 
        +execute(Scanner in, Chain chain) Chain 
    }
    CustomerAction ..|> Action: implements
    CustomerAction *-- CustomerMethod

    class StaffMethod {
        <<enumeration>>
        SET_STATUS,
        VIEW_ORDER
    }

    class StaffAction {
        -StaffMethod method
        +title() String 
        +execute(Scanner in, Chain chain) Chain 
    }
    StaffAction ..|> Action: implements
    StaffAction *-- StaffMethod
    
    class ManagerMethod {
        <<enumeration>>
        ADD_MENU,
        EDIT_MENU,
        REMOVE_MENU
        LIST_STAFF_BRANCH
    }

    class ManagerAction {
        -ManagerMethod method
        +title() String 
        +execute(Scanner in, Chain chain) Chain 
    }
    ManagerAction ..|> Action: implements
    ManagerAction *-- ManagerMethod
    
    class AdminMethod {
        <<enumeration>>
        OPEN_BRANCH,
        CLOSE_BRANCH,
        ADD_PAYMENT,
        REMOVE_PAYMENT,
        LIST_STAFF_ALL,
        ADD_STAFF,
        REMOVE_STAFF,
        ASSIGN_STAFF
    }
    
    class AdminAction {
        -AdminMethod method
        +title() String 
        +execute(Scanner in, Chain chain) Chain 
    }
    AdminAction ..|> Action: implements
    AdminAction *-- AdminMethod

    %% Identity & Access
    class Role {
        <<interface>>
        +code() char 
        +getActions() Set~Action~
    }
    Role "0..1" o-- "*" Action: allowed

    class CustomerRole {
        +char code()
        +getActions() Set~Action~
    }
    CustomerRole ..|> Role: implements

    class StaffRole {
        +char code()
        +getActions() Set~Action~
    }
    StaffRole ..|> Role: implements
    
    class ManagerRole {
        +char code()
        +getActions() Set~Action~
    }
    ManagerRole ..|> Role: implements

    class AdminRole {
        +char code()
        +getActions() Set~Action~
    }
    AdminRole ..|> Role: implements

    class User {
        -String username
        -String password
        -Role role
        +login(String username, String Password) boolean 
        +setRole(Role role)
    }
    User o-- Role: assigned

    %% Fast Food Chain & Branch
    class Branch {
        -String name
        -String location
        -Set~User~ staff
        -Set~Item~ menu

        +assign(User staff)
        +remove(User staff)
        +getName() String 
        +getLocation() String 
        +getStaff() List~User~
        +getMenu() Set~Item~
    }
    Branch "0..1" o-- "*" User: assigned
    Branch "1" *-- "*" Item: offers

    class Chain {
        -User admin
        -Set~User~ staff
        -Set~Branch~ branches
        -Set~PaymentMethod~ paymentMethods
        
        +getBranches() List~Branch~
        +getStaff() Set~User~
        +getPaymentMethods() Set~PaymentMethod~
    }
    Chain "1" *-- "*" User: employs
    Chain "1" *-- "1" User: adminsters
    Chain "1" *-- "*" Branch: branches
    Chain "1" *-- "*" PaymentMethod: supports

    %% Application
    class Application {
        -Role role
        -Optional~User~ user
        -Chain chain
        +main(String[] args)$
    }
    Application "1" *-- "*" Chain: hosted by
```

## Design Considerations
### Order Status: State Pattern

### Payment Method & Action: Strategy Pattern

### Persistence: Java Serialisation
