-- USERS DUMY ここから
INSERT INTO USERS(MST_USER_ID,USER_PASSWORD,USERS_STATUS_ID)
VALUES('hyogo1111','50aa188b7ec93b738ea97dca33616d1b1c835dcc1e17468dfca28ff6864ee7f11dc96b89e9d5104a95e24e0637a71c88d233f67a0e3b17f9d9c76d73e9450d06',1)
-- pass = 12345aaaa salt = h
,('ultrasoul','2eb40d4a0d19d0da38408211bbc8072b7da09b3929049c8a4e8cb459fa008fad531e307b3fa3148afebfe4a781d409b3371789670aab77f2ea05730dc1d182fb',1)
-- pass = matsumoto salt = u
,('satoh','02a517a568dc596045e13ba2e4f115382bfc6a9d765b6eb0d5456ce8153b1ef4a88a4952e16204782157c80bceb4f6378ced553d5a862db161edd1fc22fcea6b',2)
-- pass = 00001111 salt = s
,('studio02','e0c943d596fe48002e03ce4fd88d98caec4d953d05f067d9c222a33e6c9a8e015a8af6947d38d05164fdde0d554f00b9d60d03b4720ae093e2572383054a4b5c',1)
,('studio03','379d64a42084452c9957225c699e5829160b266c64ca39df38d83c2d12a59236b361e34095677f785e18010df75b6bc270e9387a07960614e6fea43a9b320a09',1)
,('studio04','745b2c8181bc6d2544c01dfd797154e204af4fb5c489564c91c0762b7e471ea28be52d7476e638895bd89773f5fe9789bcf821c6d34e07d6cfa2400066524bb6',1)
,('studio05','fe942f9059635a18a4f4d33ff7d51e423e1bac39445811a537b0dc74c2dc419c5d2cc193ab9e16447edf91fc27ff1290e82b502b1fe8952729726a32e697d859',1)
,('studio06','d727b15630af9c8a1d63ec48033d96a7826ae88c6ea204653add19af38aff01934fad1230f56532e15aa34bf13f5be823e8576e856f54cf568d0d5a4537db946',1)
,('studio07','ef492f0baf335239815518ca92253ca172bcba591bb0f4a80cb98546c4a0fa1977f8992bfc1ba2c5ace91504313bb1320517dba0452db2b1960458cf20c2487d',1)
,('studio08','b5c0a0dc3decd4713d80d7e9c01414462934e6ac16f861ef6c5f32c8c95fef5cacb3b69d6c5ea449f745c3a08e439686c37469394ef43582f8fa169fe9010026',1);
-- USERS DUMY ここまで

-- PINS DUMY ここから
INSERT INTO PINS(
USERS_USER_ID,
PIN_NAME,
PIN_ADDRESS,
PIN_URL,
PIN_GEO_X,
PIN_GEO_Y,
PIN_IMAGE_URL,
PIN_INFO,
PIN_TIMESTAMP
)
VALUES
('hyogo1111','神戸ポートタワー','兵庫県神戸市中央区波止場町5-5','https://www.kobe-port-tower.com/',135.1866995,34.6826300,'/uploads/18bf62a8-3a71-46d5-9631-a6e66499e544_pixta_15880984_S.jpg','赤いです','2026/05/12 16:04:12')

,('hyogo1111','甲子園','兵庫県西宮市甲子園町1-82','https://koshien.hanshin.co.jp/',135.3616563,34.7212174,'/uploads/b5578e5d-cb51-4aae-bb93-41fd7d6b1523_202309081549_IMG_3516.jpg','カッキーン！野球ってスッキリするよね！ルールわかんないけど！','2026/05/12 16:04:12')

,('ultrasoul','創造社リカレントスクール','兵庫県神戸市中央区京町６７','',135.1931439,34.6883951,'/uploads/aeb72891-e265-47ae-ab29-0f9525181bcb_tatemono07.png','素晴らしい','2026/05/12 16:04:12')

,('ultrasoul','神戸どうぶつ王国','神戸市中央区港島南町7-1-9','https://www.kobe-oukoku.com/',135.2205350,34.6521000,'/uploads/05e2022d-7762-4685-818b-1bf83a6eaec5_716cbe6a-3d89-4df2-875c-3ac27253ef2c.jpeg','カワウソがいます！','2026/05/12 16:50:27')

