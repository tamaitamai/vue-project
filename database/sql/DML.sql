--ユーザー情報を登録
INSERT INTO users(name,mail,password,address)VALUES('a','a','a','aa県aa市aa町 aaマンションa階');
INSERT INTO users(name,mail,password,address)VALUES('b','b','b','bb県bb市bb町 bbマンションb階');
INSERT INTO users(name,mail,password,address)VALUES('b','c','c','cc県cc市cc町 ccマンションc階');
INSERT INTO users(name,mail,password,address)VALUES('d','d','d','dd県dd市dd町 ddマンションd階');
INSERT INTO users(name,mail,password,address)VALUES('e','e','e','ee県ee市ee町 eeマンションe階');
INSERT INTO users(name,mail,password,address)VALUES('f','f','f','ff県ff市ff町 ffマンションf階');
INSERT INTO users(name,mail,password,address)VALUES('g','g','g','ee県ee市ee町 eeマンションe階');

--商品一覧を登録
INSERT INTO items(name,image,comment,price,genre)VALUES('杏1','anzu1.jpg','かわいい',100,'杏');
INSERT INTO items(name,image,comment,price,genre)VALUES('杏2','anzu2.jpg','景色がきれい',300,'杏');
INSERT INTO items(name,image,comment,price,genre)VALUES('杏3','anzu3.jpg','ドヤ',700,'杏');
INSERT INTO items(name,image,comment,price,genre)VALUES('杏4','anzu4.jpg','ロードスター',500,'杏');
INSERT INTO items(name,image,comment,price,genre)VALUES('杏5','anzu5.jpg','お気に入り',1000,'杏');
INSERT INTO items(name,image,comment,price,genre)VALUES('智恵理1','chieri1.jpg','押しです',1200,'智恵理');
INSERT INTO items(name,image,comment,price,genre)VALUES('智恵理2','chieri2.jpg','最高',500,'智恵理');
INSERT INTO items(name,image,comment,price,genre)VALUES('智恵理3','chieri3.jpg','かわいい',100,'智恵理');
INSERT INTO items(name,image,comment,price,genre)VALUES('智恵理4','chieri4.jpg','いいね',200,'智恵理');
INSERT INTO items(name,image,comment,price,genre)VALUES('智恵理5','chieri5.jpg','雪景色',300,'智恵理');
INSERT INTO items(name,image,comment,price,genre)VALUES('智恵理6','chieri6.jpg','岩',700,'智恵理');
INSERT INTO items(name,image,comment,price,genre)VALUES('智恵理7','chieri7.jpg','夕日がきれい',200,'智恵理');
INSERT INTO items(name,image,comment,price,genre)VALUES('卯月1','uzuki1.jpg','ピース',1000,'卯月');
INSERT INTO items(name,image,comment,price,genre)VALUES('卯月2','uzuki2.jpg','岩がいい感じ',2000,'卯月');
INSERT INTO items(name,image,comment,price,genre)VALUES('卯月3','uzuki3.jpg','景色がきれい',500,'卯月');

