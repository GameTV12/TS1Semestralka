package cz.cvut.fel.myselect;

/**
 * @author Kozhemiakin Viktor
 * Class for REST API
 */
public class Constant {
    public static final String URL = "http://myvote.fun/";
    public static final String HOME = URL+"api";
    public static final String LOGIN = HOME+"/login";
    public static final String LOGOUT = HOME+"/logout";
    public static final String REGISTER = HOME+"/register";
    public static final String SAVE_USER_INFO = HOME+"/save_user_info";
    public static final String POSTS = HOME+"/posts";
    public static final String ADD_POST = POSTS+"/create";
    public static final String DELETE_POST = POSTS+"/delete";
    public static final String MY_POST = POSTS+"/my_posts";
}