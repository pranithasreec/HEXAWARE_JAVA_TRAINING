-- 1.Provide a SQL script that initializes the database for the Pet Adoption Platform ”PetPals”. --
create database petpals;

use petpals;

-- 2. Create tables for pets, shelters, donations, adoption events, and participants. 
-- 3. Define appropriate primary keys, foreign keys, and constraints.
-- 4. Ensure the script handles potential errors, such as if the database or tables already exist.

create table if not exists shelters (
    shelterid int primary key auto_increment,
    name varchar(255) not null,
    location varchar(255) not null
);
create table if not exists pets (
    petid int primary key auto_increment,
    name varchar(255) not null,
    age int not null,
    breed varchar(255) not null,
    type varchar(50) not null,
    availableforadoption bit not null default 1,
    shelterid int,
    foreign key (shelterid) references shelters(shelterid)
);
create table if not exists donations (
    donationid int primary key auto_increment,
    donorname varchar(255) not null,
    donationtype varchar(50) not null,
    donationamount decimal(10,2),
    donationitem varchar(255),
    donationdate datetime not null,
    shelterid int,
    foreign key (shelterid) references shelters(shelterid)
);
create table if not exists adoptionevents (
    eventid int primary key auto_increment,
    eventname varchar(255) not null,
    eventdate datetime not null,
    location varchar(255) not null
);
create table if not exists participants (
    participantid int primary key auto_increment,
    participantname varchar(255) not null,
    participanttype varchar(50) not null,
    eventid int,
    foreign key (eventid) references adoptionevents(eventid)
);

insert into shelters (name, location) values 
('happy paws', 'delhi'),
('safe haven', 'mumbai'),
('paw rescue', 'kolkata'),
('furry friends', 'jaipur'),
('pet haven', 'chandigarh'),
('companion care', 'lucknow'),
('rescue shelter', 'bhopal'),
('love paws', 'patna'),
('forever home', 'agra'),
('cuddle care', 'amritsar');

insert into pets (name, age, breed, type, availableforadoption, shelterid) values 
('arjun', 2, 'labrador', 'dog', 1, 1),
('whiskers', 3, 'persian', 'cat', 1, 2),
('rex', 5, 'german shepherd', 'dog', 0, 3),
('bella', 1, 'golden retriever', 'dog', 1, 4),
('luna', 4, 'siamese', 'cat', 1, 5),
('mani', 6, 'bulldog', 'dog', 1, 6),
('ravi', 2, 'beagle', 'dog', 1, 7),
('sita', 5, 'ragdoll', 'cat', 1, 8),
('ganesh', 3, 'pomeranian', 'dog', 1, 9),
('deepa', 4, 'maine coon', 'cat', 1, 10);

insert into donations (donorname, donationtype, donationamount, donationitem, donationdate, shelterid) values 
('aravind', 'cash', 100.00, null, '2024-03-01 10:00:00', 1),
('keerthi', 'item', null, 'dog food', '2024-03-02 12:30:00', 2),
('muthu', 'cash', 250.00, null, '2024-03-03 15:00:00', 3),
('divya', 'item', null, 'cat litter', '2024-03-04 09:00:00', 4),
('sathish', 'cash', 50.00, null, '2024-03-05 14:00:00', 5),
('rajesh', 'cash', 300.00, null, '2024-03-06 16:00:00', 6),
('meena', 'item', null, 'blankets', '2024-03-07 11:00:00', 7),
('hari', 'cash', 400.00, null, '2024-03-08 13:00:00', 8),
('swathi', 'item', null, 'dog beds', '2024-03-09 10:30:00', 9),
('vikram', 'cash', 150.00, null, '2024-03-10 15:45:00', 10);

insert into adoptionevents (eventname, eventdate, location) values 
('diwali adoption mela', '2024-04-01 10:00:00', 'delhi'),
('pongal pet fair', '2024-05-15 12:00:00', 'mumbai'),
('holi rescue fest', '2024-09-10 14:00:00', 'kolkata'),
('navratri adoption drive', '2024-12-20 16:00:00', 'jaipur'),
('raksha bandhan pet fest', '2024-11-10 11:00:00', 'chandigarh'),
('ganesh chaturthi pet carnival', '2024-06-05 15:00:00', 'lucknow'),
('durga puja adoption camp', '2024-07-20 14:00:00', 'bhopal'),
('lohri pet gathering', '2024-08-30 10:00:00', 'patna'),
('onam pet celebration', '2024-10-15 17:00:00', 'agra'),
('eid pet festival', '2024-03-25 13:00:00', 'amritsar');

insert into participants (participantname, participanttype, eventid) values 
('pranitha', 'shelter', 1),
('sree', 'shelter', 2),
('tharun', 'shelter', 3),
('venkatesh', 'adopter', 1),
('lakshmi', 'adopter', 2),
('madhavan', 'adopter', 3),
('deepak', 'adopter', 4),
('sundar', 'shelter', 5),
('nithya', 'adopter', 6),
('karthik', 'shelter', 7);

