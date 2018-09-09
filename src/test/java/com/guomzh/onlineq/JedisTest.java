package com.guomzh.onlineq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guomzh.onlineq.domain.User;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * @author zgm
 * @date 2018/9/9 15:51
 */
public class JedisTest {
    public static void print(int index, Object obj) {
        System.out.println(String.format("%d , %s", index, obj.toString()));
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://localhost:6379/1");
        jedis.flushDB();

        //get和set操作
        jedis.set("hello", "first redis");
        print(1, jedis.get("hello"));
        jedis.rename("hello", "renameHelloKey");
        jedis.setex("expired", 15, "15秒过期没有啦");

        //加减操作
        jedis.set("pv", "100");
        jedis.incr("pv");
        print(2, jedis.get("pv"));
        jedis.incrBy("pv", 2);
        print(3, jedis.get("pv"));
        jedis.decrBy("pv", 3);
        print(4, jedis.get("pv"));

        //打印所有键
        print(3, jedis.keys("*"));

        //list操作,类似栈结构
        String listName = "list";
        jedis.del(listName);
        for (int i = 0; i < 10; ++i) {
            jedis.lpush(listName, "a" + String.valueOf(i));
        }
        print(5, jedis.lrange(listName, 0, 20));
        print(6, jedis.llen(listName));
        print(7, jedis.lpop(listName));
        print(8, jedis.llen(listName));
        print(9, jedis.lindex(listName, 2));
        print(10, jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "a8", "insert1"));
        print(11, jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "a8", "insert2"));
        print(12, jedis.lrange(listName, 0, 20));

        /**
         *  hash操作,优点是可以随时给数据库添加或删除属性，这在mysql中很麻烦
         *  例如今天给用户加个年龄属性，明天给用户加个email属性
         *  这在关系型数据库中很麻烦，在redis中用hset很方便
         */
        String userKey = "user_01";
        jedis.hset(userKey, "name", "小明");
        jedis.hset(userKey, "age", "18");
        jedis.hset(userKey, "phone", "110");
        print(13, jedis.hget(userKey, "name"));
        print(14, jedis.hgetAll(userKey));
        jedis.hdel(userKey, "phone");
        print(15, jedis.hgetAll(userKey));
        print(16, jedis.hexists(userKey, "email"));
        print(17, jedis.hkeys(userKey));
        print(18, jedis.hvals(userKey));
        jedis.hsetnx(userKey, "university", "CSU");
        jedis.hsetnx(userKey, "name", "小兰");
        print(20, jedis.hgetAll(userKey));
        print(21, jedis.hlen(userKey));

        //set集合操作，应用场景：共同关注求交，去重，例如点赞和踩功能，一个用户只能点赞一次
        String likeKey1 = "commentLike1";
        String likeKey2 = "commentLike2";
        for (int i = 0; i < 10; i++) {
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2, String.valueOf(i * i));
        }
        print(22, jedis.smembers(likeKey1));
        print(23, jedis.smembers(likeKey2));
        //求交，如共同好友
        print(24, jedis.sinter(likeKey1, likeKey2));
        //求并,去重
        print(25, jedis.sunion(likeKey1, likeKey2));
        //求不同元素,第一个集合有，第二个没有
        print(26, jedis.sdiff(likeKey1, likeKey2));
        //查询
        print(27, jedis.sismember(likeKey1, "12"));
        //删除一个元素
        jedis.srem(likeKey1, "6");
        print(28, jedis.smembers(likeKey1));
        //一个元素从一个集合移进第二个集合
        jedis.smove(likeKey2, likeKey1, "25");
        print(29, jedis.smembers(likeKey2));
        //集合中元素个数
        print(30, jedis.scard(likeKey2));
        //随机元素，可以用来实现抽奖功能
        print(31, jedis.srandmember(likeKey1, 2));

        //优先队列操作（堆，每个元素带一个分值）,场景：排序有关的所有操作
        String rankKey = "rankey";
        jedis.zadd(rankKey, 15, "jack");
        jedis.zadd(rankKey, 156, "leo");
        jedis.zadd(rankKey, 55, "lucy");
        //集合数量
        print(32, jedis.zcard(rankKey));
        //50到70之间的数量
        print(33, jedis.zcount(rankKey, 50, 70));
        print(34, jedis.zscore(rankKey, "lucy"));
        jedis.zincrby(rankKey, 2, "lucy");
        print(35, jedis.zscore(rankKey, "lucy"));
        //求前2位是谁，排行,默认是从小到大
        print(36, jedis.zrange(rankKey, 0, 1));
        print(37, jedis.zrevrange(rankKey, 0, 1));
        //遍历
        for (Tuple tuple : jedis.zrevrangeByScoreWithScores(rankKey, 130, 10)) {
            print(38, tuple.getElement() + ":" + String.valueOf(tuple.getScore()));
        }
        //查看在队列中的排名
        print(38, jedis.zrank(rankKey, "jack"));
        print(39, jedis.zrevrank(rankKey, "jack"));

        String setKey = "zSet";
        jedis.zadd(setKey, 1, "a");
        jedis.zadd(setKey, 1, "b");
        jedis.zadd(setKey, 1, "c");
        jedis.zadd(setKey, 1, "d");
        jedis.zadd(setKey, 1, "e");
        print(40, jedis.zlexcount(setKey, "-", "+"));
        print(41, jedis.zlexcount(setKey, "[b", "[e"));
        jedis.zrem(setKey, "c");
        print(42, jedis.zrange(setKey, 0, 5));
        jedis.zremrangeByLex(setKey, "(c", "+");
        print(43, jedis.zrange(setKey, 0, 5));

//        JedisPool pool = new JedisPool();
//        for(int i=0;i<100;++i){
//            Jedis j=pool.getResource();
//            print(44,j.get("pv"));
//        }

        User user=new User();
        user.setName("用户002");
        user.setPassword("123456");
        user.setSalt("salt");
        user.setId(1);
        user.setHeadUrl("bb.png");
        jedis.set("user1", JSONObject.toJSONString(user));
        print(45,jedis.get("user1"));
        String user1_value=jedis.get("user1");
        User user2= JSON.parseObject(user1_value,User.class);
        print(46,user2);

    }
}
