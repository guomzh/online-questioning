package com.guomzh.onlineq.service;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zgm
 * @date 2018/9/7 15:25
 */
@Service
public class SensitiveService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);
    private TrieNode rootNode = new TrieNode();

    public String filter(String text) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(text)) {
            return text;
        }
        String replace = "***";
        TrieNode curNode = rootNode;
        int begin = 0;
        int position = 0;
        while (position < text.length()) {
            char c = text.charAt(position);
            //如果不是东亚文字，如空格，则跳过在trie树中匹配
            if (isSymbol(c)) {
                if (curNode == rootNode) {
                    sb.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            curNode = curNode.getSubNode(c);
            /**
             * 如果curNode等于Null,则说明在trie树中找不到下个字符，这个词不会是敏感词
             * 换句话说，trie树中没有这个子节点，说明begin开头的字符串不是敏感词，
             * 把begin这个字符加入过滤后的字符串中,同时前缀树指针指回开头
             */
            if (curNode == null) {
                sb.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                curNode = rootNode;
            } else if (curNode.isKeywordEnd()) {
                //发现一个敏感词
                sb.append(replace);
                position = position + 1;
                begin = position;
                curNode = rootNode;
            } else {
                ++position;
            }
        }
        sb.append(text.substring(begin));
        return sb.toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                addWord(lineTxt.trim());
            }
            reader.close();
        } catch (Exception e) {
            logger.error("读取过滤的敏感词文件失败" + e.getMessage());
        }
    }

    //构建trie树，在前缀树中添加敏感词
    private void addWord(String lineTxt) {
        TrieNode curNode = rootNode;
        for (int i = 0; i < lineTxt.length(); ++i) {
            Character c = lineTxt.charAt(i);
            TrieNode node = curNode.getSubNode(c);
            if (node == null) {
                node = new TrieNode();
                curNode.addSubNode(c, node);
            }
            curNode = node;
            if (i == lineTxt.length() - 1) {
                curNode.setKeywordEnd(true);
            }
        }
    }

    private class TrieNode {
        private boolean end = false;
        //子节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        boolean isKeywordEnd() {
            return end;
        }

        void setKeywordEnd(boolean end) {
            this.end = end;
        }
    }

    //返回true表示不是东亚文字
    private boolean isSymbol(char c) {
        int i = (int) c;
        //东亚文字0x2E80-0x9FFF
        return !CharUtils.isAsciiAlphanumeric(c) && (i < 0x2E80 || i > 0x9FFF);
    }

}