select * from participants;

-- 5. Write an SQL query that retrieves a list of available pets (those marked as available for adoption)
-- from the "Pets" table. Include the pet's name, age, breed, and type in the result set. Ensure that
-- the query filters out pets that are not available for adoption. --

select name, age, breed, type from pets where availableforadoption = 1;

-- 6.Write an SQL query that retrieves the names of participants (shelters and adopters) registered
-- for a specific adoption event. Use a parameter to specify the event ID. Ensure that the query
-- joins the necessary tables to retrieve the participant names and types. --

select p.participantname,  
p.participanttype,  
ae.eventname,  
ae.eventdate,  
ae.location  
from participants p  
inner join adoptionevents ae on p.eventid = ae.eventid  
where p.eventid = 2;

-- 7.Create a stored procedure in SQL that allows a shelter to update its information (name and
-- location) in the "Shelters" table. Use parameters to pass the shelter ID and the new information.
-- Ensure that the procedure performs the update and handles potential errors, such as an invalid
-- shelter ID.

delimiter //
create procedure updateshelterinfo(in shelter_id int, in new_name varchar(255), in new_location varchar(255))
begin
update shelters set name = new_name, location = new_location where shelterid = shelter_id;
end //
delimiter ;

-- 8.Write an SQL query that calculates and retrieves the total donation amount for each shelter (by
-- shelter name) from the "Donations" table. The result should include the shelter name and the
-- total donation amount. Ensure that the query handles cases where a shelter has received no
-- donations.

select s.name, sum(d.donationamount) as total_donation
from shelters s
left join donations d on s.shelterid = d.shelterid
group by s.shelterid
order by total_donation desc;

-- 9.Write an SQL query that retrieves the names of pets from the "Pets" table that do not have an
-- owner (i.e., where "OwnerID" is null). Include the pet's name, age, breed, and type in the result
-- set.

update pets 
set shelterid = null 
where petid in (3); -- updated null data as there is no null data ---
select name, age, breed, type from pets where shelterid is null;

-- 10. Write an SQL query that retrieves the total donation amount for each month and year (e.g.,
-- January 2023) from the "Donations" table. The result should include the month-year and the
-- corresponding total donation amount. --

select date_format(donationdate, '%M %Y') as month_year, sum(donationamount) as total_donations
from donations
group by month_year;


-- 11.Retrieve a list of distinct breeds for all pets that are either aged between 1 and 3 years or older
-- than 5 years. --

select distinct breed from pets where (age between 1 and 3) or ( age >5);

-- 12.  Retrieve a list of pets and their respective shelters where the pets are currently available for
--      adoption.
select p.name as petname, s.name as sheltername from pets p
join shelters s on p.shelterid = s.shelterid
where p.availableforadoption = 1;

-- 13.. Find the total number of participants in events organized by shelters located in specific city.
-- Example: City=Chennai --

select count(*) as totalparticipants
from participants p
join adoptionevents e on p.eventid = e.eventid
join shelters s on e.location = s.location
where s.location = 'chandigarh';

-- 14. Retrieve a list of unique breeds for pets with ages between 1 and 5 years. --

select distinct breed from pets where age between 1 and 5;

-- 15. Find the pets that have not been adopted by selecting their information from the 'Pet' table. --

select * from pets where availableforadoption=1;

-- 16.Retrieve the names of all adopted pets along with the adopter's name --

select pets.name as petname, p.participantname as adoptername
from pets 
join participants p on pets.petid = p.eventid
where p.participanttype = 'adopter';

-- 17.Retrieve a list of all shelters along with the count of pets currently available for adoption in each shelter. --

select s.shelterid, s.name as sheltername, count(p.petid) as availablepetscount  
from shelters s  
left join pets p on s.shelterid = p.shelterid and p.availableforadoption = 1  
group by s.shelterid, s.name;


-- 18.Find pairs of pets from the same shelter that have the same breed. --

select p1.name as pet1, p2.name as pet2, p1.breed, s.name as sheltername  
from pets p1  
join pets p2 on p1.shelterid = p2.shelterid and p1.breed = p2.breed and p1.petid < p2.petid  
join shelters s on p1.shelterid = s.shelterid;

-- 19. List all possible combinations of shelters and adoption events. --

select  s.shelterid,s.name as shelter_name,ae.eventid,ae.eventname,ae.eventdate,ae.location  
from shelters s  
cross join adoptionevents ae  
order by s.name, ae.eventdate;

-- 20.. Determine the shelter that has the highest number of adopted pets. -- 


select s.name as sheltername, count(p.petid) as adoptedpets
from shelters s
join pets p on s.shelterid = p.shelterid
where p.availableforadoption = 0
group by s.shelterid
order by adoptedpets desc
limit 1;
  -- ensures only highest number of adopted pets is returned. --










