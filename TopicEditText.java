package com.xanthus.topicedittext;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by liyiheng on 16/11/29.
 */

public class TopicEditText extends EditText {
    private char topicChar = '#';
    /**
     * Color of the topic text.
     * Blue as default
     */
    private int colorTopic = 0xFF0000FF;

    /**
     * @return topicChar '#' as the default character.
     */
    public char getTopicChar() {
        return topicChar;
    }

    /**
     * @param topicChar '#' as the default character.
     */
    public void setTopicChar(char topicChar) {
        this.topicChar = topicChar;
    }

    public int getColorTopic() {
        return colorTopic;
    }

    public void setColorTopic(int colorTopic) {
        this.colorTopic = colorTopic;
    }


    private ArrayList<Integer> lastIndexes;
    private boolean changed;

    public TopicEditText(Context context) {
        super(context);
    }

    public TopicEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public TopicEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        ArrayList<Integer> indexes = new ArrayList<>();
        char[] chars = s.toString().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == topicChar) {
                indexes.add(i);
            }
        }
        int size = indexes.size();
        changed = false;
        if (lastIndexes == null || lastIndexes.size() != size) {
            lastIndexes = indexes;
            changed = true;
        }
        if (!changed) return;
        //if (s.length() == 0 || s.charAt(s.length() - 1) != '#') return;
        SpannableString ss = new SpannableString(s);
        ss.setSpan(new ForegroundColorSpan(getTextColors().getDefaultColor()), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int halfSize = size / 2;
        for (int i = 0; i < halfSize; i++) {
            Integer spanStart = lastIndexes.get(i * 2);
            int spanEnd = lastIndexes.get(i * 2 + 1) + 1;
            ss.setSpan(new ForegroundColorSpan(colorTopic), spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        int selectionEnd = getSelectionEnd();
        setText(ss);
        setSelection(selectionEnd);
        if (onTopicStartListener != null) {
            int length = s.length();
            if (length != 0) {
                if (size % 2 == 1 && s.charAt(length - 1) == topicChar) {
                    onTopicStartListener.topicStart();
                }
            }

        }
    }

    public void appendTopic(CharSequence cs) {
        getText().append(cs).append(topicChar);
    }

    private OnTopicStartListener onTopicStartListener;

    public void setOnTopicStartListener(OnTopicStartListener onTopicStartListener) {
        this.onTopicStartListener = onTopicStartListener;
    }

    public interface OnTopicStartListener {
        void topicStart();
    }

}
