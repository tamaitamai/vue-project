DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS review_goods;
DROP TABLE IF EXISTS histories;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS points;

--ユーザー情報を保存
CREATE TABLE users(
	id serial primary key,
	name text,
	mail text,
	password text,
	address text
);

--商品一覧を保存
CREATE TABLE items(
	id serial primary key,
	name text,
	image text,
	comment text,
	price Integer,
	genre text
);

--ユーザーごとのカート情報を保存
CREATE TABLE carts(
	id serial primary key,
	user_id Integer,
	item_id Integer,
	name text,
	image text,
	price Integer,
	count Integer,
	after_flg Integer,
	del_flg Integer
);

--商品ごとのレビューを保存
CREATE TABLE reviews(
	id serial primary key,
	good Integer,
	star Integer,
	user_id Integer,
	item_id Integer,
	name text,
	comment text
);

--レビューごとのいいね情報を保存
CREATE TABLE review_goods(
	id serial primary key,
	review_id Integer,
	user_id Integer
);
--ユーザーごとの履歴を保存
CREATE TABLE histories(
	id serial primary key,
	user_id Integer,
	item_id Integer,
	name text,
	image text,
	price Integer,
	count Integer,
	payment_date text
);

--注文時の情報を保存
CREATE TABLE orders(
	id serial primary key,
	user_id Integer,
	cart_id Integer,
	address text,
	order_date text
);

--ユーザーごとのお気に入り情報を保存
CREATE TABLE favorites(
	id serial primary key,
	user_id Integer,
	item_id Integer,
	name text,
	image text,
	price Integer,
	genre text
);

--ユーザーごとのポイント情報を保存
CREATE TABLE points(
	id serial primary key,
	user_id Integer,
	item_id Integer,
	point Integer,
	create_date text
);