--カート情報を登録
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(1,'1','杏1','anzu1.jpg',100,2,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(1,'6','智恵理1','chieri1.jpg',1200,1,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(1,'12','智恵理7','chieri7.jpg',200,3,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(1,'4','杏4','anzu4.jpg',500,2,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(1,'5','杏5','anzu5.jpg',1000,1,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(1,'14','卯月2','uzuki2.jpg',2000,1,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(1,'15','卯月3','uzuki3.jpg',500,5,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(1,'3','杏3','anzu3.jpg',700,5,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(1,'11','智恵理6','chieri6.jpg',700,1,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(1,'10','智恵理5','chieri5.jpg',300,4,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(2,'1','杏1','anzu1.jpg',100,3,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(2,'7','智恵理2','chieri2.jpg',500,3,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(2,'10','智恵理5','chieri5.jpg',300,3,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(2,'6','智恵理1','chieri1.jpg',1200,1,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(2,'14','卯月2','uzuki2.jpg',2000,1,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(3,'1','杏1','anzu1.jpg',100,1,0,1);
INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)VALUES(3,'6','智恵理1','chieri1.jpg',1200,1,0,1);

--レビュー情報を登録
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(1,5,1,2,'aa','景色がいいですね');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(0,5,1,3,'あ','かわいい');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(0,3,1,14,'あいうえお','値段が少し高い気がします');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(0,2,1,13,'aaa','曇っている');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(1,4,1,7,'あああ','とてもきれい');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(0,1,1,11,'ぺんぎん','なんか違う');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(0,5,1,10,'雪大好き','雪景色いいね');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(0,5,2,11,'いいと思う','僕はありだと思います');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(0,4,2,2,'b','私もいいと思います！');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(0,5,2,7,'bbb','すごくいい景色ですね');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(0,3,2,6,'b','僕も押していこうと思います');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(0,5,2,13,'b','まあありかな');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(0,5,3,2,'c','いいね');
INSERT INTO reviews(good,star,user_id,item_id,name,comment)VALUES(0,1,3,13,'c','なんか違う');

--レビューのいいねを登録
INSERT INTO review_goods(review_id,user_id)VALUES(9,1);
INSERT INTO review_goods(review_id,user_id)VALUES(2,1);
INSERT INTO review_goods(review_id,user_id)VALUES(10,1);
INSERT INTO review_goods(review_id,user_id)VALUES(7,1);
INSERT INTO review_goods(review_id,user_id)VALUES(5,1);
INSERT INTO review_goods(review_id,user_id)VALUES(3,1);
INSERT INTO review_goods(review_id,user_id)VALUES(11,2);
INSERT INTO review_goods(review_id,user_id)VALUES(9,2);
INSERT INTO review_goods(review_id,user_id)VALUES(1,2);
INSERT INTO review_goods(review_id,user_id)VALUES(13,2);
INSERT INTO review_goods(review_id,user_id)VALUES(10,2);
INSERT INTO review_goods(review_id,user_id)VALUES(6,3);
INSERT INTO review_goods(review_id,user_id)VALUES(8,3);
INSERT INTO review_goods(review_id,user_id)VALUES(10,3);
INSERT INTO review_goods(review_id,user_id)VALUES(9,3);
INSERT INTO review_goods(review_id,user_id)VALUES(1,3);

--履歴情報を登録
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('1','1','杏1','anzu1.jpg',100,2,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('1','6','智恵理1','chieri1.jpg',1200,1,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('1','12','智恵理7','chieri7.jpg',200,3,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('1','4','杏4','anzu4.jpg',500,2,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('1','5','杏5','anzu5.jpg',1000,1,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('1','14','卯月2','uzuki2.jpg',2000,1,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('1','15','卯月3','uzuki3.jpg',500,5,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('1','3','杏3','anzu3.jpg',700,5,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('1','11','智恵理6','chieri6.jpg',700,1,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('1','10','智恵理5','chieri5.jpg',300,4,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('2','1','杏1','anzu1.jpg',100,3,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('2','7','智恵理2','chieri2.jpg',500,3,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('2','10','智恵理5','chieri5.jpg',300,3,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('2','6','智恵理1','chieri1.jpg',1200,1,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('2','14','卯月2','uzuki2.jpg',2000,1,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('3','1','杏1','anzu1.jpg',100,1,'2025/04/01(火)');
INSERT INTO histories(user_id,item_id,name,image,price,count,payment_date)VALUES('3','6','智恵理1','chieri1.jpg',1200,1,'2025/04/01(火)');


--お気に入り情報を登録
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(1,6,'智恵理1','chieri1.jpg',1200,'智恵理');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(1,2,'杏2','anzu2.jpg',300,'杏');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(1,4,'杏4','anzu4.jpg',500,'杏');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(1,5,'杏5','anzu5.jpg',1000,'杏');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(1,3,'杏3','anzu3.jpg',700,'杏');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(1,13,'卯月1','uzuki1.jpg',1000,'卯月');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(2,11,'智恵理6','chieri6.jpg',700,'智恵理');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(2,1,'杏1','anzu1.jpg',100,'杏');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(2,7,'智恵理2','chieri2.jpg',500,'智恵理');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(2,6,'智恵理1','chieri1.jpg',1200,'智恵理');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(2,13,'卯月1','uzuki1.jpg',1000,'卯月');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(3,13,'卯月1','uzuki1.jpg',1000,'卯月');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(3,3,'杏3','anzu3.jpg',700,'杏');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(3,8,'智恵理3','chieri3.jpg',100,'智恵理');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(3,11,'智恵理6','chieri6.jpg',700,'智恵理');
INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(3,5,'杏5','anzu5.jpg',1000,'杏');

--ポイント情報を登録
INSERT INTO points(user_id,item_id,point,create_date)VALUES(1,1,1,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(1,6,12,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(1,12,2,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(1,4,5,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(1,5,10,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(1,14,20,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(1,15,5,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(1,3,7,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(1,null,-3,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(1,11,7,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(1,10,3,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(2,1,1,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(2,7,5,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(2,10,3,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(2,6,12,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(2,14,20,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(3,1,1,'2025/04/01(火)');
INSERT INTO points(user_id,item_id,point,create_date)VALUES(3,6,12,'2025/04/01(火)');