,('ultrasoul','東条湖おもちゃ王国','兵庫県加東市黒谷１２１６','',135.0612950,34.9239350,'/uploads/94c1964c-e031-40cc-a515-58fc7b737000_unnamed.jpg','おもちゃいっぱい、デカレンジャーショーもやってる','2026/05/13 9:38:28')

,('ultrasoul','城崎マリンワールド','兵庫県豊岡市瀬戸1090番地','https://marineworld.hiyoriyama.co.jp/',134.8173980,35.6590810,'/uploads/41f608b5-ac0a-4ee2-acf8-6b7e428b22c4_info01.jpg','釣りたてのさかながホクホク、おいしい！','2026/05/13 9:41:30')

,('ultrasoul','兵庫県立公園あわじ花さじき','兵庫県淡路市楠本２８０５−７','https://awajihanasajiki.jp/',134.9914090,34.5628890,'/uploads/e39271ba-67d6-4372-8113-a967d1f0278f_stock3.jpg','丘陵地に桜や色とりどりの花畑が広がる公園。海と本土の景色も一望できる。犬との同伴も可能。','2026/05/13 9:47:12')

,('ultrasoul','淡路ファームパーク イングランドの丘','兵庫県南あわじ市八木養宜上１４０１','http://www.england-hill.com/?utm_source=GBP&utm_medium=GBP&utm_term=GBP&utm_content=GBP&utm_campaign=GBP',134.8024440,34.3076780,'/uploads/52db2690-47cc-4fa2-8f33-90066ad85d8c_img_about_04.jpg','どうぶつがいっぱい！でも犬は入れない。クゥーン…','2026/05/13 9:50:17')

,('ultrasoul','国領温泉 助七','兵庫県丹波市春日町国領206','',135.1233060,35.1362950,'/uploads/f160c2c3-6c53-48b5-b4e9-0b3ebd02adc0_02301_14402853n_01.jpg','昔ながらの和のぬくもりを感じられる温泉旅館。露天風呂付きの大浴場と貸し切りの家族風呂では、神経痛や関節のこわばり、慢性消化器病、冷え性、動脈硬化などに効能があるとされる単純二酸化炭素冷鉱泉の温泉','2026/05/13 11:26:01')

,('ultrasoul','道の駅 播磨いちのみや','兵庫県宍粟市一宮町須行名510-1','',134.5946040,35.0883100,'/uploads/8fe47442-37d3-4cc3-9cf1-3889363d12ff_02301_13000235_01.jpg','車を休めてる！','2026/05/13 11:29:29')

,('ultrasoul','姫路城','兵庫県姫路市本町６８','',134.6873630,34.8358730,'/uploads/eed87ff4-5966-4cbe-a486-9fc213d371db_1814036.jpg','おっきいおしろ！','2026/05/13 11:31:43')

,('ultrasoul','竹田城跡','兵庫県朝来市和田山町竹田','https://www.city.asago.hyogo.jp/site/takeda/',134.8411710,35.2905350,'/uploads/729b9ac5-9f50-4444-b33e-fdbc33805254_no.1.jpg','にほんのマチョっ…マチピっ…マチョぴチ…にほっ…日本のマチュピチュ！','2026/05/13 11:34:45')

,('ultrasoul','神戸マルイ','兵庫県神戸市中央区三宮町１丁目７−２','https://www.0101.co.jp/083/',135.1944430,34.6922340,'/uploads/a2c20e07-78e7-4ae3-9aa7-535fae58c884_02301_14402835_01.jpg','おいおい！','2026/05/13 11:39:50')

,('ultrasoul','神戸市立六甲山牧場','兵庫県神戸市灘区六甲山町中一里山１−１','https://rokkosan.jp/',135.2079770,34.7465820,'/uploads/59b7a94e-a688-4095-a2e2-6f189c83a217_28102cb3520079878_4.jpg','さまざまな動物たちを見ることができる風光明媚な牧場。乗馬、アイスクリーム作り、食事が楽しめる。','2026/05/13 12:34:24');
-- PINS DUMY ここまで

-- REVIEWS DUMY ここから
INSERT INTO REVIEWS(
USERS_USER_ID,
PINS_ID,
REVIEW_TITLE,
REVIEW_CONTENTS,
REVIEW_TIMESTAMP
)
VALUES
('ultrasoul',1,'赤いと思います。','赤すぎるのもどうかと思う','2026/05/12 16:04:12')

