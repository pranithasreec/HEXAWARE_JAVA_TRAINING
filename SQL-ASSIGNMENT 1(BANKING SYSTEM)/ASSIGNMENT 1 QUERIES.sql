---------------------- TASK 1 -----------------------------

create database hmbank;
use hmbank;
create table customers (
    customer_id int auto_increment primary key,
    first_name varchar(50),last_name varchar(50),
    dob date,email varchar(100) unique,
    phone_number varchar(15) unique, address text);
create table accounts (
    account_id int auto_increment primary key,
    customer_id int,account_type enum('savings', 'current', 'zero_balance'),
    balance decimal(10,2) default 0.00,foreign key (customer_id) references customers(customer_id) on delete cascade
    );
create table transactions (
    transaction_id int auto_increment primary key,
    account_id int,transaction_type enum('deposit', 'withdrawal', 'transfer'),
    amount decimal(10,2),transaction_date datetime ,
    foreign key (account_id) references accounts(account_id) on delete cascade
);

-------------------------- TASK 2 ------------------------
INSERT INTO Customers (first_name, last_name, DOB, email, phone_number, address) VALUES
('Pranitha', 'Sree', '1995-07-21', 'pranitha.sree@example.com', '9876543210', 'Hyderabad, India'),
('Rahul', 'Sharma', '1990-02-15', 'rahul.sharma@example.com', '9876543211', 'Delhi, India'),
('Aarav', 'Patel', '1985-05-10', 'aarav.patel@example.com', '9876543212', 'Mumbai, India'),
('Sanya', 'Rao', '1998-09-12', 'sanya.rao@example.com', '9876543213', 'Bangalore, India'),
('Neha', 'Verma', '1993-11-30', 'neha.verma@example.com', '9876543214', 'Pune, India'),
('Rohan', 'Mehta', '1988-08-25', 'rohan.mehta@example.com', '9876543215', 'Chennai, India'),
('Aditya', 'Kulkarni', '1991-06-05', 'aditya.kulkarni@example.com', '9876543216', 'Kolkata, India'),
('Priya', 'Singh', '1996-04-18', 'priya.singh@example.com', '9876543217', 'Lucknow, India'),
('Vikram', 'Yadav', '1983-12-22', 'vikram.yadav@example.com', '9876543218', 'Jaipur, India'),
('Meera', 'Iyer', '1994-10-07', 'meera.iyer@example.com', '9876543219', 'Ahmedabad, India');
INSERT INTO Accounts (customer_id, account_type, balance) VALUES
(1, 'savings', 50000.00),
(2, 'current', 100000.00),
(3, 'zero_balance', 0.00),
(4, 'savings', 25000.00),
(5, 'current', 75000.00),
(6, 'savings', 12000.00),
(7, 'zero_balance', 0.00),
(8, 'current', 90000.00),
(9, 'savings', 40000.00),
(10, 'savings', 55000.00);

insert into transactions (account_id, transaction_type, amount, transaction_date) values
(1, 'deposit', 15000.00, '2024-03-01 10:15:00'),
(2, 'withdrawal', 5000.00, '2024-03-02 12:30:00'),
(3, 'deposit', 20000.00, '2024-03-05 09:45:00'),
(4, 'withdrawal', 7000.00, '2024-03-08 14:00:00'),
(5, 'transfer', 10000.00, '2024-03-10 16:20:00'),
(6, 'deposit', 12000.00, '2024-03-12 11:10:00'),
(7, 'transfer', 8000.00, '2024-03-15 13:50:00'),
(8, 'withdrawal', 6000.00, '2024-03-17 17:30:00'),
(9, 'deposit', 25000.00, '2024-03-18 08:40:00'),
(10, 'transfer', 9000.00, '2024-03-19 19:00:00');


------------ Write SQL queries for the following tasks ------------


-- Write a SQL query to retrieve the name, account type and email of all customers. -----------------
select customers.first_name, customers.last_name, customers.email, accounts.account_type
from customers join accounts on customers.customer_id = accounts.customer_id;

-- Write a SQL query to list all transaction corresponding customer. ------------
select c.first_name, c.last_name, t.transaction_id, t.transaction_type, t.amount, t.transaction_date
from customers c join accounts a on c.customer_id = a.customer_id join transactions t on a.account_id = t.account_id;

----- Write a SQL query to increase the balance of a specific account by a certain amount. -------------
update accounts set balance=balance+1200 where account_id=1;

