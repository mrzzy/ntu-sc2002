
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
        -String[] categories
        
        +name() String
        +price() double 
        +description() String 
        +categories() String[]
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
    NewStatus --|> OrderStatus: implements
    
    class PickupStatus {
        -LocalDateTime expiresOn
        +step(timestamp) OrderStatus 
        +collect() OrderStatus 
    }
    PickupStatus --|> OrderStatus: implements
    
    class CanceledStatus {
    }
    CanceledStatus --|> OrderStatus: implements
    
    class CompletedStatus {
    }
    CompletedStatus --|> OrderStatus: implements

    %% Payment methods
    class PaymentMethod {
        <<interface>>
        +pay(int amountCents) boolean*
    }

    class PaypalMethod {
        -String email
        +pay(int amountCents) boolean 
    }
    PaypalMethod --|> PaymentMethod: implements

    class BankCardMethod {
        -String number
        -Date expiry
        -String cvc
    }
    BankCardMethod --|> PaymentMethod: implements

    %% Actions
    class Action {
        +title() String 
        +execute(Scanner in, Chain chain) Chain 
    }
    Action --> Chain: applied on

    class OrderMethod {
        <<enumeration>>
        %% placing order includes menu browsing
        PLACE,
        %% collecting an order includes payment processing
        COLLECT,
        LIST,
        VIEW,
        PROCESS,
        GET_STATUS
    }
    
    class OrderAction {
        -OrderMethod method
        +title() String 
        +execute(Scanner in, Chain chain) Chain 
    }
    OrderAction --|> Action: implements
    OrderAction *-- OrderMethod
    
    class StaffMethod {
        <<enumeration>>
        PROMOTE,
        ADD,
        REMOVE,
        LIST,
    }

    class StaffAction {
        -StaffMethod method
        +title() String 
        +execute(Scanner in, Chain chain) Chain 
    }
    StaffAction --|> Action: implements
    StaffAction *-- StaffMethod

    class BranchMethod {
        <<enumeration>>
        ASSIGN,
        OPEN,
        CLOSE,
        LIST_STAFF,
    }
    
    class BranchAction {
        -BranchMethod method
        +title() String 
        +execute(Scanner in, Chain chain) Chain 
    }
    BranchAction --|> Action: implements
    BranchAction *-- BranchMethod

    class PaymentActionMethod {
        <<enumeration>>
        ADD,
        REMOVE
    }
    
    class PaymentAction {
        -PaymentActionMethod method
        +title() String
        +execute(Scanner in, Chain chain) Chain 
    }
    PaymentAction --|> Action: implements
    PaymentAction *-- PaymentActionMethod

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
    CustomerRole --|> Role: implements

    class StaffRole {
        +char code()
        +getActions() Set~Action~
    }
    StaffRole --|> Role: implements
    
    class ManagerRole {
        +char code()
        +getActions() Set~Action~
    }
    ManagerRole --|> Role: implements

    class AdminRole {
        +char code()
        +getActions() Set~Action~
    }
    AdminRole --|> Role: implements

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
        +getMenu() Set~User~
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
        -Chain chain
        +main(String[] args)$
    }
    Application "1" *-- "*" Chain: hosted by
```

## Design Considerations

### Order Status: State Pattern

### Payment Method:  Strategy Pattern

### Action: Command Pattern

### Persistence: Java Serialisation
