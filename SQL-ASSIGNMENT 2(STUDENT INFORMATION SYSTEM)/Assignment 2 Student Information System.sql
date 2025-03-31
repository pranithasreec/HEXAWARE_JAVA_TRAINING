-------------------- TASK 1 ------------------------

create database sisdb;
use sisdb;
create table students (student_id int auto_increment primary key,
    first_name varchar(50),last_name varchar(50),
    date_of_birth date,email varchar(100) unique,
    phone_number varchar(15) unique);
create table teacher ( teacher_id int auto_increment primary key,first_name varchar(50),
    last_name varchar(50),email varchar(100) unique);
create table courses (course_id int auto_increment primary key,
    course_name varchar(100),credits int,teacher_id int,
    foreign key (teacher_id) references teacher(teacher_id) on delete set null);
create table enrollments (
    enrollment_id int auto_increment primary key,
    student_id int,course_id int,enrollment_date date,
    foreign key (student_id) references students(student_id) on delete cascade,
    foreign key (course_id) references courses(course_id) on delete cascade);
create table payments (payment_id int auto_increment primary key,
    student_id int,amount decimal(10,2),payment_date date,
    foreign key (student_id) references students(student_id) on delete cascade);

insert into students (first_name, last_name, date_of_birth, email, phone_number) values
('Shivaji', 'Bhonsale', '1630-02-19', 'shivaji@example.com', '9876543210'),
('Krishnadevaraya', 'Tuluva', '1471-01-16', 'krishnadevaraya@example.com', '9876543211'),
('Prithviraj', 'Chauhan', '1166-06-12', 'prithviraj@example.com', '9876543212'),
('Ashoka', 'Maurya', '304-04-01', 'ashoka@example.com', '9876543213'),
('Raja Raja', 'Chola', '947-10-03', 'rajarajachola@example.com', '9876543214'),
('Chandragupta', 'Maurya', '340-07-02', 'chandragupta@example.com', '9876543215'),
('Baji', 'Rao', '1700-08-18', 'bajirao@example.com', '9876543216'),
('Akbar', 'Mughal', '1542-10-15', 'akbar@example.com', '9876543217'),
('Lakshmi', 'Bai', '1828-11-19', 'lakshmibai@example.com', '9876543218'),
('Sambaji', 'Bhonsale', '1657-05-14', 'sambaji@example.com', '9876543219');

insert into teacher (first_name, last_name, email) values
('Radhakrishnan', 'Sarvepalli', 'radhakrishnan@example.com'),
('Aryabhata', 'Mathematician', 'aryabhata@example.com'),
('Chanakya', 'Vishnugupta', 'chanakya@example.com'),
('Vikram', 'Sarabhai', 'vikram@example.com'),
('A.P.J.', 'Abdul Kalam', 'kalam@example.com'),
('Jagadish', 'Chandra Bose', 'bose@example.com'),
('C.V.', 'Raman', 'cvraman@example.com'),
('Homi', 'Bhabha', 'bhabha@example.com'),
('Satyendra', 'Nath Bose', 'snbose@example.com'),
('Ramanujan', 'Srinivasa', 'ramanujan@example.com');

insert into courses (course_name, credits, teacher_id) values
('History of Maratha Empire', 3, 1),
('Vijayanagara Architecture', 4, 2),
('Medieval Indian Warfare', 3, 3),
('Mauryan Administration', 4, 4),
('Chola Dynasty Achievements', 3, 5),
('Ancient Indian Economy', 4, 6),
('Peshwa Governance', 3, 7),
('Mughal Administration', 4, 8),
('Freedom Fighters of India', 3, 9),
('Indian Mathematics', 4, 10);

insert into enrollments (student_id, course_id, enrollment_date) values
(1, 1, '2024-01-10'),
(2, 2, '2024-01-11'),
(3, 3, '2024-01-12'),
(4, 4, '2024-01-13'),
(5, 5, '2024-01-14'),
(6, 6, '2024-01-15'),
(7, 7, '2024-01-16'),
(8, 8, '2024-01-17'),
(9, 9, '2024-01-18'),
(10, 10, '2024-01-19');

insert into payments (student_id, amount, payment_date) values
(1, 5000, '2024-02-01'),
(2, 4000, '2024-02-02'),
(3, 6000, '2024-02-03'),
(4, 5500, '2024-02-04'),
(5, 7000, '2024-02-05'),
(6, 6500, '2024-02-06'),
(7, 4800, '2024-02-07'),
(8, 7500, '2024-02-08'),
(9, 5000, '2024-02-09'),
(10, 5300, '2024-02-10');


-------------------- TASK 2 (2: Select, Where, Between, AND, LIKE)  ---------------

insert into students (first_name, last_name, date_of_birth, email, phone_number) 
values ('John', 'Doe', '1995-08-15', 'john.doe@example.com', '1234567890');

insert into enrollments (student_id, course_id, enrollment_date) 
values ((select student_id from students where first_name = 'Shivaji' and last_name = 'Bhonsale'), 
        (select course_id from courses where course_name = 'History of Maratha Empire'), 
        '2024-03-15');

update teacher set email = 'arya.new@example.com' where first_name = 'Aryabhata' and last_name = 'Mathematician';

