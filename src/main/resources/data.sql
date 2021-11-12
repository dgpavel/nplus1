-- book
insert into book(id,title) values(1,'Effective Java');
insert into book(id,title) values(2,'Head First Java');
insert into book(id,title) values(3,'Head First Design Patterns');
-- book-review
insert into book(id,book_id,review,writtenBy) values(1,1,'I got this book 2 weeks ago, and it more than fulfilled my expectations. It is packed with best practices and detailed descriptions of the finer details of the Java language. Every developer should at least read the chapters about generics and lambdas.','Thorben Janssen');
insert into book(id,book_id,review,writtenBy) values(2,1,'This is the single best book I have seen or read about Java to date. Bloch, who has been involved in the development of the latest versions of the Java language and specification, does not teach how to write Java code; he teaches how to write GOOD Java code.','Eric');
insert into book(id,book_id,review,writtenBy) values(3,1,'I read this book as a recommended reading for the Java developers at Google. I found many "items" described in this book quite useful in real-life coding.','Andrew');
insert into book(id,book_id,review,writtenBy) values(4,2,'I was/am brand new to java programming and thought I would be able to use this book. I did alright for a few chapters, but at some point there was a leap in the level of understanding required.','Colt');
insert into book(id,book_id,review,writtenBy) values(5,2,'My background is that I`m a systems engineer for a major IT technology manufacturer. I know a few programing languages, I`m good at algorithmic thinking, I know nothing about java, and i don`t code for a living.','Chris Thole');
insert into book(id,book_id,review,writtenBy) values(6,2,'I used this to teach myself Java. However, I`ve been coding since 2008. I can`t say whether it`d be easy for a new programmer to pick up Java. ','M. Sawyer');
insert into book(id,book_id,review,writtenBy) values(7,3,'I am old. I did my undergrad work before patterns were a thing and worked on legacy systemsy whole career. I found myself on a new project with new technology and new grads.','Dave Norris');
insert into book(id,book_id,review,writtenBy) values(8,3,'I love this book for its exposition of programming concepts in a simplified and humorous presentation. I also recommend that, as an appetizer, readers should first watch the video tutorial presented by the authors of this book (Eric and Elisabeth) at Lynda.com. That video tutorial covers seven of the most widely used patterns present in this book.','Shridhar Kumar');