------ Write a SQL query to Combine first and last names of customers as a full_name. -----------------------
select concat(first_name," ",last_name) as full_name from customers;

--------- Write a SQL query to remove accounts with a balance of zero where the account type is savings. --------------------------------
delete from accounts where balance=0 and account_type="savings";

--------- Write a SQL query to Find customers living in a specific city. -----------------------
select concat(first_name," ",last_name) as full_name from customers where address like "%mumbai%";

-- Write a SQL query to Get the account balance for a specific account.
select balance from accounts where customer_id=1;

-- Write a SQL query to List all current accounts with a balance greater than $1,000.
select balance from accounts where account_type="current" 
and balance>1000;

-- Write a SQL query to Retrieve all transactions for a specific account.
select amount from transactions where account_id=1;

-- Write a SQL query to Calculate the interest accrued on savings accounts based on a given interest rate.
select account_id,balance,balance*0.05 as interest_accrued from accounts
where account_type="savings" ;

--- Write a SQL query to Identify accounts where the balance is less than a specified overdraft limit. ----
select customer_id,account_id from accounts where balance<5000;

--- Write a SQL query to Find customers not living in a specific city. ----
select first_name,last_name from customers where address not like 
"%mumbai%";


-------------------------------- TASK 3 ---------------------------------------------
---- 1 ------
use hmbank;
select avg(balance) as avg_balance from accounts;

select balance from accounts order by balance desc limit 10;

select * from transactions;
alter table transactions modify column transaction_date datetime;
select sum(amount) as total_desposits from transactions where 
transaction_type="deposit" and transaction_date="2025-03-20 18:47:18";

select t.transaction_id,t.account_id,t.transaction_type,
a.account_type,t.amount from transactions t join accounts a on 
t.account_id=a.account_id;

select * from customers;
select c.customer_id,c.first_name,c.last_name,c.DOB,c.email,
c.phone_number,c.address,a.account_id,a.account_type,
a.balance from customers c join accounts a on 
c.customer_id=a.customer_id;

select t.transaction_id, t.transaction_type, t.amount, 
t.transaction_date, c.customer_id, c.first_name, 
c.last_name, c.email, c.phone_number 
from transactions t 
join accounts a on t.account_id=a.account_id
join customers c on a.customer_id=a.customer_id 
where t.account_id=1;

select customer_id, count(account_id) as account_count 
from accounts 
group by customer_id 
having count(account_id) > 1;

select account_type,sum(balance) as total_balance 
from accounts group by account_type;

select balance from accounts order by balance desc;
use hmbank;
select account_id,count(transaction_id) as transaction_count 
from transactions group by account_id
order by transaction_count desc;

select c.customer_id, c.first_name, c.last_name, a.account_type, sum(a.balance) as total_balance 
from customers c 
join accounts a on c.customer_id = a.customer_id 
group by c.customer_id, a.account_type 
having total_balance > 50000
order by total_balance desc;

select account_id,amount,date(transaction_date),count(*) as
duplicate_count 
from transactions
group by amount,account_id,date(transaction_date)
having count(*)>1;

-------------------------- TASK 4 --------------------------------
use hmbank;
select c.customer_id,c.first_name,c.last_name,a.balance
from customers c
join accounts a on c.customer_id=a.customer_id
where a.balance=(select max(balance) from accounts);

select avg(balance) as
avg_balance from accounts where customer_id in(select 
customer_id from accounts group by customer_id having count(*)>=1);

select * from transactions where 
amount > ( select avg(amount) from transactions);

select c.customer_id, c.first_name, c.last_name
from customers c
where c.customer_id not in (
    select distinct a.customer_id from accounts a
    join transactions t on a.account_id = t.account_id);

select sum(balance) as total_balance
from accounts
where account_id not in (select distinct account_id from transactions);

select * from transactions where account_id in(
select account_id from accounts where balance=
(select min(balance) from accounts));

select customer_id from accounts group by customer_id
having count(account_type) >1;

select account_type,
count(*) as count_of_type,
(count(*)*100/(select count(*) from accounts)) as percentage
from accounts
group by account_type;

select t.* from transactions t
join accounts a on t.account_id=a.account_id
where a.customer_id=2;

select account_type, 
       (select sum(balance) from accounts a2 where a2.account_type = a1.account_type) as total_balance
from accounts a1
group by account_type;