delete from enrollments where student_id=1 and course_id=1;

select * from students;
update courses set course_name="Indian Mathematics" where teacher_id=1 ;

delete from students where student_id=1;

update payments set amount=12000 where student_id=7;

-------------------------- TASK 3 ----------------------


use sisdb;

select s.first_name, s.last_name, sum(p.amount) as total_payments
from students s join payments p on s.student_id = p.student_id
where s.first_name = 'Shivaji' and s.last_name = 'Bhonsale';

select c.course_name, count(e.student_id) as student_count
from courses c left join enrollments e on c.course_id = e.course_id
group by c.course_name;

select s.first_name, s.last_name
from students s left join enrollments e on s.student_id = e.student_id
where e.student_id is null;

select s.first_name, s.last_name, c.course_name
from students s join enrollments e on s.student_id = e.student_id
join courses c on e.course_id = c.course_id;

select t.first_name, t.last_name, c.course_name
from teacher t join courses c on t.teacher_id = c.teacher_id;

select s.first_name, s.last_name, e.enrollment_date
from students s
join enrollments e on s.student_id = e.student_id
join courses c on e.course_id = c.course_id
where c.course_name = 'History of India';

select s.first_name, s.last_name
from students s left join payments p on s.student_id = p.student_id
where p.student_id is null;

select c.course_name
from courses c left join enrollments e on c.course_id = e.course_id
where e.course_id is null;

select s.first_name, s.last_name, count(e.course_id) as course_count
from students s join enrollments e on s.student_id = e.student_id
group by s.student_id having count(e.course_id) > 1;

select t.first_name, t.last_name
from teacher t
left join courses c on t.teacher_id = c.teacher_id
where c.course_id is null;

----------------------- TASK 4 -----------------------------------------
use sisdb;
-- 1. Calculate the average number of students enrolled in each course.
SELECT AVG(student_count) AS avg_students_per_course
FROM (
    SELECT course_id, COUNT(student_id) AS student_count
    FROM enrollments
    GROUP BY course_id
) AS subquery;

-- 2. Identify the student(s) who made the highest payment.
SELECT s.first_name, s.last_name, p.amount
FROM payments p
JOIN students s ON p.student_id = s.student_id
WHERE p.amount = (SELECT MAX(amount) FROM payments);

-- 3. Retrieve a list of courses with the highest number of enrollments.
SELECT c.course_name, COUNT(e.student_id) AS enrollment_count
FROM enrollments e
JOIN courses c ON e.course_id = c.course_id
GROUP BY c.course_id
HAVING COUNT(e.student_id) = (
    SELECT MAX(enrollment_count)
    FROM (SELECT COUNT(student_id) AS enrollment_count FROM enrollments GROUP BY course_id) AS subquery
);

-- 4. Calculate the total payments made to courses taught by each teacher.
SELECT t.first_name, t.last_name, SUM(p.amount) AS total_payments
FROM teacher t
JOIN courses c ON t.teacher_id = c.teacher_id
JOIN enrollments e ON c.course_id = e.course_id
JOIN payments p ON e.student_id = p.student_id
GROUP BY t.teacher_id;

-- 5. Identify students who are enrolled in all available courses.
SELECT s.first_name, s.last_name
FROM students s
WHERE (SELECT COUNT(course_id) FROM enrollments WHERE student_id = s.student_id) = (SELECT COUNT(*) FROM courses);

-- 6. Retrieve the names of teachers who have not been assigned to any courses.
SELECT t.first_name, t.last_name
FROM teacher t
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.teacher_id = t.teacher_id);

-- 7. Calculate the average age of all students.
SELECT AVG(YEAR(CURDATE()) - YEAR(date_of_birth)) AS average_age FROM students;

-- 8. Identify courses with no enrollments.
SELECT course_name
FROM courses
WHERE course_id NOT IN (SELECT DISTINCT course_id FROM enrollments);

-- 9. Calculate the total payments made by each student for each course.
SELECT s.first_name, s.last_name, c.course_name, SUM(p.amount) AS total_payment
FROM students s
JOIN enrollments e ON s.student_id = e.student_id
JOIN courses c ON e.course_id = c.course_id
JOIN payments p ON s.student_id = p.student_id
GROUP BY s.student_id, c.course_id;

-- 10. Identify students who have made more than one payment.
SELECT s.first_name, s.last_name, COUNT(p.payment_id) AS payment_count
FROM payments p
JOIN students s ON p.student_id = s.student_id
GROUP BY p.student_id
HAVING COUNT(p.payment_id) > 1;

-- 11. Calculate the total payments made by each student.
SELECT s.first_name, s.last_name, SUM(p.amount) AS total_payment
FROM students s
JOIN payments p ON s.student_id = p.student_id
GROUP BY s.student_id;

-- 12. Retrieve a list of course names along with the count of students enrolled in each course.
SELECT c.course_name, COUNT(e.student_id) AS enrollment_count
FROM courses c
LEFT JOIN enrollments e ON c.course_id = e.course_id
GROUP BY c.course_id;

-- 13. Calculate the average payment amount made by students.
SELECT s.first_name, s.last_name, AVG(p.amount) AS average_payment
FROM students s
JOIN payments p ON s.student_id = p.student_id
GROUP BY s.student_id;