,('ultrasoul',4,'パスタ ライ助','カワウソいっぱい。毎日行きたい。ずーっとサイコー。','2026/05/12 16:53:10')

,('ultrasoul',4,'ああ','ああああ','2026/05/12 16:53:32')

,('ultrasoul',12,'すごいきれい！','すごくきれいだった！雲の上！とりになったみたい！','2026/05/13 12:24:11')

,('ultrasoul',12,'鳥なんですけど','自分鳥なんですけどよくここで休憩します。','2026/05/13 12:24:39')

,('ultrasoul',12,'よかった！','彼女が出来ました！','2026/05/13 12:25:03')

,('ultrasoul',9,'パスタ ライ助','先生と一緒にサウナにいった。まだたりないかも、忍耐力。もっと修業しなきゃ','2026/05/13 12:26:09')

,('ultrasoul',9,'あったかい','あったかかった！','2026/05/13 12:26:34')

,('ultrasoul',5,'ジャッジメント！','光よ、町の天使たちに届け','2026/05/13 12:27:06')

,('ultrasoul',5,'プラレール','プラレールがあった！','2026/05/13 12:27:24')

,('ultrasoul',5,'プール開き！','プールの後って、耳に水が入って太鼓みたいな音。プールの後に食べたおにぎり、郎のラーメンぐらいおいしい。','2026/05/13 12:28:26')

,('ultrasoul',2,'緑がいっぱい','甲子園ってテレビで俯瞰でしか見ないけど側面ってこんなに葉っぱはえてるんだ！なんかマイナスイオン感じるかも。','2026/05/13 12:30:23')

,('ultrasoul',7,'犬ちゃんも満足','犬と一緒に行きました。いつもより元気そう。','2026/05/13 12:31:02')

,('ultrasoul',8,'犬と…','犬といったんですけどペット同伴できませんでした…でも昔行ったときはすごく楽しかったと思う！','2026/05/13 12:31:52')

,('ultrasoul',8,'幼少期','幼少期にプラスチックの芝滑りでめっちゃケガした覚えがあります。気を付けて滑ってね','2026/05/13 12:32:28')

,('ultrasoul',14,'ひつじレース','なんといっても見どころは羊レース。あなたの推し羊は優勝の座に輝けるか！？走れ～走れ～ウ〇娘～♪','2026/05/13 12:35:29')

,('ultrasoul',14,'羊がいっぱい','羊が自由に園内を移動していてとてもかわいいです。赤ちゃん羊は毛並みがとっても心地いい！','2026/05/13 12:36:10')

,('ultrasoul',14,'ひつじぬいぐるみ','毛を刈れる羊ぬいぐるみが売っていてとってもかわいい。ぬいぐるみだけどリアルな造形とリーズナブルな値段！','2026/05/13 12:37:03')

,('ultrasoul',1,'ブルアカスタンプラリー','ブルーアーカイブのスタンプラリーでいきました！キサキの等身大パネルもありました！','2026/05/13 12:39:18');
-- REVIEWS DUMY ここまで

-- EVENT DUMY ここから
INSERT INTO EVENTS(
USERS_USER_ID,
EVENT_NAME,
EVENT_ADDRESS,
EVENT_URL,
EVENT_GEO_X,
EVENT_GEO_Y,
EVENT_IMAGE_URL,
EVENT_INFO,
EVENT_START,
EVENT_END,
EVENT_STATUS_ID,
EVENT_TIMESTAMP
)
VALUES
('ultrasoul','姫路お城祭り','兵庫県姫路市','',134.6854550,34.8154950,'','姫路城最高','2026/05/22','2026/05/24',2,'2026/05/12 16:04:12')

,('hyogo1111','神戸祭り','兵庫県神戸市中央区','',135.1978000,34.6950720,'','すごくめでたいです','2026/05/17','2026/05/17',2,'2026/05/12 16:04:12')

,('hyogo1111','佐用町桜まつり','兵庫県佐用郡佐用町','',134.3557740,35.0042950,'','ステージイベント、飲食・物販ブース 雨天決行','2026/03/29','2026/03/29',2,'2026/05/12 16:04:12')

