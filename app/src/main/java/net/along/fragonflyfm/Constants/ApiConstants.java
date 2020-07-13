package net.along.fragonflyfm.Constants;


public class ApiConstants {
    /**
     * 默认的服务器IP和端口
     */
    public static final String DEFAULT_SERVER_IP = "10.83.229.222";
    public static final String DEFAULT_SERVER_PORT = "8888";
    /**
     * Practices数据的相对路径
     **/
    public static final String ACTION_PRACTICES = "/api/practices";
    /**
     * Questions数据的相对路径，需要传递practice（章节） id作为参数
     **/
    public static final String ACTION_QUESTIONS = "/api/pquestions?practiceid=";
    /**
     * 章节练习的上传结果地址
     **/
    public static final String ACTION_RESULT = "/api/Result/PracticeResult";


    /**
     * Api传递来的Json格式字符串中Practice对象对应的Key
     **/
    public static final String JSON_PRACTICE_API_ID = "Id";
    public static final String JSON_PRACTICE_NAME = "Name";
    public static final String JSON_PRACTICE_OUTLINES = "OutLines";
    public static final String JSON_PRACTICE_QUESTION_COUNT = "QuestionCount";
    /**
     * Api传递来的Json格式字符串中Question对象对应的Key
     **/
    public static final String JSON_QUESTION_ANALYSIS = "Analysis";
    public static final String JSON_QUESTION_CONTENT = "Content";
    public static final String JSON_QUESTION_PRACTICE_ID = "PracticeId";
    public static final String JSON_QUESTION_TYPE = "QuestionType";
    public static final String JSON_QUESTION_OPTIONS = "Options";
    public static final String JSON_QUESTION_ANSWERS = "Answers";
    /**
     * Api传递来的Json格式字符串中Option对象对应的Key
     **/
    public static final String JSON_OPTION_API_ID = "Id";
    public static final String JSON_OPTION_LABEL = "Label";
    public static final String JSON_OPTION_CONTENT = "Content";
    /**
     * Api传递来的Json格式字符串中Answer对象对应的Key
     **/
    public static final String JSON_ANSWER_OPTION_API_ID = "OptionId";
    /**
     * Post到Api的用户练习结果对应的Json Put中的key
     **/
    public static final String JSON_RESULT_API_ID = "PracticeID";
    public static final String JSON_RESULT_SCORE_RATIO = "ScroreRatio";
    public static final String JSON_RESULT_WRONG_IDS = "WrongQuestionIds";
    public static final String JSON_RESULT_PERSON = "PhoneNo";
}
