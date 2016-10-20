package com.github.funyoung.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yangfeng on 16-10-20.
 */

public class ArticleParagraphModel {
    private final List<ParagraphNode> paragraphNodeList = new ArrayList<>();
    private final Random random = new Random();

    public void add(ParagraphNode paragraphNode) {
        paragraphNodeList.add(paragraphNode);
    }

    public static class SentenceNode {
        private final List<String> segmentList = new ArrayList<>();
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            for (String segment : segmentList) {
                buffer.append(segment);
            }
            return buffer.toString();
        }

        public List<String> getSegmentList() {
            return segmentList;
        }

        public void reset() {
            segmentList.clear();
        }

        public void add(String seg) {
            segmentList.add(seg);
        }
    }
    public static class ParagraphNode {
        private final List<SentenceNode> sentenceNodeList = new ArrayList<>();
        private final Random random = new Random();
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            for (SentenceNode sentence : sentenceNodeList) {
                buffer.append(sentence);
            }
            return buffer.toString();
        }
        public List<String> getSegmentList() {
            List<String> segmentList = new ArrayList<>();
            for (SentenceNode sentence : sentenceNodeList) {
                segmentList.addAll(sentence.getSegmentList());
            }
            return segmentList;
        }

        public void reset() {
            for (SentenceNode sentence : sentenceNodeList) {
                sentence.reset();
            }
            sentenceNodeList.clear();
        }

        public void add(SentenceNode sentenceNode) {
            sentenceNodeList.add(sentenceNode);
        }

        public String randomSentence() {
            if (sentenceNodeList.isEmpty()) {
                return "";
            }

            int index = random.nextInt(sentenceNodeList.size());
            return sentenceNodeList.get(index).toString();
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (ParagraphNode paragraph : paragraphNodeList) {
            buffer.append(paragraph);
        }
        return buffer.toString();
    }

    public List<String> getSegmentList() {
        List<String> segmentList = new ArrayList<>();
        for (ParagraphNode paragraph : paragraphNodeList) {
            segmentList.addAll(paragraph.getSegmentList());
        }
        return segmentList;
    }

    public void reset() {
        for (ParagraphNode paragraphNode : paragraphNodeList) {
            paragraphNode.reset();
        }
        paragraphNodeList.clear();
    }

    public String randomSentence() {
        if (paragraphNodeList.isEmpty()) {
            return "";
        }

        int index = random.nextInt(paragraphNodeList.size());
        return paragraphNodeList.get(index).randomSentence();
    }
}