,('hyogo1111','『ザ・スーパーマリオギャラクシー』×クリスピー・クリーム・ドーナツ','兵庫県神戸市中央区三宮町１丁目７−２','https://www.0101.co.jp/083/recommend/detail.html?article_seq=972520&article_type=sho&hashtag=gourmet',135.1944430,34.6922340,'/uploads/4386d70a-af05-4a8e-9163-903b3c495937_5f5f95e7af3090aa3e9b59cb13287ab3_03_01.jpg','物語の鍵ともなる人気キャラクター、 マリオ・ヨッシー・チコをモチーフにした3種が新登場！何が出てくるかわからない！？「ハテナブロック」ドーナツも！数量限定のコラボバッグや購入者限定の特典などもご用意しています！','2026/05/08','2026/05/30',1,'2026/05/13 11:42:11')

,('hyogo1111','第76回姫路お城まつり','兵庫県姫路市本町68','https://www.city.himeji.lg.jp/shisei/0000033044.html',134.6873630,34.8358730,'/uploads/3ad7506d-5655-4c01-a33e-79122e8a834d_AqrN6ZEr8hvRuGCKSskqoK9Crg2P3x8BgnQMEHaOEqiuwS4rmKpNS-Ofz0kEqS54stMoblb-hUlBYMqhSBwc2JwHbQteD-AY7x0rJGn1sZ_fGxY25uubrUvf8d2BuRwc.jpg','姫路城のおひざ元でゴキゲンなお祭り！','2026/05/22','2026/05/24',1,'2026/05/13 12:02:41')

,('hyogo1111','湯村温泉まつり','兵庫県美方郡新温泉町湯','https://www.town.shinonsen.hyogo.jp/page/?mode=detail&page_id=d31ea7d674bfc876d6091496536a7b7e',134.4826970,35.5454330,'/uploads/d87921a7-777a-48eb-ae9d-cec3ca11f2c1_330x330.jpg','湯村温泉まつりは、湯村温泉の開祖慈覚大使への感謝と子どもたちの健やかな成長を祈願するお祭りです。名物「大菖蒲綱引き」は、長さ約80ｍ、重さ約3t、直径50～60㎝の大綱を、地元住民と観光客が一緒になり勝運を競います。','2026/06/07','2026/06/07',1,'2026/05/13 12:06:04')

,('hyogo1111','丹波竹田祭り','丹波市市島町上竹田2015','https://www.tambacity-kankou.jp/spot/spot-2965/',135.1160280,35.2254640,'/uploads/10b48100-da4d-4d44-a221-a52aa64bc35c_0525_takeda_matsuri-2-1280x853.jpg','つなひき！大迫力！アツくなっちゃうな！','2026/10/03','2026/10/04',1,'2026/05/13 12:08:51')

,('hyogo1111','第53回神戸まつり','兵庫県神戸市中央区明石町４７','',135.1912840,34.6887700,'/uploads/e1810cea-7b38-44da-8a92-755f1b54d655_kobe-matsuri2026-flyer-pdf.jpg','サンバでトリッコ～！','2026/05/17','2026/05/17',1,'2026/05/13 12:23:16');
-- EVENT DUMY ここまで

-- PINS_FAVORITE DUMY ここから
INSERT INTO PINS_FAVORITE(USERS_USER_ID,PINS_ID)
VALUES('hyogo1111',3)
,('ultrasoul',3)
,('hyogo1111',2)
,('satoh',1);
-- PINS_FAVORITE DUMY ここまで

-- REVIEWS_FAVORITE DUMY ここから
INSERT INTO REVIEWS_FAVORITE(USERS_USER_ID,REVIEWS_ID)
VALUES('ultrasoul',1)
,('hyogo1111',3)
,('ultrasoul',2);
-- REVIEWS_FAVORITE DUMY ここまで

-- EVENTS_FAVORITE DUMY ここから
INSERT INTO EVENTS_FAVORITE(USERS_USER_ID,EVENTS_ID)
VALUES('hyogo1111',1)
,('ultrasoul',3)
,('ultrasoul',2);
-- EVENTS_FAVORITE DUMY ここまで

-- PIN_TAGS DUMY ここから
INSERT INTO PIN_TAGS(PINS_ID,TAGS_CATEGORY_ID)
VALUES(1,3)
,(1,4)
,(2,3)
,(4,1)
,(4,2)
,(4,3)
,(4,4)
,(5,1)
,(5,2)
,(5,4)
,(6,2)
,(6,3)
,(7,3)
,(7,4)
,(8,1)
,(8,2)
,(8,3)
,(9,5)
,(10,4)
,(10,5)
,(11,3)
,(12,3)
,(14,1)
,(14,2)
,(14,3)
,(14,4);
-- PIN_TAGS DUMY ここまで
