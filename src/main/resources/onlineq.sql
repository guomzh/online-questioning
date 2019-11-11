/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50627
Source Host           : localhost:3306
Source Database       : onlineq

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2018-09-15 13:55:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `content` text,
  `user_id` int(11) DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `entity_type` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entity_index` (`entity_id`,`entity_type`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '&quot;hahah&quot;', '0', '19', '100', '2018-09-09 07:42:51', '0');
INSERT INTO `comment` VALUES ('9', '我发一条评论试试', '20', '19', '100', '2018-09-09 08:51:50', '0');
INSERT INTO `comment` VALUES ('10', '***你好', '20', '19', '100', '2018-09-09 09:17:07', '0');
INSERT INTO `comment` VALUES ('11', '再加一条评论试试', '20', '19', '100', '2018-09-09 09:35:29', '0');
INSERT INTO `comment` VALUES ('12', '我不知到啊，我能怎么办？？？', '21', '11', '100', '2018-09-10 09:26:58', '0');
INSERT INTO `comment` VALUES ('13', '我是小军，我来凑热闹了啊拉拉拉拉啊了***，***，我都喜欢。。。', '23', '11', '100', '2018-09-10 09:30:49', '0');
INSERT INTO `comment` VALUES ('14', '      这人不太好评价,传奇一般的存在,也就只能出现在小说里了\r\n大女主,有光环的人做任何事都对.\r\n\r\n官方给的人设是善良义气又嫉恶如仇的黑莲花.但是她之所以讨喜,\r\n或者之所以这类三观影响了一\r\n批人,原因在于她的幸存辱骂皇上,逼问太妃致死,\r\n诓骗贵妃......她任何一步黑莲花举动放到真正的\r\n大清或者甚至现在这个时代,\r\n是不可能走下去的在大清,皇上甚至会因为这个宫女总被诬陷,认为此\r\n人不详,\r\n搅动后宫而直接处理掉如果照着真实情况演,璎珞活不过5集,也就做不成女主了\r\n大概舆论\r\n对这种黑莲花人设也就会换一种解释了魏璎珞作为宫女,\r\n在深宫中太过冒险,心思极细,步步为营,\r\n即使每次都证明自己是被奸人冤枉,\r\n但她忘了在后宫中,毕竟是个女人,围着她生事太多会有不详\r\n之嫌祸水一词\r\n大概也就是这个意思吧不一定是她自己要怎样,但是只要她出现周围就风波不断,\r\n\r\n此为不详者,必定留不得这个道理在大清成立。\r\n       在现实中也适用再者,\r\n就算外界给了这个黑莲花以\r\n生存的空间,也很难保证她能掌握好善恶尺度.\r\n剧里面的魏璎珞每一步都是为了复仇即使她讨好\r\n皇上也是为了当初口中所的靠山;,\r\n为了给皇后报仇她为了向自己的目标前进一步,\r\n能连环设计整个后宫,\r\n能布3个月的局万幸她是为了正义,为了复仇.这种心思如果向私心动了一\r\n点点,\r\n下场会有多可怕?剧里表现的是,她一点自己的私心都没有但是现实生活里面,\r\n有这样的人\r\n吗?职场里面,谁不是为了自己的发展?谁不是利益的寻求者?\r\n个人拙见,魏璎珞这类人只适合平\r\n时绝对心善的包子效仿,她教会心善软弱的要懂得以牙还牙。\r\n然而对于自己本来就揣了一肚子\r\n小心思,甚至小嫉妒的仙女,还是算了吧.....\r\n撇去善良二字,魏璎珞会有多讨厌,简直不能想象剧里\r\n的人物看看就好\r\n\r\n      补充: 本人并不讨厌她,这女主本人大家看着都喜欢,不管是宫斗剧还是生活里面看着都很\r\n痛快\r\n只是澄清这个女主角人设的不可复制性:幸运的幸存和永不改变的三观,\r\n魏璎珞之所以这么\r\n幸运也从不做恶,就是因为她的每一步算计,不多也不少,\r\n在为自己报仇的时候能保证不连带着\r\n坑害到任何一个无辜的人,精准至极.\r\n更重要的, 她能活下来是因为有编剧在,有女主光环在,我\r\n前面说她活不过5集,\r\n貌似都说多了......一步步走到现在,靠的真的是作者给的好命...\r\n可惜的是,\r\n在剧里面可以把这个人三观设计得笔直,绝不误会任何一个人,\r\n作者可以把女主的人生设计得\r\n步步为营,惩恶扬善,路路回转,\r\n但这终究是在剧里在生活里没有几个人这么幸运,没有那么的善\r\n良, \r\n没有那个绝不会误会任何一个好人的慧眼,也没有那么知轻重,懂得报仇以后适时收手,\r\n不\r\n连带伤害到任何一个无辜的人也没有几个人会这么好命之所以极力澄清这个女主的不可复\r\n制性,\r\n就是希望小姑娘们不要看剧看得开心,一猛子扎下去开始训练自己做有心计的愤青首先\r\n怕是混不下去其次,\r\n走偏一点就会毁了自己最最想补充的是什么呢我看到54集就想等更完,没\r\n看了\r\n印象中魏璎珞先是给姐姐报仇,然后给皇后先是接近傅恒, 慢慢产生了感情\r\n后来接近皇上\r\n,也成为了令妃,也算是人生赢家卫龙 CP\r\n看着也是甜得很但是真的始终觉得有些可惜,替她累\r\n即使在自己的丈夫面前,\r\n每一个小小的举动,背后都是欲擒故纵的功于心计没有一步不是算好\r\n的......\r\n引得皇上喜欢,我们看着甜到腻但是魏璎珞,究竟有没有真的开心?\r\n九曲心肠步步如履薄\r\n冰,可曾有放松下来,真正享受爱的自由?\r\n所以如果评价魏璎珞的人生,感觉她像是正义的捍卫\r\n者却难说她是真的快乐。', '21', '20', '100', '2018-09-10 10:00:29', '0');
INSERT INTO `comment` VALUES ('15', '我想说&hellip;如果不是女主光环 她早被赐死千万次了吧&hellip;还有我觉得她并不好看&hellip;刚开始知道她是女主 就完全没有了想看下去的欲望。。。', '23', '20', '100', '2018-09-10 10:01:48', '0');
INSERT INTO `comment` VALUES ('16', '前排吃瓜，有请大佬回答。', '21', '4', '100', '2018-09-10 23:51:05', '0');
INSERT INTO `comment` VALUES ('17', '这就尴尬了，有谁说一下自己的经历码?哈哈哈', '21', '3', '100', '2018-09-10 23:54:12', '0');
INSERT INTO `comment` VALUES ('18', '有请伍文君同学给我回答一下。。。。', '21', '11', '100', '2018-09-10 23:57:12', '0');
INSERT INTO `comment` VALUES ('19', '谷歌一下就知道啦', '23', '12', '100', '2018-09-11 08:50:31', '0');
INSERT INTO `comment` VALUES ('20', '在语言首选项里设置一下就好了', '23', '12', '100', '2018-09-11 09:42:07', '0');
INSERT INTO `comment` VALUES ('21', '有请楼下详解，吃瓜观众来啦', '23', '3', '100', '2018-09-11 09:45:30', '0');

-- ----------------------------
-- Table structure for login_ticket
-- ----------------------------
DROP TABLE IF EXISTS `login_ticket`;
CREATE TABLE `login_ticket` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `ticket` varchar(50) DEFAULT NULL,
  `expired` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ticket` (`ticket`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login_ticket
-- ----------------------------
INSERT INTO `login_ticket` VALUES ('51', '27', '530b814d7ba44a3e863fdf0f748d3ebf', '2018-09-28 22:55:15', '0');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `from_id` int(11) DEFAULT NULL,
  `to_id` int(11) DEFAULT NULL,
  `content` text,
  `has_read` int(11) DEFAULT NULL,
  `conversation_id` varchar(45) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `conversation_index` (`conversation_id`),
  KEY `created_date` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `user_id` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `comment_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('3', '在正式场合穿着正装如何避免被当做服务员？', '怕被当作服务员，可以穿White tie full dress（一般二般的服务员没那么讲究）： 黑色或藏蓝色燕尾服 裤边带有镶边的同样颜色长裤 正装白衬衫 白色腰封 白色蝴蝶领结 黑色袜子 黑色非系带皮鞋 既然你们都说这是服务员标配，那么请告诉我哪家餐厅服务员和领班', '20', '2018-09-06 19:28:01', '2');
INSERT INTO `question` VALUES ('4', '当前凝聚态物理（理论以及实验）有哪些研究热点和难题？', '08年入行，略微有一些了解。以下是我所涉猎的一些08年后的研究重点。本人实验物理出身，理论上不周全和错误之处还请海涵。评论区指出了一些表达不清楚容易误解之处甚至是错误之处，已经一一修改，在此一并感谢。凝聚态领域很大，有一些方面不熟悉或者不了解', '21', '2018-09-06 19:40:37', '1');
INSERT INTO `question` VALUES ('11', '为什么月球上的陨石坑直径很大，但却很浅呢？', '2010.3 据物理学家组织网报道，月球形成后不久，一颗小行星在月球南半球与其相撞，形成一个巨大的陨石坑，它就是目前的南极-艾托肯盆地(SPA)，这个盆地的直径大约是1500英里(2414.01公里)，深度超过5英里(8.05公里)。\r\n\r\n月球表面的坑洞是陨石和彗星撞击形成的。地球上也有些陨石坑，据说科学家计算出来，若是一颗直陉10哩的陨石，以每秒三万哩的速度(等於100万吨黄色炸药的威力)撞到地球或月球，它所穿透的深度应该是直径的四到五倍。', '20', '2018-09-07 10:22:49', '3');
INSERT INTO `question` VALUES ('12', '爲什麽我打的字都是繁體字', '誰能解答一下嗎', '21', '2018-09-07 10:36:59', '2');
INSERT INTO `question` VALUES ('19', '排名在前 1% 的高中生是靠天赋还是靠努力？', '我曾经就读于一所全省前2名的高中（广东省），身边不乏有很多学习极为优秀的学生。他们在高考中能在70万人排进前1% 也就是前7000名。和他们学习在一起之后我发现了个有趣的现象：他们当中有挺多人花费在玩的时间并不比考进前5万名的学生少，甚至更有多。他们却能做到玩的很多，却成绩又很好。通常，当老师提出一个新的知识点时，他们能非常快速的理解和运用，其他学生则需要花费一点时间。考试的试卷设计通常会让普通学生得到60%-75%的分数，优秀的学生得到75-85%，而厉害的学生则能得到85%-90%的分数（理科尤为明显，后面的大题不是靠背书就能解决的）。我觉得，努力能让一个学生成为优秀的学生，可想进入前1%， 除了努力是不是还有其他的因素？\r\n--------------------------------------------------------------------------------------------------------------------------------------------\r\n现有多人提出，会学习，能持续学习，会制定计划这些能力均属于“天赋（如智商）”的体现，大家对此的看法是什么？', '20', '2018-09-07 16:58:27', '4');
INSERT INTO `question` VALUES ('20', '怎么评价《延禧攻略》中的角色魏璎珞？', '这人应该如何评价？传奇一般的存在,也就只能出现在小说里了\r\n\r\n大女主,有光环的人做任何事都对.\r\n\r\n官方给的人设是善良义气又嫉恶如仇的黑莲花.\r\n\r\n但是她之所以讨喜,或者之所以这类三观影响了一批人,原因在于她的幸存\r\n\r\n辱骂皇上,逼问太妃致死,诓骗贵妃......', '23', '2018-09-10 09:53:30', '2');
INSERT INTO `question` VALUES ('21', '既然所有的生命都要死亡，那么生命的意义是什么？', '&ldquo;意义&rdquo;这个词语需要解释', '23', '2018-09-13 16:47:30', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `salt` varchar(64) DEFAULT NULL,
  `head_url` varchar(128) DEFAULT NULL,
  `email` varchar(64) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('0', '匿名用户', '582BF1BF3C82E368FF13859E5408F446', 'b1e58', 'https://images.nowcoder.com/head/281m.png', ' ');
INSERT INTO `user` VALUES ('2', '赞', '58C0636E9811EB28CE608F4F69C4CE3A', '8165d', 'https://images.nowcoder.com/head/900m.png', ' ');
INSERT INTO `user` VALUES ('3', '关注通知', 'F41D98898A71C42F5EE71992C3E7E17F', 'bf5f4', 'https://images.nowcoder.com/head/684m.png', ' ');
INSERT INTO `user` VALUES ('20', '小兰', '1554BC2560BEEC9AA5B3A2FA4B053F06', '76096', 'https://images.nowcoder.com/head/995m.png', ' ');
INSERT INTO `user` VALUES ('21', '小明', '3BCE7F18D924ACF00DDBEE824F4B8CDC', 'd7d20', 'https://images.nowcoder.com/head/621m.png', ' ');
INSERT INTO `user` VALUES ('23', '小军', 'BF302E979061DEFABAC27818F3845506', '2d18a', 'https://images.nowcoder.com/head/636m.png', ' ');
INSERT INTO `user` VALUES ('26', '伍文君', 'B677512E2EB222BA1A51D53FD974AE42', '2338a', 'https://images.nowcoder.com/head/479m.png', '1434043324@qq.com');
