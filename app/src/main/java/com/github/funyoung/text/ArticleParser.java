package com.github.funyoung.text;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangfeng on 16-10-20.
 */

public class ArticleParser {
    private static final String DEFAULT_ID = "DEFAULT_ID";

    private static ArticleParser _instance;

    private String id;
    private final ArticleCharModel articleCharModel = new ArticleCharModel();
    private final ArticleParagraphModel articleParagraphModel = new ArticleParagraphModel();

    private ArticleParser(@NonNull String id) {
        reset(id);
    }

    private void reset(@NonNull String id) {
        if (TextUtils.equals(this.id, id)) {
            articleCharModel.reset();
            articleParagraphModel.reset();
        }

        this.id = id;
    }

    public static ArticleParser getInstance() {
        return getInstance(DEFAULT_ID);
    }

    public static ArticleParser getInstance(@NonNull String id) {
        if (null == _instance) {
            _instance = new ArticleParser(id);
        } else {
            _instance.reset(id);
        }
        return _instance;
    }

    public String parse(InputStream inputStream) {
        int index = 0;
        try {
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader bufReader = new BufferedReader(inputReader);
            StringBuilder builder = new StringBuilder();
            int tempChar;
            while ((tempChar = bufReader.read()) != -1) {
                parseAdded(index, tempChar, builder);
                index++;
            }
//            return articleCharModel.indexInfo();
            return articleParagraphModel.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void parseAdded(int index, int tempChar, StringBuilder builder) {
        builder.append((char) tempChar);
        articleCharModel.add(index, tempChar);
        if (tempChar == '\n' || tempChar == '\r') {
            addParagraph(builder.toString());
            builder.delete(0, builder.length());
        }
    }

    private void addParagraph(String text) {
        Pattern majorPunc = Pattern.compile("[．!.?！……。？]");
        Pattern minorPunc = Pattern.compile("[．`~!@#$^&*()=|{}':;',\\\\[\\\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");

        Matcher major = majorPunc.matcher(text);
        String[] sentences = majorPunc.split(text);
        ArticleParagraphModel.ParagraphNode paragraphNode = new ArticleParagraphModel.ParagraphNode();
        for (String str : sentences) {
            if (major.find()) {
                str += major.group();
            }
            Matcher minor = minorPunc.matcher(str);
            String[] segments = minorPunc.split(str);
            ArticleParagraphModel.SentenceNode sentenceNode = new ArticleParagraphModel.SentenceNode();
            for (String seg : segments) {
                if (minor.find()) {
                    seg += minor.group();
                }
                sentenceNode.add(seg);
            }
            paragraphNode.add(sentenceNode);
        }
        articleParagraphModel.add(paragraphNode);
    }
}
