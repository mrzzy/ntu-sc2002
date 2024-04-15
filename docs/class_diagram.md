# Class Diagram
```mermaid
---
title: Fastfood Ordering Management System
---
classDiagram
    class Item {
        -String name
        -double price
        -String description
        -boolean available
        -String category

        +name() String
        +price() double 
        +description() String 
        +categories() String
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
        +collect()
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
        -Date expiresOn
        +step(Date timestamp) OrderStatus 
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
    
    class Payment {
        -String name
        +getName() String;
        +pay(int amountCents) boolean 
    }
    
    Payment ..|> PaymentMethod: implements

    %% class PaypalMethod {
    %%     -String email
    %%     +pay(int amountCents) boolean 
    %% }
    %% PaypalMethod ..|> PaymentMethod: implements

    %% class BankCardMethod {
    %%     -String number
    %%     -Date expiry
    %%     -String cvc
    %%     +pay(int amountCents) boolean 
    %% }
    %% BankCardMethod ..|> PaymentMethod: implements

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
        ADD_STAFF,
        EDIT_STAFF
        REMOVE_STAFF,
        LIST_STAFF_ALL,
        ASSIGN_MANAGER,
        PROMOTE_STAFF,
        TRANSFER_STAFF,
        ADD_PAYMENT,
        REMOVE_PAYMENT,
        OPEN_BRANCH,
        CLOSE_BRANCH,
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
        +getActions() List~Action~
    }
    Role "0." o-- "*" Action: allowed

    class CustomerRole {
        +code() char()
        +getActions() List~Action~
    }
    CustomerRole ..|> Role: implements

    class StaffRole {
        +code() char()
        +getActions() List~Action~
    }
    StaffRole ..|> Role: implements
    
    class ManagerRole {
        +code() char()
        +getActions() List~Action~
    }
    ManagerRole ..|> Role: implements

    class AdminRole {
        +code() char()
        +getActions() List~Action~
    }
    AdminRole ..|> Role: implements

    class Gender {
        <<enumeration>>
        MALE,
        FEMALE,
        UNKNOWN
    }
    class User {
        -String username
        -String name
        -int age
        -String password
        -Gender gender
        -Role role
        +login(String username, String Password) boolean 
        +setRole(Role role)
        +getUsername() String
        +getName() String
        +getAge() int
        +getPassword() String
        +getGender() String
    }
    User o-- Role: assigned
    User *-- Gender: gender

    %% Fast Food Chain & Branch
    class Branch {
        -String name
        -String location
        -Set~User~ staffs
        -Set~User~ managers
        -Set~Item~ menu

        +assign(User staff)
        +remove(User staff)
        +getName() String 
        +getLocation() String 
        +getStaffs() List~User~
        +getManagers() List~User~
        +getMenu() Set~Item~
        +getStaffQuota() int
        +getManagerQuota() int
    }
    Branch "0." o-- "*" User: assigned
    Branch "1" *-- "*" Item: offers

    class Chain {
        -User admin
        -Map~String, User~ staff
        -Set~Branch~ branches
        -Set~PaymentMethod~ paymentMethods
        
        +getBranches() List~Branch~
        +getStaffs() Collection~User~
        +getPaymentMethods() Set~PaymentMethod~
    }
    Chain "1" *-- "*" User: employs
    Chain "1" *-- "1" User: administers
    Chain "1" *-- "*" Branch: branches
    Chain "1" *-- "*" PaymentMethod: supports

    %% Session
    class Session {
        -Optional~User~ user
        -Role role
        
        +getUser() Optional~User~
        +getRole() Role
    }
    Session "1" *-- "*" User: used by
    Session "1" *-- "1" Role: authorised

    %% Application Init
    class Init {
        +initChain() Chain$
        -parseMenus(List~Row~ rows) Map~String, Set~$
        -parseStaffs(List~Row~ rows) Map~String, Set~$
        -parseBranches(List~Row~ rows) Map~String, Branch~$
        -readInitWorkbook(String path) List~Row~$
    }
    Init "1" ..> "1" Chain: initialises
    
    %% Application
    class Application {
        +main(String[] args)$
    }
    Application "1" ..> "1" Session: session
    Application "1" ..> "1" Chain: chain
    Application "1" ..> "1" Init: initialise

```