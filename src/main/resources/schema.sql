CREATE TABLE m_users
(
   id int NOT NULL IDENTITY,
   user_name varchar (50) NOT NULL,
   furigana varchar (50) NOT NULL,
   e_mail varchar (50) NOT NULL UNIQUE,
   password varchar (250) NOT NULL,
   postal_code varchar (50) NOT NULL,
   prefectures varchar (50) NOT NULL,
   city varchar (50) NOT NULL,
   town_name varchar (50) NOT NULL,
   others varchar (50),
   phone_number varchar (50) NOT NULL,
   gender int NOT NULL,
   birthday date NOT NULL,
   role varchar (50) NOT NULL,
   created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);
CREATE TABLE m_goods
(
   id int NOT NULL IDENTITY,
   goods_name varchar (50) NOT NULL,
   description varchar (250) NOT NULL,
   price int NOT NULL,
   image_url varchar (250) NOT NULL UNIQUE,
   created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);
CREATE TABLE cart
(
   id int NOT NULL IDENTITY,
   user_id int NOT NULL,
   goods_id int NOT NULL,
   quantity varchar (50) NOT NULL,
   created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);
CREATE TABLE `order`
(
   id int NOT NULL IDENTITY,
   user_id int NOT NULL,
   order_date timestamp NOT NULL,
   order_number varchar (50) NOT NULL,
   created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);
CREATE TABLE order_details
(
   id int NOT NULL IDENTITY,
   order_id int NOT NULL,
   goods_id int NOT NULL,
   price int NOT NULL,
   quantity int NOT NULL,
   created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);
CREATE TABLE delivery_address
(
   id int NOT NULL IDENTITY,
   order_id int NOT NULL,
   postal_code varchar (50) NOT NULL,
   prefectures varchar (50) NOT NULL,
   city varchar (50) NOT NULL,
   town_name varchar (50) NOT NULL,
   others varchar (50),
   created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);
CREATE TABLE monthly_sales
(
   id int NOT NULL IDENTITY,
   month int NOT NULL,
   year int NOT NULL,
   sales int NOT NULL,
   PRIMARY KEY (id),
   CONSTRAINT uq_year_month UNIQUE
   (
      year,
      month
   )
);